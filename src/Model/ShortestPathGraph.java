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
     * Costs matrix for choco
     */
    protected int[][]mCostMatrix;

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

    	for (Map.Entry<Integer, Map<Integer,Path>> entry : pathMap.entrySet())
    	{ 		
    		this.mNb += entry.getValue().size();
    	}
    	
    	this.mMax = Integer.MAX_VALUE;
    	this.mMin = Integer.MIN_VALUE;
    	
    	initializeCosts(pathMap);
    	
    }
    
    /**
     * 
     * @return
     */
    public Map<Integer,Integer>indexMap(Map<Integer, Map<Integer,Path>> pathMap){
    	
    	Map<Integer, Integer> indexMap = new HashMap<Integer,Integer>();
    	int i = 0;
    	for (Map.Entry<Integer, Map<Integer,Path>> entry : pathMap.entrySet())
    	{
    		indexMap.put(entry.getKey(),i);
    		i++;
    	}
    	return indexMap;
    }
    
    /**
     * Initializes the map the costs and successors of our graph
     * @param Map<Integer, Map<Integer,Path>> pathMap
     */
    private void initializeCosts(Map<Integer, Map<Integer,Path>> pathMap){
    	
    	
    	Map<Integer, Integer> indexMap = indexMap (pathMap);
    	this.mSucc = new HashMap<Integer,int[]>();
    	this.mCostMatrix = new int[this.mNb][this.mNb];
    	int length = 0;
    	int originNode = 0;
    	for (Map.Entry<Integer, Map<Integer,Path>> entry : pathMap.entrySet())
    	{
    		originNode = indexMap.get(entry.getKey());
    		length =entry.getValue().size();   		
    		Map<Integer,Integer> costMap = new HashMap<Integer,Integer>();
    		int[] succ = new int[length]; 
    		
    		int cost = 0;
        	int destNode = 0;
        	int j=0;
    		for (Map.Entry<Integer,Path> value : entry.getValue().entrySet())
        	{	
    			destNode = indexMap.get(value.getKey());
    			cost = value.getValue().getGlobalTime();
    			//updating the cost map and the array of successors for each key.
    			costMap.put(destNode,cost); 
    			succ[j]=destNode;
    			this.mCostMatrix[originNode][destNode]=cost;
    			//updating the maximum and minimum costs of the graph
    			if(cost < this.mMin)
    				mMin = cost;
    			if(cost > this.mMax)
    				mMax = cost;
    			j++;
    					
        	}
    	    this.mSucc.put(originNode, succ);
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
		return this.mCostMatrix;
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