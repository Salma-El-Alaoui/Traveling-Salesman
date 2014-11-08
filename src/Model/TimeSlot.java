package Model;

import java.awt.Color;
import java.util.List;

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
    
    protected Color color;

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

	@Override
	public int buildFromXML(Element element) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Color getColor() {
		return color;
	}
	

}