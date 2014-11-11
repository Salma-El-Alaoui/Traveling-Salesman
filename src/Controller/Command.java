package Controller;

import java.util.*;

/**
 * 
 */
public interface Command {

    /**
     * @return
     */
    public boolean execute();

    /**
     * @return
     */
    public void undo();

}