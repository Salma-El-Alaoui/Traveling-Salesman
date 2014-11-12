package Model;

import java.awt.Color;
import java.util.List;

import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 */
public class TimeSlot implements XmlParse {

	protected Color color;

	public TimeSlot() {
		mDeliveryList = new ArrayList<Delivery>();
		setColor();
	}

	/**
     * 
     */
	protected int mStartHour;

	/**
     * 
     */
	protected int mEndHour;

	/**
     * 
     */

	protected List<Delivery> mDeliveryList;

	/**
	 * @return
	 */
	public List<Delivery> getAllDeliveries() {
		return mDeliveryList;
	}

	/**
	 * @return
	 */
	public int getStartHour() {
		return mStartHour;
	}

	public int getEndHour() {
		return mEndHour;
	}

	@Override
	public String buildFromXML(Element timeSlotElement, Network network) {

		mStartHour = stringToCustomTimestamp(timeSlotElement
				.getAttribute("heureDebut"));
		mEndHour = stringToCustomTimestamp(timeSlotElement
				.getAttribute("heureFin"));

		NodeList listDeliveries = timeSlotElement
				.getElementsByTagName("Livraison");
		Integer deliveriesNumber = listDeliveries.getLength();

		for (int i = 0; i < deliveriesNumber; i++) {
			Delivery delivery = new Delivery(this);
			Element deliveryElement = (Element) listDeliveries.item(i);

			delivery.buildFromXML(deliveryElement, network);

			mDeliveryList.add(delivery);
		}

		return "";

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

	public Color getColor() {
		return color;
	}

	private void setColor() {
		Random random = new Random();
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);

		Color mix = new Color(32, 178, 170); // Base color
		// mix the color
		if (mix != null) {
			red = (red + mix.getRed()) / 2;
			green = (green + mix.getGreen()) / 2;
			blue = (blue + mix.getBlue()) / 2;
		}

		this.color = new Color(red, green, blue);

	}

}
