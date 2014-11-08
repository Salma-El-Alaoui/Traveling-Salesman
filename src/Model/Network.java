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
     * Calculate the shortest path from startNode to endNode
     * @param originNode
     * @param endNode 
     * @return The shortest path, null if no path can be found
     * @deprecated Prefer to use Dijkstra class to improve performance (in case of multiples calls with the same start)
     */
    @Deprecated
    public Path calculateShortestPath(Node originNode, Node endNode) {
    	Dijkstra d = new Dijkstra(originNode);
    	return d.calculateShortestPathTo(endNode);
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