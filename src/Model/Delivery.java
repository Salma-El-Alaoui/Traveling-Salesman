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





	/**
	 * @return Arrival Hour
	 */
	public int getArrivalHour() {
		return mArrivalHour;
	}

	/**
	 * @return Delivery Hour
	 */
	public int getDeliveryHour() {
		return mDeliveryHour;
	}

	/**
	 * @return Departure Hour
	 */
	public int getDepartureHour() {
		return mDepartureHour;
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
	public String buildFromXML(Element deliveryElement, Network network) throws InvalidDeliveryRequestFileException{
		mId = Integer.parseInt(deliveryElement.getAttribute("id"));
		mClient = Integer.parseInt(deliveryElement.getAttribute("client"));
		int nodeId = Integer.parseInt(deliveryElement.getAttribute("adresse"));

		mNode = network.getNode(nodeId);

		if (mNode == null){
			throw new InvalidDeliveryRequestFileException("Le noeud " + nodeId + " dans les demandes de Livraisons n'existe pas dans le Réseau");
		}
		
		return null;
	}
	@Override
	public String toString() {
		return "(Delivery : ID " + mId + " ,Node " + mNode + " ,Client " + mClient + ");";
	}


}