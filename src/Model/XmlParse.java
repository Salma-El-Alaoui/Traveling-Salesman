package Model;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

/**
 * Interface with methods to create objects from XML files
 */
public interface XmlParse {

	/**
	 * @param Element
	 * @param Network
	 * @param String
	 *            empty at the beginning
	 * @param int : node of the Warehouse, for checking we don't serve the
	 *        Warehouse during a Timeslot
	 * @return Report message : OK if building succeeded, a warning message if a
	 *         non-blocking exception occurs.
	 * @throws DeliveryRequestParseException
	 *             when a blocking eroor occurs
	 */
	public String buildFromXML(Element element, Network network,
			String tmpWarningMessage, Map<Integer, Node> map_clientAdress,
			List<Integer> list_allAdress, int mWareHouseInt)
			throws InvalidDeliveryRequestFileException,
			WarningDeliveryRequestFile;

	/**
	 * @param Element
	 * @param Network
	 * @return Report message : OK if building succeeded, a warning message if a
	 *         non-blocking exception occurs.
	 * @throws DeliveryRequestParseException
	 *             when a blocking eroor occurs
	 */
	public String buildFromXML(Element element, Network network)
			throws InvalidDeliveryRequestFileException,
			WarningDeliveryRequestFile;

}