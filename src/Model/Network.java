package Model;

import java.util.*;

/**
 * 
 */
public class Network {

    /**
     * 
     */
    public Network() {
    }


    /**
     * 
     */
    protected Set<Segment> mSegmentList;

    /**
     * 
     */
    protected DeliveryRequest[] mDeliveryRequest;

    /**
     * 
     */
    protected Set<Node> mWarehouseList;

    /**
     * @param Node n1 
     * @param Node n2 
     * @return
     */
    public Path calculateShorterPath(void Node n1, void Node n2) {
        // TODO implement here
        return null;
    }

    /**
     * @param Node previous 
     * @param Node selected 
     * @return
     */
    public void addDelivery(void Node previous, void Node selected) {
        // TODO implement here
        return null;
    }

    /**
     * @param int id 
     * @return
     */
    public Node getNode(void int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param File 
     * @return
     */
    public void parseDeliveryRequestFile(void File) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Element getDocumentRoot() {
        // TODO implement here
        return null;
    }

    /**
     * @param File 
     * @return
     */
    public int parseNetworkFile(void File) {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public Node getSelectedNode() {
        // TODO implement here
        return null;
    }

}