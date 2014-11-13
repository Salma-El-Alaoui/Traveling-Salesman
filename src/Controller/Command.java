package Controller;

import java.util.*;

/**
 * 
 */
public interface Command {

   /**
    * Execute the command
    * @return true if the command executed properly, false otherwise
    */
    public boolean execute();

    /**
     * Undo the command
     * @return true if the command was undone properly, false otherwise
     */
    public boolean undo();

    public String getName();
}