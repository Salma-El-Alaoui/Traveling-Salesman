package Model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

/**
 * Class that represents a delivery
 */
public class Delivery implements XmlParse {

	/**
	 * Time to complete a delivery
	 */
	private static final int DELIVERY_TIME = 10 * 60;

	/**
	 * Constructor
	 * 
	 * @param node
	 *            Node associated with the delivery
	 */
	public Delivery(Node node) {
		node.setDelivery(this);
		mNode = node;
	}

	/**
	 * Delivery hour
	 */
	private int mDeliveryHour;

	/**
	 * Node associated with the delivery
	 */
	private Node mNode;

	/**
	 * Id of the delivery
	 */
	private int mId;

	/**
	 * Hour when the delivery man reaches the node
	 */
	private int mArrivalHour;

	/**
	 * Hour when the delivery man leaved the node
	 */
	private int mDepartureHour;

	/**
	 * Time slot in which the delivery should happen
	 */
	private TimeSlot mTimeSlot;

	/**
	 * Client to be delivered
	 */
	private int mClient;

	/**
	 * Constructor
	 * 
	 * @param timeSlot
	 *            Time slot of the delivery
	 */
	public Delivery(TimeSlot timeSlot) {
		mTimeSlot = timeSlot;
	}

	/**
	 * Returns the node associated with the delivery
	 * 
	 * @return the node associated with the delivery
	 */
	public Node getNode() {
		return mNode;
	}

	/**
	 * Returns the client to be delivered
	 * 
	 * @return the client to be delivered
	 */
	public int getClient() {
		return mClient;
	}

	/**
	 * Returns the hour when the delivery man reaches the delivery node
	 * 
	 * @return Arrival Hour
	 */
	public int getArrivalHour() {
		return mArrivalHour;
	}

	/**
	 * Returns the arrival hour as a String
	 * 
	 * @return Arrival Hour
	 */
	public String getFormattedArrivalHour() {
		NumberFormat nf = new DecimalFormat("##00");
		String hours = nf.format(mArrivalHour / 3600);
		String minutes = nf.format((mArrivalHour % 3600) / 60);
		String seconds = nf.format(mArrivalHour % 60);
		return hours + ":" + minutes + ":" + seconds;
	}

	/**
	 * Returns the hour when the client is delivered
	 * 
	 * @return Delivery Hour
	 */
	public int getDeliveryHour() {
		return mDeliveryHour;
	}

	/**
	 * Returns the delivery hour as a String
	 * 
	 * @return Delivery Hour
	 */
	public String getFormattedDeliveryHour() {
		NumberFormat nf = new DecimalFormat("##00");
		String hours = nf.format(mDeliveryHour / 3600);
		String minutes = nf.format((mDeliveryHour % 3600) / 60);
		String seconds = nf.format(mDeliveryHour % 60);
		return hours + ":" + minutes + ":" + seconds;
	}

	/**
	 * Returns the hour when the delivery man leaves the node
	 * 
	 * @return Departure Hour
	 */
	public int getDepartureHour() {
		return mDepartureHour;
	}

	/**
	 * Returns the departure hour as a String
	 * 
	 * @return
	 */
	public String getFormattedDepartureHour() {
		NumberFormat nf = new DecimalFormat("##00");
		String hours = nf.format(mDepartureHour / 3600);
		String minutes = nf.format((mDepartureHour % 3600) / 60);
		String seconds = nf.format(mDepartureHour % 60);
		return hours + ":" + minutes + ":" + seconds;
	}

	/**
	 * Sets the time slot associated with the delivery
	 * 
	 * @param TimeSlot
	 *            timeSlot associated with the delivery
	 */
	public void setTimeSlot(TimeSlot timeSlot) {
		if (mTimeSlot != null) {
			mTimeSlot.removeDelivery(this);
		}
		if (timeSlot != null) {
			timeSlot.addDelivery(this);
		}
		mTimeSlot = timeSlot;
	}

	/**
	 * Update all the hours according to the arrival hour
	 * 
	 * @param arrivalHour
	 */
	public void setArrivalHour(int arrivalHour) {
		mArrivalHour = arrivalHour;
		if (mArrivalHour < mTimeSlot.getStartHour()) {
			mDeliveryHour = mTimeSlot.getStartHour();
		} else {
			mDeliveryHour = arrivalHour;
		}
		mDepartureHour = mDeliveryHour + DELIVERY_TIME;
	}

	/**
	 * Returns the time slot associated with the delivery
	 * 
	 * @return time slot
	 */
	public TimeSlot getTimeSlot() {
		return mTimeSlot;
	}

	/**
	 * Fills the delivery objects with data from XML files.
	 * 
	 * @param deliveryElement
	 *            The XML delivery Element to be parsed
	 * @param network
	 *            The network
	 * @param listClientsWithSeveralAdresses
	 *            String representing the list of clients with several
	 *            addresses. Warning to be raised.
	 * @param map_clientAdress
	 *            Used together with list_allAdress to verify the one to one
	 *            mapping between clients and addresses.
	 * @param list_allAdress
	 *            Used together with map_clientAdress to verify the one to one
	 *            mapping between clients and addresses.
	 * @return reportString Warnings list if applicable.
	 */
	@Override
	public String buildFromXML(Element deliveryElement, Network network,
			String listClientsWithSeveralAdresses,
			Map<Integer, Node> map_clientAdress, List<Integer> list_allAdress)
			throws InvalidDeliveryRequestFileException {
		mId = Integer.parseInt(deliveryElement.getAttribute("id"));
		mClient = Integer.parseInt(deliveryElement.getAttribute("client"));
		int nodeId = Integer.parseInt(deliveryElement.getAttribute("adresse"));
		Integer nodeIdInteger = new Integer(nodeId);

		mNode = network.getNode(nodeId);

		if (mNode == null) {
			throw new InvalidDeliveryRequestFileException(
					"Le noeud "
							+ nodeId
							+ " dans les demandes de Livraisons n'existe pas dans le Réseau");

			// Node not in the Network
		} else {

			mNode.setDelivery(this);

			if (list_allAdress.contains(nodeIdInteger)) {
				throw new InvalidDeliveryRequestFileException("L'adresse "
						+ nodeId + " ne peut pas être associée à deux Clients");
			} else
				list_allAdress.add(nodeIdInteger);

			// Check 1 Client had 1 Adress
			if (map_clientAdress.get(mClient) != null) {
				if (!map_clientAdress.get(mClient).equals(mNode)) {
					listClientsWithSeveralAdresses += mClient + " ";
					return listClientsWithSeveralAdresses;
				}
			} else {
				map_clientAdress.put(mClient, mNode);
				return "OK";
			}
			return null;
		}
	}

	@Override
	public String toString() {
		return "(Delivery : ID " + mId + " ,Node " + mNode + " ,Client "
				+ mClient + ");";
	}

	/**
	 * Indicates if the node is in it TimeSlot
	 * 
	 * @return True if the Delivery is in it timeslot
	 */
	public boolean isInTimeslot() {
		return mDeliveryHour >= mTimeSlot.getStartHour()
				&& mDeliveryHour <= mTimeSlot.getEndHour();
	}

	@Override
	public String buildFromXML(Element element, Network network)
			throws InvalidDeliveryRequestFileException,
			WarningDeliveryRequestFile {
		// TODO Auto-generated method stub
		return null;
	}

}