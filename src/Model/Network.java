package Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class that represents a network
 */
public class Network extends Observable {

	/**
	 * Map of all the nodes in the network with their id as key
	 */
	protected Map<Integer, Node> mNodesList;

	/**
	 * List of all the segments in the network
	 */
	protected List<Segment> mSegmentList;

	/**
	 * Node currently selected
	 */
	protected Node mSelectedNode;

	/**
	 * Delivery request loaded
	 */
	protected DeliveryRequest mDeliveryRequest;

	/**
	 * Constructor
	 */
	public Network() {
		mNodesList = new HashMap<Integer, Node>();
		mSegmentList = new ArrayList<Segment>();
	}

	/**
	 * Returns a map with all the nodes of the network with their id as key
	 * @return a map with all the nodes of the network with their id as key
	 */
	public Map<Integer, Node> getNodesList() {
		return mNodesList;
	}

	/**
	 * Returns the list of all the segments of the network
	 * @return the list of all the segments of the network
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

	/**
	 * Returns the node currently selected
	 * @return the node currently selected
	 */
	public Node getSelectedNode() {
		return mSelectedNode;
	}

	/**
	 * Sets the node currently selected
	 * @param node the node currently selected
	 */
	public void setSelectedNode(Node node) {
		if (mSelectedNode != null) {
			mSelectedNode.setSelectedNode(false);
		}
		mSelectedNode = node;
		mSelectedNode.setSelectedNode(true);
	}

	/**
	 * Returns the node corresponding to the id
	 * @param id Id of the node
	 * @return Node associated with the id
	 */
	public Node getNode(int id) {
		return mNodesList.get(id);
	}

	/**
	 * Update the incoming and outcoming segments of the node identified by id
	 * @param id Id associated with the node to update
	 * @param inSegment New incoming segment
	 * @param outSegment New outcoming segment
	 */
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

	/**
	 * Returns the delivery request associated with the network
	 * @return the delivery request associated with the network
	 */
	public DeliveryRequest getDeliveryRequest() {
		return mDeliveryRequest;
	}


	public void parseDeliveryRequestFile(File deliveriesFile)
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

			this.mDeliveryRequest.buildFromXML(deliveryRequestElement);
			networkChanged();
			networkChanged(document);

		} catch (SAXException | IOException | IllegalArgumentException
				| ParserConfigurationException
				| InvalidDeliveryRequestFileException ex) { // Syntactic errors
			// in XML

			// file.
			throw new InvalidDeliveryRequestFileException(ex.getMessage());

		} catch (WarningDeliveryRequestFile wa) {
			networkChanged();
			throw new WarningDeliveryRequestFile(wa.getMessage());
		}
		
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
	
	public void networkChanged(Document doc){
		setChanged();
		notifyObservers(doc);
	}

	public void parseNetworkFile(File networkFile)
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

		int nodeRes = 0;
		int flagNodeRes = 0;

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
				nodeRes = segment.buildFromXML(departureNode, segmentElement, this);
				if (nodeRes!=-1){
					flagNodeRes = nodeRes;
				}
				mSegmentList.add(segment);
			}

		}
		// Au moins une erreur ou le node de depart et le même que le node d'arrivee
		if (Segment.ERROR_XML_SEGMENT_NODE_DESTINATION_SAME_AS_DEPARTURE > 0) {
			throw new WarningDeliveryRequestFile(
					"Erreur dans le fichier des livraisons - Noeud de départ "+ flagNodeRes + " similaire au Noeud d'arrivée");
		} else
			return 0;
	}

	/**
	 * Returns a string representing the network
	 *  @return a string representing the network
	 */
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