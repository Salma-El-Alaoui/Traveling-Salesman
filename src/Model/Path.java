package Model;

import java.util.*;

/**
 * 
 */
public class Path {

    /**
     * 
     */
    public Path() {
    }

    /**
     * 
     */
    protected List<Segment> mSegmentList;

    /**
     * @param int index 
     * @return
     */
    public Segment getSegment(int index) {
        return mSegmentList.get(index);
    }

    /**
     * @return
     */
    public int getGlobalTime() {
        // TODO implement here
        return 0;
    }

}