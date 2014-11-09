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
     * @param Delivery 
     * @return
     */
    public void addDelivery(Delivery delivery) {
        // TODO implement here
    }

    /**
     * @param Path path 
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
    	 
    	 //TODO : updateHour()
    	 
    	 return previousNode;
    }

}