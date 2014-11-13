package Model;

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
	 * @param node Node associated with the delivery
	 */
	public Delivery(Node node) {
		node.setDelivery(this);
		mNode = node;
	}

	/**
	 * Delivery hour
	 */
	protected int mDeliveryHour;

	/**
	 * Node associated with the delivery
	 */
	protected Node mNode;
	
	/**
	 * Id of the delivery
	 */
	protected int mId;

	/**
	 * Hour when the delivery man reaches the node
	 */
	protected int mArrivalHour;

	/**
	 * Hour when the delivery man leaved the node
	 */
	protected int mDepartureHour;

	/**
	 * Time slot in which the delivery should happen
	 */
	protected TimeSlot mTimeSlot;
	
	/**
	 * Client to be delivered
	 */
	protected int mClient;
	
	/**
	 * Constructor
	 * @param timeSlot Time slot of the delivery
	 */
	public Delivery(TimeSlot timeSlot) {
		mTimeSlot = timeSlot;
	}
	
	/**
	 * Returns the node associated with the delivery
	 * @return the node associated with the delivery
	 */
	public Node getNode() {
		return mNode;
	}

	/**
	 * Returns the client to be delivered
	 * @return the client to be delivered
	 */
	public int getClient() {
		return mClient;
	}

	/**
	 * Returns the hour when the delivery man reaches the delivery node
	 * @return Arrival Hour
	 */
	public int getArrivalHour() {
		return mArrivalHour;
	}
	
	/**
	 * Returns the arrival hour as a String
	 * @return Arrival Hour
	 */
	public String getFormattedArrivalHour(){
		String hours = ((Integer) (mArrivalHour / 3600)).toString();
		String minutes = ((Integer) ((mArrivalHour % 3600) / 60)).toString();
		String seconds = ((Integer) (mArrivalHour % 60)).toString();
		return hours + ":" + minutes + ":" + seconds;
	}
	
	/**
	 * Returns the hour when the client is delivered
	 * @return Delivery Hour
	 */
	public int getDeliveryHour() {
		return mDeliveryHour;
	}
	
	/**
	 * Returns the delivery hour as a String
	 * @return Delivery Hour
	 */
	public String getFormattedDeliveryHour(){
		String hours = ((Integer) (mDeliveryHour / 3600)).toString();
		String minutes = ((Integer) ((mDeliveryHour % 3600) / 60)).toString();
		String seconds = ((Integer) (mDeliveryHour % 60)).toString();
		return hours + ":" + minutes + ":" + seconds;
	}

	/**
	 * Returns the hour when the delivery man leaves the node
	 * @return Departure Hour
	 */
	public int getDepartureHour() {
		return mDepartureHour;
	}

	/**
	 * Returns the departure hour as a String
	 * @return
	 */
	public String getFormattedDepartureHour(){
		String hours = ((Integer) (mDepartureHour / 3600)).toString();
		String minutes = ((Integer) ((mDepartureHour % 3600) / 60)).toString();
		String seconds = ((Integer) (mDepartureHour % 60)).toString();
		return hours + ":" + minutes + ":" + seconds;
	}

	/**
	 * Sets the time slot associated with the delivery
	 * @param TimeSlot
	 *            timeSlot associated with the delivery
	 */
	public void setTimeSlot(TimeSlot timeSlot) {
		if(mTimeSlot!=null)
		{
			mTimeSlot.removeDelivery(this);	
		}
		timeSlot.addDelivery(this);
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
	 * @return time slot
	 */
	public TimeSlot getTimeSlot() {
		return mTimeSlot;
	}

	@Override
	public String buildFromXML(Element deliveryElement, Network network,
			String listClientsWithSeveralAdresses,
			Map<Integer, Node> map_clientAdress)
					throws InvalidDeliveryRequestFileException {
		mId = Integer.parseInt(deliveryElement.getAttribute("id"));
		mClient = Integer.parseInt(deliveryElement.getAttribute("client"));
		int nodeId = Integer.parseInt(deliveryElement.getAttribute("adresse"));

		mNode = network.getNode(nodeId);
		mNode.setDelivery(this);

		if (mNode == null) {
			throw new InvalidDeliveryRequestFileException(
					"Le noeud "
							+ nodeId
							+ " dans les demandes de Livraisons n'existe pas dans le Réseau");
		}

		// Check if one client has just one and only one adress
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