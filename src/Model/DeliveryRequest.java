package Model;

import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 */
public class DeliveryRequest {

	/**
     * 
     */
	public DeliveryRequest() {
	}

	/**
     * 
     */
	protected Node mWarehouse;

	/**
     * 
     */
	protected Set<TimeSlot> mTimeSlotList;

	/**
     * 
     */
	protected Tour[] mTour;

	/**
	 * @param Network
	 *            network
	 */
	public void calculateTour(Network network) {
		// TODO implement here
	}

	/**
	 * @param Node
	 *            previous
	 * @param Node
	 *            next
	 * @return
	 */
	public void insertDelivery(Node previous, Node next) {
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

	
	public String buildFromXML(Element deliveryRequestElement, Network network)
			throws DeliveryRequestParseException {

		setWarehouseFromXML(deliveryRequestElement, network);

		buildTimeSlotsFromXML(deliveryRequestElement, network);

		return "OK";
	}

	private int checkValidity(Element element) {
		// TODO: Check for validity
		return 0;
	}

	private void setWarehouseFromXML(Element deliveryRequestElement, Network network) {
		NodeList nodeListWarehouse = deliveryRequestElement
				.getElementsByTagName("Entrepot");
		Element warehouseElement = (Element) nodeListWarehouse.item(0);

		this.mWarehouse = network.getNode(Integer.parseInt(warehouseElement.getAttribute("adresse")));
		this.mWarehouse.setIsWarehouse(true);
	}

	private void buildTimeSlotsFromXML(Element deliveryRequestElement, Network network) {
		NodeList listTimeSlots = deliveryRequestElement
				.getElementsByTagName("Plage");
		Integer numberOfSlots = listTimeSlots.getLength();

		Element timeSlotElement;

		for (int i = 0; i < numberOfSlots; i++) {
			TimeSlot timeSlot = new TimeSlot();
			timeSlotElement = (Element) listTimeSlots.item(i);

			timeSlot.buildFromXML(timeSlotElement, network);

			mTimeSlotList.add(timeSlot);

		}
	}

}