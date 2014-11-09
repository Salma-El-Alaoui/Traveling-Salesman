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
    protected Node mSelectedNode;

    /**
     * 
     */
    protected DeliveryRequest mDeliveryRequest;

    /**
     * 
     */
    protected List<Node> mWarehouseList;

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
        mDeliveryRequest.insertDelivery(previous, selected);
    }
    
    /**
     * @param Node node 
     * @return
     */
    public void removeDelivery(Node node) {
        mDeliveryRequest.removeDelivery(node);
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
        return mSelectedNode;
    }
    
    public void setSelectedNode(Node node){
    	mSelectedNode=node;
    }

}