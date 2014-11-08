package Model;

import java.util.*;
import org.w3c.dom.Element;

/**
 * 
 */
public class Segment {

    /**
     * 
     */
    public Segment() {
    }

    /**
     * 
     */
    protected Node mDepartureNode;

    /**
     * 
     */
    protected Node mArrivalNode;

    /**
     * @return
     */
    public Node getDepartureNode() {
        return mDepartureNode;
    }

    /**
     * @return
     */
    public int getTime() {
        // TODO implement here
        return 0;
    }

    /**
     * @param Node 
     * @param Element
     */
    public void buildFromXML(Node node, Element element) {
        // TODO implement here
    }

}