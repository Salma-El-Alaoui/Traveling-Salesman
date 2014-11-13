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
public class Network extends Observable {

	protected Map<Integer, Node> mNodesList;

	/**
	 * 
	 */

	protected List<Segment> mSegmentList;

	/**

	 * 
	 */
	protected Node mSelectedNode;

	protected DeliveryRequest mDeliveryRequest;

	public Network() {
		mNodesList = new HashMap<Integer, Node>();
		mSegmentList = new ArrayList<Segment>();
	}

	/**
	 * 
	 */
	public Map<Integer, Node> getNodesList() {
		return mNodesList;
	}

	/**
	 * 
	 * @return
	 */
	public List<Segment> getSegmentList() {
		return mSegmentList;
	}

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

	public Node getSelectedNode() {
		return mSelectedNode;
	}

	public void setSelectedNode(Node node) {
		if (mSelectedNode != null) {
			mSelectedNode.setSelectedNode(false);
		}
		mSelectedNode = node;
		mSelectedNode.setSelectedNode(true);
	}

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
			InvalidDeliveryRequestFileException, WarningDeliveryRequestFile {
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

			this.mDeliveryRequest = new DeliveryRequest(this);

			msg = this.mDeliveryRequest.buildFromXML(deliveryRequestElement,
					this);
			networkChanged();

		} catch (SAXException | IOException | IllegalArgumentException
				| ParserConfigurationException
				| InvalidDeliveryRequestFileException ex) { // Syntactic errors
															// in XML

			// file.
			throw new InvalidDeliveryRequestFileException(ex.getMessage());

		} catch (WarningDeliveryRequestFile wa) {
			throw new WarningDeliveryRequestFile(wa.getMessage());
		}
		return msg;
	}
	
	public void clearNodeContent(){
		for(Node n : mNodesList.values()){
			n.setDelivery(null);
			n.setIsWarehouse(false);
		}
	}

	public void networkChanged() {
		setChanged(); // Marks this Observable object as having been changed;
		notifyObservers(); // If this object has changed then notify all of its
							// observers and then call the clearChanged method
							// to indicate that this object has no longer
							// changed.
	}

	/**
	 * @param File
	 * @return
	 * @throws InvalidNetworkFileException
	 * @throws InvalidDeliveryRequestFileException
	 */
	public String parseNetworkFile(File networkFile)
			throws InvalidNetworkFileException,
			InvalidDeliveryRequestFileException, WarningDeliveryRequestFile {
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

		} catch (WarningDeliveryRequestFile warning) {
			throw new WarningDeliveryRequestFile(warning.getMessage());
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

	private int buildSegmentsFromXML(Element networkElement)
			throws WarningDeliveryRequestFile {

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
		// Au moins une erreur où le node de départ et le même que le node
		// d'arrivée
		if (Segment.ERROR_XML_SEGMENT_NODE_DESTINATION_SAME_AS_DEPARTURE > 0) {
			throw new WarningDeliveryRequestFile(
					"Error xml DeliveryRequest - Noeud de départ similaire au Noeud d'arrivée");
		} else
			return 0;
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