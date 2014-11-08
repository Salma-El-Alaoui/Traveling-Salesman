package Model;

import java.util.*;

import org.w3c.dom.Element;

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
    public void setSelectedNode(boolean isSelected) {
        // TODO implement here
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
    public void setIsWareHouse(boolean isWareHouse) {
        // TODO implement here
    }

	@Override
	public int buildFromXML(Element element) {
		// TODO Auto-generated method stub
		return 0;
	}

}