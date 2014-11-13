package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import tsp.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 */
public class DeliveryRequest {

	/**
	 * 
	 */

	public DeliveryRequest(Network network) {
		this.network = network;
		mTimeSlotList = new ArrayList<TimeSlot>();
	}

	/**
	 * 
	 */
	protected Network network;

	public DeliveryRequest() {
		mTimeSlotList = new ArrayList<TimeSlot>();
	}

	/**
	 * 
	 */
	protected Node mWarehouse;

	/**
	 * List containing all the time slots in a delivery request the list is
	 * sorted in ascending order.
	 */
	protected List<TimeSlot> mTimeSlotList;

	/**
	 * 
	 */
	protected Tour mTour;

	static final int MAX_COMPUTE_TIME = 10000;

	/**
	 * Computes the most interesting tour and initializes the tour object
	 */
	public void calculateTour() {
		Map<Integer, Integer> mapIdToIndex = generateMapIdToIndex();

		Map<Integer, Map<Integer, Path>> mapIndexPath = createPathMap(mapIdToIndex);
		ShortestPathGraph graph = new ShortestPathGraph(mapIndexPath, mapIdToIndex.size());
		TSP tsp = new TSP(graph);
		SolutionState state;
		int bound = graph.getNbVertices() * graph.getMaxArcCost() + 1;
		int t = 1000;
		do {
			state = tsp.solve(t, bound);
			bound = tsp.getTotalCost();
			t *= 2;
		} while (state != SolutionState.OPTIMAL_SOLUTION_FOUND
				&& t < MAX_COMPUTE_TIME);

		int[] nodesIndex = tsp.getNext();
		int[] nodesId = decodeMapNode(mapIdToIndex, nodesIndex);

		this.mTour = new Tour(mWarehouse);
		
		int warehouseIndex = mapIdToIndex.get(mWarehouse.getId());
		int previousIndex = warehouseIndex;
		int nextIndex;

		do {
			nextIndex = nodesIndex[previousIndex];
			Node previousNode = this.network.getNode(nodesId[previousIndex]);
			if(previousNode.getDelivery() != null){
				this.mTour.addDelivery(previousNode.getDelivery());				
			}
			
			this.mTour.addPath(mapIndexPath.get(previousIndex).get(nextIndex));
			previousIndex = nextIndex;

		}while(previousIndex != warehouseIndex);
				
		this.mTour.updateHour();

		network.networkChanged();
	}

	private int[] decodeMapNode(Map<Integer, Integer> mapIdToIndex, int[] nodesIndex) {
		int [] result = new int[nodesIndex.length];
		Map<Integer, Integer> mapIndexToId = new HashMap<Integer, Integer>();
		for(Map.Entry<Integer, Integer> e : mapIdToIndex.entrySet()){
			mapIndexToId.put(e.getValue(), e.getKey());
		}

		for(int i=0;i<nodesIndex.length;i++){
			result[i] = mapIndexToId.get(nodesIndex[i]);
		}
		return result;
	}

	private Map<Integer, Integer> generateMapIdToIndex() {
		int i = 0;
		Map<Integer, Integer> mapNode = new HashMap<Integer, Integer>();

		mapNode.put(mWarehouse.getId(), i);
		i++;

		for(TimeSlot t : mTimeSlotList){
			for(Delivery d : t.getAllDeliveries()){
				mapNode.put(d.getNode().getId(), i);
				i++;
			}
		}
		return mapNode;
	}

	/**
	 * Create a delivery associated with previousNode and insert it into tour
	 * after the delivery associated with previousNode
	 * 
	 * @param NodepreviousNode
	 * @param Node selectedNode
	 * @return
	 */
	public boolean insertDelivery(Node previousNode, Node selectedNode) {
		Delivery previousDelivery=null;
		if(!previousNode.isWarehouse())
		{
			previousDelivery = previousNode.getDelivery();
		}
		Delivery newDelivery = new Delivery(selectedNode);
		return mTour.insertDelivery(previousDelivery, newDelivery);
	}

	/**
	 * Remove from the tour the delivery associated with the node
	 * 
	 * @param Node node associated with the delivery to remove
	 * @return the node before the removed delivery
	 */
	public Node removeDelivery(Node node) {
		return mTour.removeDelivery(node.getDelivery());
	}

