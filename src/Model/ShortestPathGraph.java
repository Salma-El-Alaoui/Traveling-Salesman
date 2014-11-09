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
    public ShortestPathGraph(Map<Integer, Map<Integer,Path>> pathMap) {
    	
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