package Model;

import java.util.*;

import tsp.*;

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
    protected Network network;

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
     * Computes the most interesting tour and initializes the tour object
     */
    public void calculateTour() {
    	
       Map<Integer, Map<Integer, Path>> pathMap = createPathMap();
       ShortestPathGraph graph = new ShortestPathGraph(pathMap);
       TSP tsp= new TSP(graph);
       SolutionState state;
       int bound = graph.getNbVertices() * graph.getMaxArcCost() + 1;
       int timeLimit = 1000; //TODO : how should the timeLimite increase ?
       do{
    	   state = tsp.solve(timeLimit, bound);
    	   bound = tsp.getTotalCost(); 
    	   timeLimit *= 2;
       }while(state != SolutionState.OPTIMAL_SOLUTION_FOUND && timeLimit<10000);
       
       int[]nodes=tsp.getNext();
       Tour tour = new Tour();
       
       int previous,next;
       for(int i = 0; i < nodes.length; i++){
    	   previous= nodes[i];  	   
    	   tour.addDelivery(this.network.getNode(previous).getDelivery());
    	   if(i!= nodes.length-1){
    		   next = nodes[i+1];
    		   tour.addPath(pathMap.get(previous).get(next));
    	   }
    	   tour.updateHour();
       }
       
    }

    /**
     * Create a delivery associated with previousNode and
     * insert it into tour after the delivery associated with previousNode
     * @param  NodepreviousNode
     * @param Node selectedNode
     * @return 
     */
    public boolean insertDelivery(Node previousNode, Node selectedNode) {
        Delivery previousDelivery=previousNode.getDelivery();
        Delivery newDelivery=new Delivery(selectedNode);
        return mTour.insertDelivery(previousDelivery, newDelivery);
    }

    /**
     * Remove from the tour the delivery associated with the node
     * @param Node node associated with the delivery to remove
     * @return the node before the removed delivery
     */
    public Node removeDelivery(Node node) {
        return mTour.removeDelivery(node.getDelivery());
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
	 * @return the initialized map
	 * 
	 */
	private Map<Integer, Map<Integer, Path>> createPathMap(){
		Map<Integer, Map<Integer, Path>> mapPath = new HashMap<Integer, Map<Integer, Path>>();
		int nbSlots =mTimeSlotList.size();
		
		//link the nodes in the first time slot with the warehouse
		TimeSlot firstTimeSlot = mTimeSlotList.get(0);		
		Dijkstra dWarehouse = new Dijkstra(mWarehouse);   	
    	Map<Integer, Path> destDeliveries = new HashMap<Integer,Path>();
		for (Delivery delivery : firstTimeSlot.getAllDeliveries()){
			Node destination = delivery.getNode();
			Path path = dWarehouse.calculateShortestPathTo(destination);
			destDeliveries.put(destination.getId(),path);						
		}
		mapPath.put(mWarehouse.getId(),destDeliveries);
		
		//internal links between the nodes in all the time slots
		for (int i = 0; i <nbSlots ; i++){
			TimeSlot timeSlot= mTimeSlotList.get(i); 				
			for (Delivery originDelivery : timeSlot.getAllDeliveries()){
				Node origin = originDelivery.getNode();				
				Dijkstra dOrigin = new Dijkstra(origin); 
				destDeliveries = new HashMap<Integer,Path>();
				
				//internal links in the current time slot
				for (Delivery destDelivery : timeSlot.getAllDeliveries()){
					if (destDelivery != originDelivery){//we should discuss this
						Node destination = destDelivery.getNode();
						Path path = dOrigin.calculateShortestPathTo(destination);
						destDeliveries.put(destination.getId(),path);							
					}					
				}
				
				//links with the next time slot
				if(i != nbSlots-1){ //if we're not dealing with last time slot
					TimeSlot nextTimeSlot = mTimeSlotList.get(i+1);	
					for (Delivery destDelivery : nextTimeSlot.getAllDeliveries()){
						Node destination = destDelivery.getNode();
						Path path = dOrigin.calculateShortestPathTo(destination);
						destDeliveries.put(destination.getId(),path);																	
				}				
				mapPath.put(origin.getId(),destDeliveries);	
				}
				
					
			}
						
		}
		
		//link the all the nodes in the last time slot with the warehouse
		TimeSlot lastTimeSlot = mTimeSlotList.get(nbSlots-1);	
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