	public String buildFromXML(Element deliveryRequestElement, Network network) throws InvalidDeliveryRequestFileException, WarningDeliveryRequestFile {

		try {
			setWarehouseFromXML(deliveryRequestElement, network);
			buildTimeSlotsFromXML(deliveryRequestElement, network);
		} catch (InvalidDeliveryRequestFileException iDRFE){
			throw new InvalidDeliveryRequestFileException(iDRFE.getMessage());
		} catch (WarningDeliveryRequestFile wa){
			throw new WarningDeliveryRequestFile(wa.getMessage());
		}

		return "OK";
	}

	/*
	 * private int checkValidity(Element element) { // TODO: Check for validity
	 * return 0; }
	 */

	private void setWarehouseFromXML(Element deliveryRequestElement,
			Network network) throws InvalidDeliveryRequestFileException{
		NodeList nodeListWarehouse = deliveryRequestElement
				.getElementsByTagName("Entrepot");
		Element warehouseElement = (Element) nodeListWarehouse.item(0);

		this.mWarehouse = network.getNode(Integer.parseInt(warehouseElement.getAttribute("adresse")));
		
		if (mWarehouse == null){
			throw new InvalidDeliveryRequestFileException("Le noeud de l'Entrepot dans les demandes de Livraison n'existe pas dans le Réseau");
		}
		
		this.mWarehouse.setIsWarehouse(true);
	}

	private void buildTimeSlotsFromXML(Element deliveryRequestElement,
			Network network) throws InvalidDeliveryRequestFileException, WarningDeliveryRequestFile{
		NodeList listTimeSlots = deliveryRequestElement
				.getElementsByTagName("Plage");
		Integer numberOfSlots = listTimeSlots.getLength();

		Element timeSlotElement;
		String listClientsWithSeveralAdresses = "";
		Map<Integer, Node> m_clientAdress = new HashMap<Integer, Node>();


		for (int i = 0; i < numberOfSlots; i++) {
			TimeSlot timeSlot = new TimeSlot(i);
			timeSlotElement = (Element) listTimeSlots.item(i);

			try {
				listClientsWithSeveralAdresses += timeSlot.buildFromXML(timeSlotElement, network, "", m_clientAdress);
			} catch (InvalidDeliveryRequestFileException iDRFE){
				throw new InvalidDeliveryRequestFileException(iDRFE.getMessage());
			}

			mTimeSlotList.add(timeSlot);

		}
		
		if (!listClientsWithSeveralAdresses.equals("")){
			throw new WarningDeliveryRequestFile("Les clients suivants ont plusieurs adresses : "+listClientsWithSeveralAdresses);
		}
		
	}

	@Override
	public String toString() {

		return "Delivery Request : Warehouse (" + mWarehouse + "), TimeSlots"
				+ mTimeSlotList.toString();
	}

	/**
	 * Initializes the map that contains the paths the first key id the id of
	 * the origin node the second key is the id of the destination node
	 * 
	 * @return the initialized map
	 * 
	 */
	private Map<Integer, Map<Integer, Path>> createPathMap(Map<Integer, Integer> mapNode) {
		Map<Integer, Map<Integer, Path>> mapPath = new HashMap<Integer, Map<Integer, Path>>();
		int nbSlots = mTimeSlotList.size();

		// link the nodes in the first time slot with the warehouse
		TimeSlot firstTimeSlot = mTimeSlotList.get(0);
		Dijkstra dWarehouse = new Dijkstra(mWarehouse);
		Map<Integer, Path> destDeliveries = new HashMap<Integer, Path>();
		for (Delivery delivery : firstTimeSlot.getAllDeliveries()) {
			Node destination = delivery.getNode();
			Path path = dWarehouse.calculateShortestPathTo(destination);
			destDeliveries.put(mapNode.get(destination.getId()), path);
		}
		mapPath.put(mapNode.get(mWarehouse.getId()), destDeliveries);

		// internal links between the nodes in all the time slots
		for (int i = 0; i < nbSlots; i++) {
			TimeSlot timeSlot = mTimeSlotList.get(i);
			for (Delivery originDelivery : timeSlot.getAllDeliveries()) {
				Node origin = originDelivery.getNode();
				Dijkstra dOrigin = new Dijkstra(origin);
				destDeliveries = new HashMap<Integer, Path>();

				// internal links in the current time slot
				for (Delivery destDelivery : timeSlot.getAllDeliveries()) {
					if (destDelivery != originDelivery) {// we should discuss
						// this
						Node destination = destDelivery.getNode();
						Path path = dOrigin
								.calculateShortestPathTo(destination);
						destDeliveries.put(mapNode.get(destination.getId()), path);
					}
				}

				// links with the next time slot
				if (i != nbSlots - 1) { // if we're not dealing with last time
					// slot
					TimeSlot nextTimeSlot = mTimeSlotList.get(i + 1);
					for (Delivery destDelivery : nextTimeSlot
							.getAllDeliveries()) {
						Node destination = destDelivery.getNode();
						Path path = dOrigin
								.calculateShortestPathTo(destination);
						destDeliveries.put(mapNode.get(destination.getId()), path);
					}
				} else {
					Path path = dOrigin.calculateShortestPathTo(mWarehouse);
					destDeliveries.put(mapNode.get(mWarehouse.getId()), path);
				}
				mapPath.put(mapNode.get(origin.getId()), destDeliveries);
			}
		}

		return mapPath;
	}

