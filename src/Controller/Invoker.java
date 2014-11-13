package Controller;

import java.util.Stack;

public class Invoker {

	/**
	 * Constructor
	 */
	public Invoker(){
		mRedoStack = new Stack<Command>();
		mUndoStack = new Stack<Command>();
	}

	/**
	 * All commands that can be redone
	 */
	private Stack<Command> mRedoStack;

	/**
	 * All commands that can be undone
	 */
	protected Stack<Command> mUndoStack;

	/**
	 * Adds a new command to the stack and executes it
	 * @param cmd command to execute
	 */
	public void addAndExecute(Command cmd){
		if(cmd.execute()){
			mUndoStack.add(cmd);
			mRedoStack.clear();
		}
	}

	/**
	 * Undo the last command executed
	 */
	public void undo(){
		if(!mUndoStack.isEmpty()){
			Command command = mUndoStack.pop();
			if (command.undo()) {
				mRedoStack.push(command);
			}
		}
	}

	/**
	 * Redo the last command executed
	 */
	public void redo(){
		if(!mRedoStack.isEmpty()){
			Command command = mRedoStack.pop();
			if (command.execute()) {
				mUndoStack.push(command);
			}
		}
	}

	/**
	 * Returns the name of the last command executed
	 * @return name of the last command executed
	 */
	public String getUndoName(){
		try{
			return mUndoStack.peek().getName();
		} catch (Exception e){
			return null;
		}
	}

	/**
	 * Returns the name of the last command undone
	 * @return the name of the last command undone
	 */
	public String getRedoName(){
		try{
			return mRedoStack.peek().getName();
		} catch (Exception e){
			return null;
		}
	}

	/**
	 * Empty the stack
	 */
	public void clear(){
		mUndoStack.clear();
		mRedoStack.clear();
	}
}
