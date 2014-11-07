package Model;

import java.util.*;

/**
 * 
 */
public class DeliveryRequest implements XmlParse {

    /**
     * 
     */
    public DeliveryRequest() {
    }

    /**
     * 
     */
    protected Node[] mWarehouse;

    /**
     * 
     */
    protected Set<TimeSlot> mTimeSlotList;

    /**
     * 
     */
    protected Tour[] mTour;

    /**
     * @param Network network
     */
    public void calculateTour(void Network network) {
        // TODO implement here
    }

    /**
     * @param Node previous 
     * @param Node next 
     * @return
     */
    public void insertDelivery(void Node previous, void Node next) {
        // TODO implement here
        return null;
    }

    /**
     * @param Delivery delivery 
     * @return
     */
    public void removeDelivery(void Delivery delivery) {
        // TODO implement here
        return null;
    }

}