package Model;

import java.util.*;

/**
 * 
 */
public class Path {

    /**
     * Create a new empty Path
     */
    public Path() {
    	mPathTime = 0;
    	mSegmentList = new ArrayList<Segment>();
    }

    /**
     * Ordered list containing all the segments
     */
    protected List<Segment> mSegmentList;
    
    /**
     * Required time to travel across the path
     */
    protected int mPathTime;
    
    /**
     * Add the specified segment to the end of the segment list of the path
     * @param Segment segment
     */
    public void addSegment(Segment segment){
    	mSegmentList.add(segment);
    	mPathTime +=  segment.getTime();
    }

	/**
	 * Return the segment indicated in parameter
     * @param int index
     * @return The segment if the index is correct, null if the index is out of bounds
     */
    public Segment getSegment(int index) {
    	if(index < mSegmentList.size() && index >=0 ){
    		return mSegmentList.get(index);
    	}
    	return null;
    }

    /**
     * Return the time to follow the path
     * @return The total time needed
     */
    public int getGlobalTime() {
        return mPathTime;
    }


}