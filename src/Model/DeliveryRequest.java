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
     * Create a delivery associated with previousNode and
     * insert it into tour after the delivery associated with previousNode
     * @param previousNode
     * @param selectedNode
     */
    public boolean insertDelivery(Node previousNode, Node selectedNode) {
        Delivery previousDelivery=previousNode.getDelivery();
        Delivery newDelivery=new Delivery(selectedNode);
        return mTour.insertDelivery(previousDelivery, newDelivery);
    }

    /**
     * Remove from the tour the delivery associated with the node
     * @param Node node associated with the delivery to remove
     * @return the node before the removed delivery
     */
    public Node removeDelivery(Node node) {
        return mTour.removeDelivery(node.getDelivery());
    }

	@Override
	public int buildFromXML(Element element) {
		// TODO Auto-generated method stub
		return 0;
	}

}