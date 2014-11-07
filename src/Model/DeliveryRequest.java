package Model;

import java.util.*;

import org.w3c.dom.Element;

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
    public void calculateTour(Network network) {
        // TODO implement here
    }

    /**
     * @param Node previous 
     * @param Node next 
     * @return
     */
    public void insertDelivery(Node previous, Node next) {
        // TODO implement here
    }

    /**
     * @param Node node 
     * @return
     */
    public void removeDelivery(Node node) {
        // TODO implement here
    }

	@Override
	public int buildFromXML(Element element) {
		// TODO Auto-generated method stub
		return 0;
	}

}