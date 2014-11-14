package Model;

import java.util.*;

import org.w3c.dom.Element;

/**
 * Class that represents a node
 */
public class Node implements XmlParse {

	/**
	 * Constructor
	 */
	public Node() {
		mInSegmentList = new ArrayList<Segment>();
		mOutSegmentList = new ArrayList<Segment>();
	}

	/**
	 * True if the node is a warehouse
	 */
	private boolean mIsWarehouse;

	/**
	 * Address of the node
	 */
	private String mAddress;

	/**
	 * Coordinate X
	 */
	private int mX;

	/**
	 * Coordinate Y
	 */
	private int mY;

	/**
	 * Id of the node
	 */
	private int mId;

	/**
	 * Delivery associated with the node
	 */
	private Delivery mDelivery;

	/**
	 * Network containing the node
	 */
	private Network mNetwork;

	/**
	 * List of segment coming in the node
	 */
	private List<Segment> mInSegmentList;

	/**
	 * True if the node is selected
	 */
	private boolean mIsSelected;

	/**
	 * List of segments coming out of the node
	 */
	private List<Segment> mOutSegmentList;

	/**
	 * Returns true if the node is associated with a delivery
	 * 
	 * @return true if the node is associated with a delivery
	 */
	public boolean hasDelivery() {
		return mDelivery != null;
	}

	/**
	 * Returns true if the node is a warehouse
	 * 
	 * @return true if the node is a warehouse
	 */
	public boolean isWarehouse() {
		return mIsWarehouse;
	}

	/**
	 * Adds an incoming segment to the node
	 * 
	 * @param segment
	 *            Segment to add
	 */
	public void addInSegment(Segment segment) {
		mInSegmentList.add(segment);
	}

	/**
	 * Adds an outcoming segment to the node
	 * 
	 * @param segment
	 *            Segment to add
	 */
	public void addOutSegment(Segment segment) {
		mOutSegmentList.add(segment);
	}

	/**
	 * Returns true if the node is selected
	 * 
	 * @return true if the node is selected
	 */
	public boolean isSelected() {
		return mIsSelected;
	}

	/**
	 * Returns the network containing the node
	 * 
	 * @return the network containing the node
	 */
	public Network getNetwork() {
		return mNetwork;
	}

	/**
	 * Sets the node as selected if isSelected is true, sets it as not selected
	 * otherwise
	 * 
	 * @param isSelected
	 *            whether the node should be set as selected
	 */
	public void setSelectedNode(boolean isSelected) {
		mIsSelected = isSelected;
	}

	/**
	 * Returns the delivery associated with the node
	 * 
	 * @return the delivery associated with the node, null if there isn't one
	 */
	public Delivery getDelivery() {
		return mDelivery;
	}

	/**
	 * Returns the list of segments coming out of the node
	 * 
	 * @return the list of segments coming out of the node
	 */
	public List<Segment> getOutSegmentList() {
		return mOutSegmentList;
	}

	/**
	 * Returns the list of segments coming in the node
	 * 
	 * @return the list of segments coming in the node
	 */
	public List<Segment> getInSegmentList() {
		return mInSegmentList;
	}

	/**
	 * Returns the unique id of the node
	 * 
	 * @return id of the node
	 */
	public int getId() {
		return mId;
	}

	/**
	 * Sets the node as warehouse if isWarehouse is true, sets it as not
	 * warehouse otherwise
	 * 
	 * @param boolean whether the node should be set as warehouse
	 */
	public void setIsWarehouse(boolean isWarehouse) {
		mIsWarehouse = isWarehouse;
	}

	/**
	 * Fills Node object with data from XML
	 * 
	 * @param nodeElement
	 *            XML element to be parsed
	 * @param network
	 *            The network
	 */
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
		try {
			return "NODE : ID=" + this.mId + ", X= " + this.mX + ", Y= " + mY
					+ ", InSegment " + mInSegmentList.toString()
					+ ", outSegment" + mOutSegmentList.toString();
		} catch (NullPointerException npx) {
			return "NODE : ID=" + this.mId + ", X= " + this.mX + ", Y= " + mY;
		}

	}

	/**
	 * Returns the X coordinate of the node
	 * 
	 * @return the X coordinate of the node
	 */
	public int getX() {
		return mX;
	}

	/**
	 * Returns the Y coordinate of the node
	 * 
	 * @return the Y coordinate of the node
	 */
	public int getY() {
		return mY;
	}

	/**
	 * Associate the node with the delivery in parameter
	 * 
	 * @param delivery
	 *            Delivery to be associated with the node
	 */
	public void setDelivery(Delivery delivery) {
		mDelivery = delivery;
	}

	@Override
	public String buildFromXML(Element element, Network network,
			String tmpWarningMessage, Map<Integer, Node> map_clientAdress,
			List<Integer> list_allAdress)
			throws InvalidDeliveryRequestFileException,
			WarningDeliveryRequestFile {
		// TODO Auto-generated method stub
		return null;
	}
}