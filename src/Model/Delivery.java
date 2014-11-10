package Model;

import org.w3c.dom.Element;

/**
 * 
 */
public class Delivery implements XmlParse {

	/**
     * 
     */
	public Delivery() {
	}
	
	protected int mId;

	/**
     * 
     */
	protected int mDeliveryHour;

	/**
     * 
     */
	protected int mDepartureHour;

	/**
     * 
     */
	protected int mArrivalHour;
	
	protected Node mNode;
	
	protected int mClient;

	/**
     * 
     */
	protected TimeSlot[] mTimeSlot;

	/**
	 * @return
	 */
	public Node getNode() {
		// TODO implement here
		return null;
	}

	/**
	 * @return
	 */
	public Delivery getDelivery() {
		// TODO implement here
		return null;
	}

	/**
	 * @return
	 */
	public int getArrivalHour() {
		// TODO implement here
		return 0;
	}

	/**
	 * @return
	 */
	public int getDepartureHour() {
		// TODO implement here
		return 0;
	}

	/**
	 * @param TimeSlot
	 *            timeSlot
	 */
	public void setTimeSlot(TimeSlot timeSlot) {
		// TODO implement here
	}

	/**
     * 
     */
	public void getTimeSlot() {
		// TODO implement here
	}

	@Override
	public String buildFromXML(Element deliveryElement, Network network) {
		mId = Integer.parseInt(deliveryElement.getAttribute("id"));
		mClient = Integer.parseInt(deliveryElement.getAttribute("client"));
		int nodeId = Integer.parseInt(deliveryElement.getAttribute("adresse"));

		mNode = network.getNode(nodeId);

	
		return null;
	}

}