package Model;

import java.io.File;
import java.util.*;

import org.w3c.dom.Element;

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
    protected List<Segment> mSegmentList;

    /**
     * 
     */
    protected DeliveryRequest[] mDeliveryRequest;

    /**
     * 
     */
    protected List<Node> mWarehouseList;

    /**
     * @param Node n1 
     * @param Node n2 
     * @return
     */
    public Path calculateShortestPath(Node n1, Node n2) {
        // TODO implement here
        return null;
    }

    /**
     * @param Node previous 
     * @param Node selected 
     * @return
     */
    public void addDelivery(Node previous, Node selected) {
        // TODO implement here
    }
    
    /**
     * @param Node node 
     * @return
     */
    public void removeDelivery(Node node) {
        // TODO implement here
    }

    /**
     * @param int id 
     * @return
     */
    public Node getNode(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param File 
     * @return
     */
    public void parseDeliveryRequestFile(File file) {
        // TODO implement here
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
    public int parseNetworkFile(File file) {
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