package Model;

import java.util.*;

import tsp.Graph;

/**
 * 
 */
public class ShortestPathGraph implements Graph {

    /**
     * 
     */
    public ShortestPathGraph() {
    }

    /**
     * 
     */
    protected Map<Integer,int[]> mSucc;

    /**
     * 
     */
    protected Map<Integer,Map<Integer,Integer>> mCost;

    /**
     * 
     */
    protected int mMax;

    /**
     * 
     */
    protected int mMin;

    /**
     * 
     */
    protected int mNb;

    /**
     * Constructor initializes the shortest paths graph and its costs
     * @param List<Path>
     */
    public void Graph(List<Path> pathList) {
        // TODO implement here
    }

	@Override
	public int getMaxArcCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinArcCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNbVertices() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[][] getCost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return 0;
	}

}