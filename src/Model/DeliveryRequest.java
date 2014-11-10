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
    protected Node mWarehouse;

    /**
     * 
     */
    protected Set<TimeSlot> mTimeSlotList;

    /**
     * 
     */
    protected Tour mTour;

    /**
     * @param Network network
     */
    public void calculateTour(Network network) {
        // TODO implement here
    }

    /**
     * @param Node previous 
     * @param Node  
     * @return
     */
    public void insertDelivery(Node previousNode, Node selectedNode) {
        Delivery previousDelivery=previousNode.getDelivery();
        Delivery newDelivery=new Delivery(selectedNode);
        mTour.insertDelivery(previousDelivery, newDelivery);
    }

    /**
     * @param Node node 
     * @return
     */
    public void removeDelivery(Node node) {
        mTour.removeDelivery(node.getDelivery());
    }

	@Override
	public int buildFromXML(Element element) {
		// TODO Auto-generated method stub
		return 0;
	}

}