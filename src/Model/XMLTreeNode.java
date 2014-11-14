package Model;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
/**
 *This class is exclusively used to represent the XML tree displayed in the XMLTreePanel
 */
public class XMLTreeNode {
	Element element;
	
	/**
	 * Create an XMLTreeNode
	 * @param element Root element
	 */
	public XMLTreeNode(Element element) {
		this.element = element;
	}
	/**
	 * @return Root Element
	 */
	public Element getElement() {
		return element;
	}
	
	/**
	 * @return String value of the file
	 */
	public String toString() {
		String nodeName = element.getNodeName();
		switch (nodeName){
		case "JourneeType":
			return nodeName;
		case "Entrepot":
			return "Entrepot à l'adresse : "+element.getAttribute("adresse");
		case "PlagesHoraires":
			return nodeName;
		case "Plage":
			return "Plage horaire de "+ element.getAttribute("heureDebut") + " à "+ element.getAttribute("heureFin");
		case "Livraisons":
			return nodeName;
		case "Livraison":
			return "Client "+ element.getAttribute("client") + " à l'adresse " + element.getAttribute("adresse");  
		default:
			return "4222";
		}
		
	}
	
	/**
	 * @return The text content of the Node
	 */
	public String getText() {
		NodeList list = element.getChildNodes();
		for (int i=0 ; i<list.getLength() ; i++) {
			if (list.item(i) instanceof Text) {
				return ((Text)list.item(i)).getTextContent();
			}
		}
		return "";
	}
}
