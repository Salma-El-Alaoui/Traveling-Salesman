package Model;

import java.util.*;

/**
 * 
 */
public class Node implements XmlParse {

    /**
     * 
     */
    public Node() {
    }

    /**
     * 
     */
    protected boolean mIsWareHouse;

    /**
     * 
     */
    protected String mAddress;

    /**
     * 
     */
    protected int mX;

    /**
     * 
     */
    protected int mY;

    /**
     * 
     */
    protected int mId;


    /**
     * 
     */
    protected Network[] mNetwork;


    /**
     * 
     */
    protected Segment mInSegment;

    /**
     * 
     */
    protected Set<Segment> mOutSegment;

    /**
     * @return
     */
    public boolean hasDelivery() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public boolean isSelected() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public Network getNetwork() {
        // TODO implement here
        return null;
    }

    /**
     * @param boolean 
     * @return
     */
    public void setSelectedNode(void boolean) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Delivery getDelivery() {
        // TODO implement here
        return null;
    }

    /**
     * @param boolean 
     * @return
     */
    public void setIsWareHouse(void boolean) {
        // TODO implement here
        return null;
    }

}