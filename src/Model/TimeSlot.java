package Model;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 */
public class TimeSlot implements XmlParse {

	private static final Color[] COLORS = {
		new Color(248, 148, 6),
		new Color(30, 130, 76),
		new Color(65, 131, 215),
		new Color(242, 38, 19),
		new Color(103, 65, 114),
		new Color(219, 10, 91)
	};
	
	protected Color color;

	public TimeSlot(int i) {
		mDeliveryList = new ArrayList<Delivery>();
		setColor(i);
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

	public String getFormattedStartHour() {
		NumberFormat nf = new DecimalFormat("##00");
		String hours = nf.format(mStartHour / 3600);
		String minutes = nf.format((mStartHour % 3600) / 60);
		String seconds = nf.format(mStartHour % 60);
		return hours + ":" + minutes + ":" + seconds;
	}

	public String getFormattedEndHour() {
		NumberFormat nf = new DecimalFormat("##00");
		String hours = nf.format(mEndHour / 3600);
		String minutes = nf.format((mEndHour % 3600) / 60);
		String seconds = nf.format(mEndHour % 60);
		return hours + ":" + minutes + ":" + seconds;
	}

	public int getEndHour() {
		return mEndHour;
	}
	
	public void addDelivery(Delivery delivery)
	{
		mDeliveryList.add(delivery);
	}
	
	public void removeDelivery(Delivery delivery)
	{
		mDeliveryList.remove(delivery);
	}

	@Override
	public String buildFromXML(Element timeSlotElement, Network network, String listClientsWithSeveralAdresses, Map<Integer, Node> map_clientAdress) throws InvalidDeliveryRequestFileException{

		mStartHour = stringToCustomTimestamp(timeSlotElement
				.getAttribute("heureDebut"));
		mEndHour = stringToCustomTimestamp(timeSlotElement
				.getAttribute("heureFin"));

		// Check if time slots are not well concived
		String ex;
		if (mStartHour>mEndHour){
			ex = "Une des plages horaires est mal construite et commence apr�s avoir fini...";
			throw new InvalidDeliveryRequestFileException(ex);	
		} else if (mStartHour==mEndHour){
			ex = "Une des plages horaires est mal construite et l'heure de d�but est identique � l'heure de fin";
			throw new InvalidDeliveryRequestFileException(ex);	
		}


		NodeList listDeliveries = timeSlotElement
				.getElementsByTagName("Livraison");
		Integer deliveriesNumber = listDeliveries.getLength();

		for (int i = 0; i < deliveriesNumber; i++) {
			Delivery delivery = new Delivery(this);
			Element deliveryElement = (Element) listDeliveries.item(i);

			// Check one client has only one adress
			try {
				String tmp = delivery.buildFromXML(deliveryElement, network, listClientsWithSeveralAdresses, map_clientAdress );
				if (tmp != "OK"){
					listClientsWithSeveralAdresses = tmp;
				}
			} catch (InvalidDeliveryRequestFileException iDRFE){
				throw new InvalidDeliveryRequestFileException(iDRFE.getMessage());
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

	public Color getColor() {
		return color;
	}

	private void setColor(int i) {
		if(i<0){
			i = 0;
		}
		while(i>COLORS.length){
			i-=COLORS.length;
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

}
