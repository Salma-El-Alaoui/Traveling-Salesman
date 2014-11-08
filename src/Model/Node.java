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
    protected Delivery mDelivery;

    /**
     * 
     */
    protected Network mNetwork;


    /**
     * 
     */
    protected Segment mInSegment;

    /**
     * 
     */
    protected List<Segment> mOutSegment;
    
    /**
     * 
     */
    protected boolean mIsSelected;

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
        return mIsSelected;
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
    	mIsSelected=isSelected;
    	if(isSelected)
    	{
    		mNetwork.getSelectedNode().setSelectedNode(false);
    		mNetwork.setSelectedNode(this);
    	}  	      
    }
    
    /**
     * 
     */
    public int getId()
    {
    	return mId;
    }

    /**
     * @return
     */
    public Delivery getDelivery() {
        return mDelivery;
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