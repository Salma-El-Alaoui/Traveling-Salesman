package Controller;

import java.util.*;

/**
 * 
 */
public interface Command {

    /**
     * @return
     */
    public void execute();

    /**
     * @return
     */
    public void undo();

}