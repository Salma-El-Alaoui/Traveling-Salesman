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
     * @param previousDelivery 
     * @param newDelivery
     * @return
     */
    public void insertDelivery(Delivery previousDelivery, Delivery newDelivery) {
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
        			nextNode=mDeliveryList.get(++i).getNode();
        		}else{
        			nextNode=mDeliveryList.get(0).getNode();
        		}
        	}
        }
        
        if(nextNode==null)
        {
        	System.out.println("Can't find node after the one we want to insert");
        	return;
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
        			mPathList.add(++i, secondPath);
        		}else{
        			mPathList.add(0, secondPath);
        		}
        		
        	}
        }
        updateHour();
    }

    /**
     * @param delivery 
     * @return
     */
    public void removeDelivery(Delivery delivery) {
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
         			previousNode=mDeliveryList.get(mDeliveryList.size()-1).getNode();
         			nextNode=mDeliveryList.get(1).getNode();
         		}else{
         			previousNode=mDeliveryList.get(i-1).getNode();
         			nextNode=mDeliveryList.get(0).getNode();
         		}
         	}
         }
        
        if(previousNode==null || nextNode==null)
        {
        	System.out.println("Can't find nodes around the one we want to remove");
        	return;
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

}