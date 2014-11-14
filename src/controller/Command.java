package controller;

/**
 * Interface Command
 */
public interface Command {

	/**
	 * Execute the command
	 * 
	 * @return true if the command executed properly, false otherwise
	 */
	public boolean execute();

	/**
	 * Undo the command
	 * 
	 * @return true if the command was undone properly, false otherwise
	 */
	public boolean undo();

	/**
	 * Return the name of the command
	 * 
	 * @return name of the command
	 */
	public String getName();
}