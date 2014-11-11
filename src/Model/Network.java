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
	public Network() {
	}

	/**
     * 
     */
	protected List<Segment> mSegmentList;

	/**
     * 
     */
	protected DeliveryRequest deliveryRequest;

	/**
     * 
     */
	protected Map<Integer, Node> mNodesList;

	/**
	 * @param Node
	 *            n1
	 * @param Node
	 *            n2
	 * @return
	 */
	public Path calculateShortestPath(Node n1, Node n2) {
		// TODO implement here
		return null;
	}

	/**
	 * @param Node
	 *            previous
	 * @param Node
	 *            selected
	 * @return
	 */
	public void addDelivery(Node previous, Node selected) {
		// TODO implement here
	}

	/**
	 * @param Node
	 *            node
	 * @return
	 */
	public void removeDelivery(Node node) {
		// TODO implement here
	}

	/**
	 * @param int id
	 * @return
	 */
	public Node getNode(int id) {
		// TODO implement here
		return null;
	}

	/**
	 * @param File
	 * @return
	 */
	public String parseDeliveryRequestFile(File deliveriesFile) {
		String msg;
		try {

			DocumentBuilder constructeur = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			// Reading XML file content and storing result in DOM document.
			Document document = constructeur.parse(deliveriesFile); // Might
																	// throw
																	// exceptions

			Element deliveryRequestElement = document.getDocumentElement();

			this.deliveryRequest = new DeliveryRequest();
			try {
				msg = this.deliveryRequest.buildFromXML(deliveryRequestElement,
						this);
			} catch (DeliveryRequestParseException pex) {
				return pex.getMessage();
			}

		} catch (SAXException | IOException | IllegalArgumentException
				| ParserConfigurationException ex) { // Syntactic errors in XML
														// file.
			return ex.getMessage();
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
	 */
	public String parseNetworkFile(File networkFile) {
		String msg = "OK";
		try {

			DocumentBuilder constructeur = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			// Reading XML file content and storing result in DOM document.
			Document document = constructeur.parse(networkFile); // Might
																	// throw
																	// exceptions

			Element networkElement = document.getDocumentElement();

			buildNodesFromXML(networkElement);

			buildSegmentsFromXML(networkElement);

		} catch (SAXException | IOException | IllegalArgumentException
				| ParserConfigurationException ex) { // Syntactic errors in XML
														// file.
			return ex.getMessage();
		}
		return msg;
	}

	/**
	 * @return
	 */
	public Node getSelectedNode() {
		// TODO implement here
		return null;
	}

	private String buildNodesFromXML(Element networkElement) {
		NodeList listNodes = networkElement.getElementsByTagName("Noeud");
		Integer nodesNumber = listNodes.getLength();

		mNodesList = new HashMap<Integer, Node>();
		mSegmentList = new ArrayList<Segment>();

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
			System.out.println("Gere" + i + " "+ nodesNumber);
			nodeElement = (Element) listNodes.item(i);
			
			Node departureNode = this.getNode(Integer.parseInt(nodeElement.getAttribute("id")));

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
		for (Map.Entry<Integer, Node> entry: mNodesList.entrySet()){
			res += entry.getKey();
			res += "  ";
			res += entry.getValue().toString();
			res += "\n";
		}
		for (Segment s : mSegmentList){
			res += s.toString() +"\n";
		}
		return res;
	}
}