	public Tour getTour(){
		return mTour;
	}
	
	public void createRoadMap(FileWriter fw){
		try {
			String breakLine = System.getProperty("line.separator");
			String header = "--------------------- Votre Feuille de route --------------------";
			String pathSeparator = breakLine+breakLine+"--------------------- Itinéraire --------------------";
			String deliverySeparator = breakLine+breakLine+"--------------------- Livraison --------------------";
			String pathHeaderTemplate = breakLine+"Itinéraire de %s à l'adresse de la livraison numéro %s : ";
			String deliveryHeaderTemplate = breakLine+ "Détails de la livraison numéro %s";
			String pathDescriptionTemplate = breakLine+"Suivre rue %s sur %s m de l'adresse %s à l'adresse %s  ";
			String deliveryDescriptionTemplate = breakLine+"Adresse: %s"+breakLine+"Heure d'arrivée prévue: %s"+ breakLine+"Heure de livraison prévue: %s"+breakLine+"Heure de départ prévue: %s"+breakLine+"Coordonnées du client: %s";
			
			fw.write(header);
			
			int nbPaths = mTour.getPathList().size();
			//Path from Warehouse to Node 1
			for (int i = 0; i<nbPaths-1; i++){
				Path pathToNextDelivery = mTour.getPathList().get(i);
				List<Segment> pathSegments = pathToNextDelivery.getSegmentList();
				String pathHeader;
				if(i ==0){
					pathHeader = String.format(pathHeaderTemplate,"l'entrepôt", i+1);
				}else{
					pathHeader = String.format(pathHeaderTemplate,"la livraison numéro " + i, i+1);
				}
				
				fw.write(pathSeparator);
				fw.write(pathHeader);
				for(Segment seg : pathSegments){
					String path = String.format(pathDescriptionTemplate, seg.getStreetName(), seg.getLength(), seg.getDepartureNode().getId(), seg.getArrivalNode().getId());
					fw.write(path);
				}
				
				//Delivery description
				
				Delivery nthDelivery = mTour.getDeliveryList().get(i);
				String deliveryHeader = String.format(deliveryHeaderTemplate, i+1);
				String deliveryDescription = String.format(deliveryDescriptionTemplate, nthDelivery.getNode().getId(),nthDelivery.getFormattedArrivalHour(), nthDelivery.getFormattedDeliveryHour(), nthDelivery.getFormattedDepartureHour(), nthDelivery.getClient());
				fw.write(deliverySeparator);
				fw.write(deliveryHeader);
				fw.write(deliveryDescription);
			}
			
			String lastPathHeader = breakLine+ "Itinéraire de la dernière livraison à l'entrepôt";
			Path lastPath = mTour.getPathList().get(nbPaths-1);
			List<Segment> pathSegments = lastPath.getSegmentList();
			
			fw.write(pathSeparator);
			fw.write(lastPathHeader);
			for(Segment seg : pathSegments){
				String path = String.format(pathDescriptionTemplate, seg.getStreetName(), seg.getLength(), seg.getDepartureNode().getId(), seg.getArrivalNode().getId());
				fw.write(path);
			}

			fw.close();
		} catch (IOException e) {
			
			return;
		}
		
	}

}