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
    	mInSegmentList = new ArrayList<Segment>();
    	mOutSegmentList = new ArrayList<Segment>();
    }

    /**
     * 
     */
    protected boolean mIsWarehouse;

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
    public boolean isWarehouse() {
        // TODO implement here
        return mIsWarehouse;
    }

    public void addInSegment(Segment segment){
    	mInSegmentList.add(segment);
    }

    public void addOutSegment(Segment segment){
    	mOutSegmentList.add(segment);
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
    public void setIsWarehouse(boolean isWarehouse) {
        mIsWarehouse = isWarehouse;
    }

	@Override
	public String buildFromXML(Element nodeElement, Network network) {
		this.mNetwork = network;
		
		this.mId = Integer.parseInt(nodeElement.getAttribute("id"));
		
		this.mX = Integer.parseInt(nodeElement.getAttribute("x"));
		
		this.mY = Integer.parseInt(nodeElement.getAttribute("y"));
			
		
		return "OK";
	}
	@Override
	public String toString() {
		try{
			return "NODE : ID="+ this.mId +", X= "+  this.mX+", Y= " + mY + ", InSegment " + mInSegmentList.toString() + ", outSegment" + mOutSegmentList.toString();
		} catch(NullPointerException npx){
			return "NODE : ID="+ this.mId +", X= "+  this.mX+", Y= " + mY;
		}
		
	}
	
    public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}

	public void setDelivery(Delivery delivery) {
		mDelivery = delivery;
	}

}