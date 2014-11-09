package Model;

import java.util.*;

import org.w3c.dom.Element;

/**
 * 
 */
public class DeliveryRequest implements XmlParse {

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
     * List containing all the time slots in a delivery request
     * the list is sorted in ascending order.
     */
    protected List<TimeSlot> mTimeSlotList;

    /**
     * 
     */
    protected Tour mTour;

    /**
     * 
     */
    public void calculateTour() {
       ShortestPathGraph graph = new ShortestPathGraph(createPathMap());
       
    
    	
    	
    }

    /**
     * @param Node previous 
     * @param Node selectedNode
     * @return
     */
    public void insertDelivery(Node previousNode, Node selectedNode) {
        Delivery previousDelivery=previousNode.getDelivery();
        Delivery newDelivery=new Delivery(selectedNode);
        mTour.insertDelivery(previousDelivery, newDelivery);
    }

    /**
     * @param Node node 
     * @return
     */
    public void removeDelivery(Node node) {
        mTour.removeDelivery(node.getDelivery());
    }

	@Override
	public int buildFromXML(Element element) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Initializes the map that contains the paths
	 * the first key id the id of the origin node
	 * the second key is the id of the destination node
	 * @param Network network
	 * @return the initialized map
	 * 
	 */
	private Map<Integer, Map<Integer, Path>> createPathMap(){
		Map<Integer, Map<Integer, Path>> mapPath = new HashMap<Integer, Map<Integer, Path>>();
		int nbSlots =mTimeSlotList.size()-1;
		
		//first time slot
		TimeSlot firstTimeSlot = mTimeSlotList.get(0);		
		Dijkstra dWarehouse = new Dijkstra(mWarehouse);   	
    	Map<Integer, Path> destDeliveries = new HashMap<Integer,Path>();
		for (Delivery delivery : firstTimeSlot.getAllDeliveries()){
			Node destination = delivery.getNode();
			Path path = dWarehouse.calculateShortestPathTo(destination);
			destDeliveries.put(destination.getId(),path);						
		}
		mapPath.put(mWarehouse.getId(),destDeliveries);
		
		//next time slots
		for (int i = 0; i <nbSlots ; i++){
			TimeSlot timeSlot= mTimeSlotList.get(i); 
			TimeSlot nextTimeSlot = mTimeSlotList.get(i+1);			
			
			for (Delivery originDelivery : timeSlot.getAllDeliveries()){
				Node origin = originDelivery.getNode();				
				Dijkstra dOrigin = new Dijkstra(origin); 
				destDeliveries = new HashMap<Integer,Path>();
				//links with the current time slot
				for (Delivery destDelivery : timeSlot.getAllDeliveries()){
					if (destDelivery != originDelivery){//we should rediscuss this
						Node destination = destDelivery.getNode();
						Path path = dOrigin.calculateShortestPathTo(destination);
						destDeliveries.put(destination.getId(),path);							
					}					
				}				
				for (Delivery destDelivery : nextTimeSlot.getAllDeliveries()){
						Node destination = destDelivery.getNode();
						Path path = dOrigin.calculateShortestPathTo(destination);
						destDeliveries.put(destination.getId(),path);							
										
				}				
				mapPath.put(origin.getId(),destDeliveries);	
					
			}
						
		}
		
		//last time slot
		TimeSlot lastTimeSlot = mTimeSlotList.get(nbSlots);	
		Dijkstra dLast;   	
		destDeliveries = new HashMap<Integer,Path>();
		for (Delivery delivery : lastTimeSlot.getAllDeliveries()){
			Node origin = delivery.getNode();
			dLast = new Dijkstra(origin);
			Path path = dLast.calculateShortestPathTo(mWarehouse);
			destDeliveries.put(mWarehouse.getId(),path);	
			mapPath.put(origin.getId(),destDeliveries);
		}
		
		return mapPath;
	}
	
}