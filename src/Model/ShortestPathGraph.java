package Model;

import java.util.*;

import tsp.Graph;

/**
 * 
 */
public class ShortestPathGraph implements Graph {

   
    /**
     * Stores the successors of the nodes of our graph
     * the key is the id of the node
     * the value is an array containing the Ids of the successor nodes.
     */
    protected Map<Integer,int[]> mSucc;

    /**
     * Stores the cost of each arc of our graph
     * the first key is the id of the origin node
     * the second key is the id of the destination node
     * the value is the cost of the arc (path)
     */
    protected Map<Integer,Map<Integer,Integer>> mCost;

    /**
     * Maximum cost
     */
    protected int mMax;

    /**
     * Minimum cost
     */
    protected int mMin;

    /**
     * Number of vertices
     */
    protected int mNb;
    
    /**
     * Initializes the graph 
     * @param Map<Integer, Map<Integer,Path>> pathMap 
     * the first key is the id of the origin node
     * the second key is the id of the destination node
     */
    public ShortestPathGraph(Map<Integer, Map<Integer,Path>> pathMap) {
    	
    	this.mNb = pathMap.size();
    	this.mMax = Integer.MAX_VALUE;
    	this.mMin = Integer.MIN_VALUE;
    	
    	initializeCosts(pathMap);
    	
    }
    /**
     * Initializes the map the costs and successors of our graph
     * @param Map<Integer, Map<Integer,Path>> pathMap
     */
    private void initializeCosts(Map<Integer, Map<Integer,Path>> pathMap){
    	//TODO: forgot to update the min and max, I'll do it tomorrow !
    	
    	this.mCost = new HashMap<Integer,Map<Integer,Integer>>();
    	this.mSucc = new HashMap<Integer,int[]>();
    	
    	for (Map.Entry<Integer, Map<Integer,Path>> entry : pathMap.entrySet())
    	{
    		int nodeId = entry.getKey();
    		Map<Integer,Integer> costMap = new HashMap<Integer,Integer>();
    		int[] succ = new int[entry.getValue().size()];   		
    		int cost = 0;
        	int key = 0;
        	int j=0;
    		for (Map.Entry<Integer,Path> value : entry.getValue().entrySet())
        	{	
    			key = value.getKey();
    			cost = value.getValue().getGlobalTime();
    			//updating the cost map and the array of successors for each key.
    			costMap.put(key,cost); 
    			succ[j]=key;   			
    			//updating the maximum and minimum costs of the graph
    			if(cost < this.mMin)
    				mMin = cost;
    			if(cost > this.mMax)
    				mMax = cost;
    			j++;
    					
        	}
    		
    	    this.mCost.put(nodeId,costMap);
    	    this.mSucc.put(nodeId, succ);
    	}
  	
    }


	@Override
	public int getMaxArcCost() {
		return this.mMax;
	}

	@Override
	public int getMinArcCost() {
		return this.mMin;
	}

	@Override
	public int getNbVertices() {		
		return this.mNb;
	}

	@Override
	public int[][] getCost() {
		return null;
	}

	@Override
	public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException {
		return this.mSucc.get(i);
	}
	
	@Override
	public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
		return this.mSucc.get(i).length;
	}

}