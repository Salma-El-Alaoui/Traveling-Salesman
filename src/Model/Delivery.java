package Model;

import java.util.*;

/**
 * 
 */
public class Delivery {

	private static final int DELIVERY_TIME = 10*60;
	
    /**
     * 
     */
    public Delivery() {
    }
    
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
     * Departure hour
     */
    protected int mDepartureHour;

    /**
     * Arrival hour
     */
    protected int mArrivalHour;

    /**
     * 
     */
    protected TimeSlot mTimeSlot;
    
    /**
     * 
     */
    protected Node mNode;

    /**
     * @return
     */
    public Node getNode() {
        return mNode;
    }

    /**
     * @return
     */
    public Delivery getDelivery() {
        // TODO implement here
        return null;
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
        // TODO implement here
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

    /**
     * 
     */
    public TimeSlot getTimeSlot() {
        return mTimeSlot;
    }

}