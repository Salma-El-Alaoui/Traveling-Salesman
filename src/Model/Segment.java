package Model;

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


	private String mStreetName;


	/**
     * 
     */

    protected Node mArrivalNode;

	private float mSpeed;
	
	private float mLength;


	/**
     * 
     */
    public Node getDepartureNode() {
        return mDepartureNode;
    }
    
    /**
     * @return
     */
    public Node getArrivalNode() {
        return mArrivalNode;
    }
	


    
 
  

	
	/**
	 * @return
	 */
	public float getTime() {

		return mLength / mSpeed;
	}

	/**
	 * @param Node
	 * @param Element
	 */
	public void buildFromXML(Node departureNode, Element segmentElement,
			Network network) {
		
		mDepartureNode = departureNode;

		mStreetName = segmentElement.getAttribute("nomRue");

		mSpeed = Float.parseFloat(segmentElement.getAttribute("vitesse").replace(',', '.'));

		mLength = Float.parseFloat(segmentElement.getAttribute("longueur").replace(',', '.'));

		mArrivalNode = network.getNode(Integer.parseInt(segmentElement
				.getAttribute("idNoeudDestination")));
		

		network.updateNode(mDepartureNode.getId(), null , this); // This segment is the out segment of its FromNode
		network.updateNode(mArrivalNode.getId(), this, null); // This segment is the in segments of its ToNode.

	}
	@Override
	public String toString() {
		
		return "Segment : street " + mStreetName + ", length " + mLength + ", speed " + mSpeed ;
		//return  "Segment : "+ " From ("+ mFromNode.toString() + " ), To (" + mToNode.toString() +  ") ,street " + mStreetName + ", length " + mLength + ", speed " + mSpeed ;
	}

}