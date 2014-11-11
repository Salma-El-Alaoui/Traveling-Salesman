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
	private String mStreetName;

	/**
     * 
     */
	private float mSpeed;

	/**
     * 
     */
	private float mLength;

	/**
     * 
     */
	protected Node mFromNode;

	/**
     * 
     */
	protected Node mToNode;

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
	public float getTime() {

		return mLength / mSpeed;
	}

	/**
	 * @param Node
	 * @param Element
	 */
	public void buildFromXML(Node departureNode, Element segmentElement,
			Network network) {
		
		mFromNode = departureNode;

		mStreetName = segmentElement.getAttribute("nomRue");

		mSpeed = Float.parseFloat(segmentElement.getAttribute("vitesse").replace(',', '.'));

		mLength = Float.parseFloat(segmentElement.getAttribute("longueur").replace(',', '.'));

		mToNode = network.getNode(Integer.parseInt(segmentElement
				.getAttribute("idNoeudDestination")));
		

		network.updateNode(mFromNode.getId(), null , this); // This segment is the out segment of its FromNode
		network.updateNode(mToNode.getId(), this, null); // This segment is the in segments of its ToNode.

	}
	@Override
	public String toString() {
		
		return "Segment : street " + mStreetName + ", length " + mLength + ", speed " + mSpeed ;
		//return  "Segment : "+ " From ("+ mFromNode.toString() + " ), To (" + mToNode.toString() +  ") ,street " + mStreetName + ", length " + mLength + ", speed " + mSpeed ;
	}

}