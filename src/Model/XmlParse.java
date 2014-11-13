package Model;

import java.util.Map;

import org.w3c.dom.Element;

/**
 * 
 */
public interface XmlParse {

	/**
	 * @param Element
	 * @param Network	 
	 * @param String empty at the beginning
	 * @return Report message : OK if building succeeded, a warning message if a
	 *         non-blocking exception occurs.
	 * @throws DeliveryRequestParseException
	 *             when a blocking eroor occurs
	 */
	public String buildFromXML(Element element, Network network, String tmpWarningMessage, Map<Integer, Node> map_clientAdress) throws InvalidDeliveryRequestFileException, WarningDeliveryRequestFile;
	
	/**
	 * @param Element
	 * @param Network
	 * @return Report message : OK if building succeeded, a warning message if a
	 *         non-blocking exception occurs.
	 * @throws DeliveryRequestParseException
	 *             when a blocking eroor occurs
	 */
	public String buildFromXML(Element element, Network network) throws InvalidDeliveryRequestFileException, WarningDeliveryRequestFile;

}