package Model;

import java.util.*;

/**
 * 
 */
public class Delivery {

    /**
     * 
     */
    public Delivery() {
    }
    
    /**
     * 
     */
    public Delivery(Node node)
    {
    	mNode=node;
    }

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
     * @param TimeSlot timeSlot
     */
    public void setTimeSlot(TimeSlot timeSlot) {
        // TODO implement here
    }

    /**
     * 
     */
    public TimeSlot getTimeSlot() {
        return mTimeSlot;
    }

}