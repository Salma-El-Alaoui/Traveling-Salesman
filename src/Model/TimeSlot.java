package Model;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Class that represents a time slot
 */
public class TimeSlot implements XmlParse, Comparable<TimeSlot> {

	/**
	 * Possible colors to be set to a Time Slot
	 */
	private static final Color[] COLORS = { new Color(248, 148, 6),
			new Color(30, 130, 76), new Color(65, 131, 215),
			new Color(242, 38, 19), new Color(103, 65, 114),
			new Color(219, 10, 91) };

	/**
	 * Color of this time slot
	 */
	private Color color;

	/**
	 * Constructor
	 * 
	 * @param i
	 *            number corresponding to a color
	 */
	public TimeSlot(int i) {
		mDeliveryList = new ArrayList<Delivery>();
		setColor(i);
	}

	/**
	 * Beginning of the time slot
	 */
	private int mStartHour;

	/**
	 * End of the time slot
	 */
	private int mEndHour;

	/**
	 * List of the deliveries to be delivered in this time slot
	 */
	private List<Delivery> mDeliveryList;

	/**
	 * Returns all the deliveries to be delivered in this time slot
	 * 
	 * @return all the deliveries to be delivered in this time slot
	 */
	public List<Delivery> getAllDeliveries() {
		return mDeliveryList;
	}

	/**
	 * Returns the beginning hour of the time slot
	 * 
	 * @return the beginning hour of the time slot
	 */
	public int getStartHour() {
		return mStartHour;
	}

	/**
	 * Returns the start hour as a string
	 * 
	 * @return the start hour as a string
	 */
	public String getFormattedStartHour() {
		NumberFormat nf = new DecimalFormat("##00");
		String hours = nf.format(mStartHour / 3600);
		String minutes = nf.format((mStartHour % 3600) / 60);
		String seconds = nf.format(mStartHour % 60);
		return hours + ":" + minutes + ":" + seconds;
	}

	/**
	 * Returns the end hour as a string
	 * 
	 * @return the end hour as a string
	 */
	public String getFormattedEndHour() {
		NumberFormat nf = new DecimalFormat("##00");
		String hours = nf.format(mEndHour / 3600);
		String minutes = nf.format((mEndHour % 3600) / 60);
		String seconds = nf.format(mEndHour % 60);
		return hours + ":" + minutes + ":" + seconds;
	}

	/**
	 * Returns the end hour
	 * 
	 * @return the end hour
	 */
	public int getEndHour() {
		return mEndHour;
	}

	/**
	 * Add a delivery to the time slot
	 * 
	 * @param delivery
	 *            Delivery to add
	 */
	public void addDelivery(Delivery delivery) {
		mDeliveryList.add(delivery);
	}

	/**
	 * Remove a delivery from the time slot's delivery list
	 * 
	 * @param delivery
	 *            Delivery to remove
	 */
	public void removeDelivery(Delivery delivery) {
		mDeliveryList.remove(delivery);
	}

	/**
	 * Fills the time slot objects with data from XML files.
	 * 
	 * @param timeSlotElement
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
	public String buildFromXML(Element timeSlotElement, Network network,
			String listClientsWithSeveralAdresses,
			Map<Integer, Node> map_clientAdress, List<Integer> list_allAdress)
			throws InvalidDeliveryRequestFileException {

		mStartHour = stringToCustomTimestamp(timeSlotElement
				.getAttribute("heureDebut"));
		mEndHour = stringToCustomTimestamp(timeSlotElement
				.getAttribute("heureFin"));

		// Check if time slots are not well concived
		String ex;
		if (mStartHour > mEndHour) {
			ex = "Une des plages horaires est mal construite et commence après avoir fini...";
			throw new InvalidDeliveryRequestFileException(ex);
		} else if (mStartHour == mEndHour) {
			ex = "Une des plages horaires est mal construite et l'heure de début est identique à l'heure de fin";
			throw new InvalidDeliveryRequestFileException(ex);
		}

		NodeList listDeliveries = timeSlotElement
				.getElementsByTagName("Livraison");
		Integer deliveriesNumber = listDeliveries.getLength();

		for (int i = 0; i < deliveriesNumber; i++) {
			Delivery delivery = new Delivery(this);
			Element deliveryElement = (Element) listDeliveries.item(i);

			// Check 1-1 between Client and Adress
			try {
				String tmp = delivery.buildFromXML(deliveryElement, network,
						listClientsWithSeveralAdresses, map_clientAdress,
						list_allAdress);
				if (tmp != "OK") {
					listClientsWithSeveralAdresses = tmp;
				}
			} catch (InvalidDeliveryRequestFileException iDRFE) {
				throw new InvalidDeliveryRequestFileException(
						iDRFE.getMessage());
			}
			mDeliveryList.add(delivery);
		}
		return listClientsWithSeveralAdresses;

	}

	private int stringToCustomTimestamp(String hour) {
		// hour must be of format 'H(H):m(m):s(s)' or 'H(H):m(m)'
		String[] parsedDate = hour.split(":");
		int res = 0;
		for (int i = 0; i < parsedDate.length; i++) {
			switch (i) {
			case 0:
				res += Integer.parseInt(parsedDate[i]) * 3600;
				break;
			case 1:
				res += Integer.parseInt(parsedDate[i]) * 60;
				break;
			case 2:
				res += Integer.parseInt(parsedDate[i]);
				break;
			}

		}
		return res;
	}

	@Override
	public String toString() {
		return "(Time Slot : startHour " + mStartHour + ", endHour " + mEndHour
				+ "Deliveries " + mDeliveryList.toString() + "); ";
	}

	/**
	 * Returns the color associated with the time slot
	 * 
	 * @return the color associated with the time slot
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets a color for this time slot
	 * 
	 * @param i
	 *            Number associated with the color to set
	 */
	private void setColor(int i) {
		if (i < 0) {
			i = 0;
		}
		while (i > COLORS.length) {
			i -= COLORS.length;
		}
		this.color = COLORS[i];
	}

	@Override
	public String buildFromXML(Element element, Network network)
			throws InvalidDeliveryRequestFileException,
			WarningDeliveryRequestFile {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * compareTo implementation for TimeSlots. Used to sort the TimeSlots Array
	 * member in DeliveryRequest.
	 * 
	 * @param arg0
	 *            TimeSlot to compare to the current object
	 * @return difference 0 if current object equals argument, positive if
	 *         current object greater, negative if current object less than
	 *         argument.
	 */
	@Override
	public int compareTo(TimeSlot arg0) {
		return this.mStartHour - arg0.getStartHour();
	}

}
