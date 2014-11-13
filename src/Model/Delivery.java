package Model;

import org.w3c.dom.Element;

/**
 * 
 */
public class Delivery implements XmlParse {


	private static final int DELIVERY_TIME = 10*60;



	/**
	 * @param node
	 */
	public Delivery(Node node)
	{
		node.setDelivery(this);
		mNode=node;
	}

	/**
	 * Delivery hour
	 */
	protected int mDeliveryHour;

	/**
	 * 
	 */
	protected Node mNode;

	/**
	 * Departure hour
	 */
	protected int mDepartureHour;

	/**
	 * Arrival hour
=======
	/**
	 * 
	 */
	public Delivery() {
	}

	public Delivery(TimeSlot timeSlot) {
		mTimeSlot = timeSlot;
		}

	protected int mId;


	protected int mArrivalHour;


	protected int mClient;

	/**
	 * 
	 */

	protected TimeSlot mTimeSlot;

	/**
	 * @return
	 */
	public Node getNode() {
		return mNode;
	}

	public int getClient() {
		return mClient;
	}



	/**
	 * @return Arrival Hour
	 */
	public int getArrivalHour() {
		return mArrivalHour;
	}
	public String getFormattedArrivalHour(){
		String hours = ((Integer) (mArrivalHour / 3600)).toString();
		String minutes = ((Integer) ((mArrivalHour % 3600) / 60)).toString();
		String seconds = ((Integer) (mArrivalHour % 60)).toString();
		return hours + ":" + minutes + ":" + seconds;
	}
	

	/**
	 * @return Delivery Hour
	 */
	public int getDeliveryHour() {
		return mDeliveryHour;
	}
	public String getFormattedDeliveryHour(){
		String hours = ((Integer) (mDeliveryHour / 3600)).toString();
		String minutes = ((Integer) ((mDeliveryHour % 3600) / 60)).toString();
		String seconds = ((Integer) (mDeliveryHour % 60)).toString();
		return hours + ":" + minutes + ":" + seconds;
	}

	/**
	 * @return Departure Hour
	 */
	public int getDepartureHour() {
		return mDepartureHour;
	}

	public String getFormattedDepartureHour(){
		String hours = ((Integer) (mDepartureHour / 3600)).toString();
		String minutes = ((Integer) ((mDepartureHour % 3600) / 60)).toString();
		String seconds = ((Integer) (mDepartureHour % 60)).toString();
		return hours + ":" + minutes + ":" + seconds;
	}

	/**
	 * @param TimeSlot timeSlot
	 */
	public void setTimeSlot(TimeSlot timeSlot) {
		// TODO add delivery to timeSlot node
		mTimeSlot = timeSlot;
	}

	/**
	 * Update all the hours according to the arrival hour
	 * @param arrivalHour
	 */
	public void setArrivalHour(int arrivalHour){
		mArrivalHour = arrivalHour;
		if(mArrivalHour < mTimeSlot.getStartHour()){
			mDeliveryHour = mTimeSlot.getStartHour();
		} else {
			mDeliveryHour = arrivalHour;
		}
		mDepartureHour = mDeliveryHour + DELIVERY_TIME;
	}




	public TimeSlot getTimeSlot() {
		return mTimeSlot;
	}



	@Override
	public String buildFromXML(Element deliveryElement, Network network) {
		mId = Integer.parseInt(deliveryElement.getAttribute("id"));
		mClient = Integer.parseInt(deliveryElement.getAttribute("client"));
		int nodeId = Integer.parseInt(deliveryElement.getAttribute("adresse"));

		mNode = network.getNode(nodeId);
		mNode.setDelivery(this);


		return null;
	}
	@Override
	public String toString() {
		return "(Delivery : ID " + mId + " ,Node " + mNode + " ,Client " + mClient + ");";
	}


}