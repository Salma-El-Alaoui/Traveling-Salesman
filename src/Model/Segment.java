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
    protected Node[] mFromNode;

    /**
     * 
     */
    protected Node[] mToNode;

    /**
     * @return
     */
    public Node getDepartureNode() {
        // TODO implement here
        return null;
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