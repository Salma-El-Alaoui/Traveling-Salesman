package Model;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * 
 */
public class Network {

	/**
     * 
     */

	protected List<Segment> mSegmentList;

	/**
     * 
     */
	protected Node mSelectedNode;

	public Network() {
		this.mDeliveryRequest = new DeliveryRequest();
		mNodesList = new HashMap<Integer, Node>();
		mSegmentList = new ArrayList<Segment>();
	}

	/**
     * 
     */

	protected DeliveryRequest mDeliveryRequest;

	/**
     * 
     */

	protected List<Node> mWarehouseList;

	/**
	 * Calculate the shortest path from startNode to endNode
	 * 
	 * @param originNode
	 * @param endNode
	 * @return The shortest path, null if no path can be found
	 * @deprecated Prefer to use Dijkstra class to improve performance (in case
	 *             of multiples calls with the same start)
	 */
	@Deprecated
	public Path calculateShortestPath(Node originNode, Node endNode) {
		Dijkstra d = new Dijkstra(originNode);
		return d.calculateShortestPathTo(endNode);
	}

	/**
	 * Add a delivery associated with selected node after the one associated
	 * with previous node
	 * 
	 * @param previous
	 * @param selected
	 */
	public boolean addDelivery(Node previous, Node selected) {
		return mDeliveryRequest.insertDelivery(previous, selected);
	}

	/**
	 * Remove from the tour the delivery associated with the node
	 * 
	 * @param Node
	 *            node associated with the delivery to remove
	 * @return the node before the removed delivery
	 */
	public Node removeDelivery(Node node) {
		return mDeliveryRequest.removeDelivery(node);
	}

	/**
     * 
     */

	public Node getSelectedNode() {
		return mSelectedNode;
	}

	public void setSelectedNode(Node node) {
		mSelectedNode.setSelectedNode(false);
		mSelectedNode = node;
	}

	protected Map<Integer, Node> mNodesList;

	/**
	 * @param Node
	 *            previous
	 * @param Node
	 *            selected
	 * @return
	 */

	public Node getNode(int id) {
		return mNodesList.get(id);
	}

	public void updateNode(int id, Segment inSegment, Segment outSegment) {
		Node updatedNode = mNodesList.get(id);
		if (inSegment != null) {
			updatedNode.addInSegment(inSegment);
		}
		if (outSegment != null) {
			updatedNode.addOutSegment(outSegment);
		}
		mNodesList.put(id, updatedNode); // Updates the node having this ID
	}

	public DeliveryRequest getDeliveryRequest() {
		return mDeliveryRequest;
	}

	/**
	 * @param File
	 * @return
	 * @throws InvalidDeliveryRequestFileException
	 * @throws InvalidNetworkFileException
	 */
	public String parseDeliveryRequestFile(File deliveriesFile)
			throws InvalidNetworkFileException,
			InvalidDeliveryRequestFileException {
		String msg;
		try {

			DocumentBuilder constructeur = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			// Reading XML file content and storing result in DOM document.
			Document document = constructeur.parse(deliveriesFile); // Might
																	// throw
																	// exceptions

			Utils.FileValidator(document, "livraison.xsd");

			Element deliveryRequestElement = document.getDocumentElement();

			msg = this.mDeliveryRequest.buildFromXML(deliveryRequestElement,
					this);

		} catch (SAXException | IOException | IllegalArgumentException
				| ParserConfigurationException ex) { // Syntactic errors in XML
														// file.
			throw new InvalidDeliveryRequestFileException(ex.getMessage());
		}
		return msg;

	}

	/**
	 * @return
	 */
	public Element getDocumentRoot() {
		// TODO implement here
		return null;
	}

	/**
	 * @param File
	 * @return
	 * @throws InvalidNetworkFileException
	 * @throws InvalidDeliveryRequestFileException
	 */
	public String parseNetworkFile(File networkFile)
			throws InvalidNetworkFileException,
			InvalidDeliveryRequestFileException {
		String msg = "OK";
		try {

			DocumentBuilder constructeur = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			// Reading XML file content and storing result in DOM document.
			Document document = constructeur.parse(networkFile); // Might
																	// throw
																	// exceptions

			Utils.FileValidator(document, "plan.xsd");

			Element networkElement = document.getDocumentElement();

			buildNodesFromXML(networkElement);

			buildSegmentsFromXML(networkElement);

		} catch (SAXException | IOException | IllegalArgumentException
				| ParserConfigurationException ex) { // Syntactic errors in XML
														// file.
			throw new InvalidNetworkFileException(ex.getMessage());
		}

		return msg;
	}

	private String buildNodesFromXML(Element networkElement) {
		NodeList listNodes = networkElement.getElementsByTagName("Noeud");
		Integer nodesNumber = listNodes.getLength();

		Element nodeElement;
		for (int i = 0; i < nodesNumber; i++) {
			Node node = new Node();
			nodeElement = (Element) listNodes.item(i);

			node.buildFromXML(nodeElement, this);

			mNodesList.put(node.getId(), node);

		}
		return "OK";
	}

	private String buildSegmentsFromXML(Element networkElement) {

		NodeList listNodes = networkElement.getElementsByTagName("Noeud");
		Integer nodesNumber = listNodes.getLength();

		Element nodeElement;
		for (int i = 0; i < nodesNumber; i++) {
			nodeElement = (Element) listNodes.item(i);

			Node departureNode = this.getNode(Integer.parseInt(nodeElement
					.getAttribute("id")));

			NodeList listSegments = nodeElement
					.getElementsByTagName("LeTronconSortant");

			Integer segmentsNumber = listSegments.getLength();

			Element segmentElement;
			for (int j = 0; j < segmentsNumber; j++) {

				Segment segment = new Segment();
				segmentElement = (Element) listSegments.item(j);
				segment.buildFromXML(departureNode, segmentElement, this);
				mSegmentList.add(segment);
			}

		}
		return "OK";
	}

	public String toString() {
		String res = "-------------------------Network Object------------------------------- \n";
		res += "------------Nodes List --------------- \n";
		for (Map.Entry<Integer, Node> entry : mNodesList.entrySet()) {
			res += entry.getKey();
			res += "  ";
			res += entry.getValue().toString();
			res += "\n";
		}
		res += "------------Segments List --------------- \n";
		for (Segment s : mSegmentList) {
			res += s.toString() + "\n";
		}
		return res;
	}

}