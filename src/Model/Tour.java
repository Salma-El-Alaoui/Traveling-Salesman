package Model;

import java.util.*;

/**
 * 
 */
public class Tour {

    /**
     * 
     */
    public Tour() {
    }

    protected Node mWarehouse;
    /**
     * 
     */
    protected List<Delivery> mDeliveryList;

    /**
     * 
     */
    protected List<Path> mPathList;
    
    /**
     * Departure hour from the warehouse
     */
    protected int mStartHour;
    
    /**
     * @param delivery 
     * @return
     */
    public void addDelivery(Delivery delivery) {
        // TODO implement here
    }

    /**
     * @param path 
     * @return
     */
    public void addPath(Path path) {
        // TODO implement here
    }

    /**
     * Insert newDelivery after previousDelivery into the tour and recalculate locally the path
     * @param previousDelivery
     * @param newDelivery
     */
    public boolean insertDelivery(Delivery previousDelivery, Delivery newDelivery) {
        newDelivery.setTimeSlot(previousDelivery.getTimeSlot());
        
        Node previousNode=previousDelivery.getNode();
        Node newNode=newDelivery.getNode();
        Node nextNode=null;
        
        for(int i=0; i<mDeliveryList.size(); i++)
        {
        	if(mDeliveryList.get(i).getNode().getId()==previousNode.getId())
        	{
        		if(i!=mDeliveryList.size()-1)
        		{
        			nextNode=mDeliveryList.get(i+1).getNode();
        			mDeliveryList.add(i+1, newDelivery);
        			break;
        		}else{
        			nextNode=mWarehouse;
        			mDeliveryList.add(newDelivery);
        			break;
        		}
        	}
        }
        
        if(nextNode==null)
        {
        	System.out.println("Can't find node after the one we want to insert");
        	return false;
        }
        
        Network network=previousNode.getNetwork();
        Path firstPath=network.calculateShortestPath(previousNode, newNode);
        Path secondPath=network.calculateShortestPath(newNode, nextNode);
        
        for(int i=0; i<mPathList.size(); i++)
        {
        	Node departureNode=mPathList.get(i).getSegment(0).getDepartureNode();
        	if(departureNode.getId()==previousNode.getId())
        	{
        		mPathList.set(i, firstPath);
        		if(i!=mPathList.size()-1)
        		{
        			mPathList.add(i+1, secondPath);
        		}else{
        			mPathList.add(0, secondPath);
        		}
        		
        	}
        }
        
        updateHour();
        if(newDelivery.getDeliveryHour()>newDelivery.getTimeSlot().getEndHour())
        {
        	newDelivery.setTimeSlot(nextNode.getDelivery().getTimeSlot());
        }

        return true;
    }

    /**
     * Remove the delivery from the tour and recalculate locally the path
     * @param Delivery delivery to remove
     * @return the node before the removed delivery
     */
    public Node removeDelivery(Delivery delivery) {
    	Node node=delivery.getNode();
    	Node previousNode=null;
    	Node nextNode=null;
        
    	 for(int i=0; i<mDeliveryList.size(); i++)
         {
         	if(mDeliveryList.get(i).getNode().getId()==node.getId())
         	{
         		if(i!=mDeliveryList.size()-1 && i!=0)
         		{
         			previousNode=mDeliveryList.get(i-1).getNode();
         			nextNode=mDeliveryList.get(i+1).getNode();
         		}else if(i!=mDeliveryList.size()-1 && i==0){
         			previousNode=mWarehouse;
         			nextNode=mDeliveryList.get(1).getNode();
         		}else{
         			previousNode=mDeliveryList.get(i-1).getNode();
         			nextNode=mWarehouse;
         		}
         		mDeliveryList.remove(i);
     			break;
         	}
         }
        
        if(previousNode==null || nextNode==null)
        {
        	System.out.println("Can't find nodes around the one we want to remove");
        	return null;
        }
        
        Network network=node.getNetwork();
        Path newPath=network.calculateShortestPath(previousNode, nextNode);
    	
    	 for(int i=0; i<mPathList.size(); i++)
         {
         	Node departureNode=mPathList.get(i).getSegment(0).getDepartureNode();
         	if(departureNode.getId()==node.getId())
         	{
         		mPathList.set(i, newPath);
         		if(i!=0)
         		{
         			mPathList.remove(i-1);
         		}else{
         			mPathList.remove(mPathList.size()-1);
         		}
         	}
         }
    	 updateHour(); 	 
    	 return previousNode;
    }
    
    /**
     * Update the hours of all the deliveries
     */
    public void updateHour(){
    	// Init the
    	mStartHour = mDeliveryList.get(0).getTimeSlot().getStartHour() 
		- mPathList.get(0).getGlobalTime();
    	int globalTime = mStartHour;
    	for(int i=0 ; i<mDeliveryList.size() ; i++){
    		Delivery delivery = mDeliveryList.get(i);
    		delivery.setArrivalHour(globalTime);
    		globalTime = delivery.getDepartureHour();
    	}
    }

	public List<Delivery> getmDeliveryList() {
		return mDeliveryList;
	}
    

}