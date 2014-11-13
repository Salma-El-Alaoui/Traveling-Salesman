package Model;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XMLTreeNode {
	Element element;
	public XMLTreeNode(Element element) {
		this.element = element;
	}
	public Element getElement() {
		return element;
	}
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
