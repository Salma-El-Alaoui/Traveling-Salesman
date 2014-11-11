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
		System.out.println("Here " + mStreetName + " " + mSpeed + mFromNode + mToNode);

		/*// Update From Node DOES NOT WORK
		network.getNode(mFromNode.getId()).addOutSegment(this);

		// Update To Node DOES NOT WORK
		network.getNode(mFromNode.getId()).addInSegment(this);*/

	}
	@Override
	public String toString() {
		
		return  "Segment : "+  " ,street " + mStreetName + ", length " + mLength + ", speed " + mSpeed ;
	}

}