package Model;

import org.w3c.dom.Element;

/**
 * 
 */
public interface XmlParse {

	/**
	 * @param Element
	 * @return Report message : OK if building succeeded, a warning message if a
	 *         non-blocking exception occurs.
	 * @throws DeliveryRequestParseException
	 *             when a blocking eroor occurs
	 */
	public String buildFromXML(Element element, Network network) throws InvalidDeliveryRequestFileException, WarningDeliveryRequestFile;

}