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
	protected Set<Segment> mSegmentList;

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
				msg = this.deliveryRequest.buildFromXML(deliveryRequestElement, this);
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
	public int parseNetworkFile(File file) {
		// TODO implement here
		return 0;
	}

	/**
	 * @return
	 */
	public Node getSelectedNode() {
		// TODO implement here
		return null;
	}

}