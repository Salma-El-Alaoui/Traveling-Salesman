package Model;

import java.util.*;

/**
 * 
 */
public class TimeSlot implements XmlParse {

    /**
     * 
     */
    public TimeSlot() {
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
    protected Set<Delivery> mDeliveryList;

    /**
     * @return
     */
    public List<Delivery> getAllDeliveries() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public int getStartHour() {
        // TODO implement here
        return 0;
    }

}