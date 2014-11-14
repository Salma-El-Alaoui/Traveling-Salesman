package Model;

import org.w3c.dom.Element;

/**
 * Class that represents a segment
 */
public class Segment {

	static int ERROR_XML_SEGMENT_NODE_DESTINATION_SAME_AS_DEPARTURE = 0;

	/**
	 * Constructor
	 */
	public Segment() {
	}

	/**
	 * Node where the segment begins
	 */
	protected Node mDepartureNode;

	/**
	 * Name of the street containing the segment
	 */
	private String mStreetName;

	/**
	 * Node where the segment ends
	 */
	protected Node mArrivalNode;

	/**
	 * Driving speed on this segment
	 */
	private float mSpeed;

	/**
	 * Length of the segment
	 */
	private float mLength;

	/**
	 * Returns the node at the beginning of the segment
	 * 
	 * @return departure node
	 */
	public Node getDepartureNode() {
		return mDepartureNode;
	}

	/**
	 * Returns the node at the end of the segment
	 * 
	 * @return arrival node
	 */
	public Node getArrivalNode() {
		return mArrivalNode;
	}

	/**
	 * Returns the name of the street containing the segment
	 * 
	 * @return the name of the street containing the segment
	 */
	public String getStreetName() {
		return mStreetName;
	}

	/**
	 * Returns the length of the segment
	 * 
	 * @return the length of the segment
	 */
	public float getLength() {
		return mLength;
	}

	/**
	 * Returns the time needed to reach the end of the segment
	 * 
	 * @return the time needed to reach the end of the segment
	 */
	public float getTime() {
		return mLength / mSpeed;
	}

	/**
	 * Fills the segment object with data from XML.
	 * 
	 * @param Node
	 *            The departure node
	 * @param Element
	 *            XML element to be parsed.
	 * @param Network
	 *            the network.
	 */
	public int buildFromXML(Node departureNode, Element segmentElement,
			Network network) {

		int res = -1;
		int noeudDestinationInt = Integer.parseInt(segmentElement
				.getAttribute("idNoeudDestination"));

		if (noeudDestinationInt == departureNode.getId()) {
			ERROR_XML_SEGMENT_NODE_DESTINATION_SAME_AS_DEPARTURE++;
			res = noeudDestinationInt;
			System.out.println("res : " + res);
			System.out.println("noeudDestinationInt : " + noeudDestinationInt);
		}

		mDepartureNode = departureNode;

		mStreetName = segmentElement.getAttribute("nomRue");

		mSpeed = Float.parseFloat(segmentElement.getAttribute("vitesse")
				.replace(',', '.'));

		mLength = Float.parseFloat(segmentElement.getAttribute("longueur")
				.replace(',', '.'));

		mArrivalNode = network.getNode(Integer.parseInt(segmentElement
				.getAttribute("idNoeudDestination")));

		network.updateNode(mDepartureNode.getId(), null, this); // This segment
																// is the out
																// segment of
																// its FromNode
		network.updateNode(mArrivalNode.getId(), this, null); // This segment is
																// the in
																// segments of
																// its ToNode.

		return res;
	}

	@Override
	public String toString() {
		return "Segment : street " + mStreetName + ", length " + mLength
				+ ", speed " + mSpeed;
		// return "Segment : "+ " From ("+ mFromNode.toString() + " ), To (" +
		// mToNode.toString() + ") ,street " + mStreetName + ", length " +
		// mLength + ", speed " + mSpeed ;
	}

}