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
    private List<Segment> mInSegmentList;

    /**
     * 
     */


    
    /**
     * 
     */
    protected boolean mIsSelected;

    private List<Segment> mOutSegmentList;

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
    		mNetwork.setSelectedNode(this);
    	}  	      
    }
    

    /**
     * @return
     */
    public Delivery getDelivery() {
        return mDelivery;
    }
    
    /**
     * @return
     */
    public List<Segment> getOutSegmentList(){
    	return mOutSegmentList;
    }
    
    /**
     * @return
     */
    public List<Segment> getInSegmentList(){
    	return mInSegmentList;
    }
        
    /**
     * @return
     */
    public int getId(){
    	return mId;
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