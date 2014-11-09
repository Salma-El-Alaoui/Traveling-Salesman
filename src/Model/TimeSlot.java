package Model;

import java.util.*;

import org.w3c.dom.Element;

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
        return mStartHour;
    }
    
    public int getEndHour() {
        return mEndHour;
    }

	@Override
	public int buildFromXML(Element element) {
		// TODO Auto-generated method stub
		return 0;
	}

}