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
    	mInSegment = new ArrayList<Segment>();
    	mOutSegment = new ArrayList<Segment>();
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
    protected Network mNetwork;


    /**
     * 
     */
    protected List<Segment> mInSegment;

    /**
     * 
     */
    protected List<Segment> mOutSegment;
    
    public int getId() {
		return mId;
	}

    /**
     * @return
     */
    public boolean hasDelivery() {
        // TODO implement here
        return false;
    }
    
    public void addInSegment(Segment segment){
    	mInSegment.add(segment);
    }

    public void addOutSegment(Segment segment){
    	mOutSegment.add(segment);
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
			return "NODE : ID="+ this.mId +", X= "+  this.mX+", Y= " + mY + ", InSegment " + mInSegment.toString() + ", outSegment" + mOutSegment.toString();
		} catch(NullPointerException npx){
			return "NODE : ID="+ this.mId +", X= "+  this.mX+", Y= " + mY;
		}
		
	}

}