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
    private List<Segment> mSegmentList;
    
    /**
     * Required time to travel across the path
     */
    private float mPathTime;
    
    /**
     * Append the specified segment to the beginning of the segment list
     * @param Segment segment
     */
    public void addSegment(Segment segment){
    	mSegmentList.add(0, segment);
    	mPathTime +=  segment.getTime();
    }

	/**
	 * Return the segment indicated in parameter
     * @param index
     * @return The segment if the index is correct, null if the index is out of bounds
     */
    public Segment getSegment(int index) {
    	if(index < mSegmentList.size() && index >=0 ){
    		return mSegmentList.get(index);
    	}
    	return null;
    }

    /**
     *
     * @return The total time needed to follow the path
     */
    public int getGlobalTime() {
        return (int) mPathTime;
    }

	public List<Segment> getSegmentList() {
		return mSegmentList;
	}


}