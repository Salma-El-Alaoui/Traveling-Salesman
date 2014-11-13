package Controller;

import java.util.Stack;

public class Invoker {

	/**
	 * 
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


	public void addAndExecute(Command cmd){
		if(cmd.execute()){
			mUndoStack.add(cmd);
			mRedoStack.clear();
		}
	}
	
	public void undo(){
		if(!mUndoStack.isEmpty()){
			Command command = mUndoStack.pop();
			if (command.undo()) {
				mRedoStack.push(command);
			}
		}
	}

	public void redo(){
		if(!mRedoStack.isEmpty()){
			Command command = mRedoStack.pop();
			if (command.execute()) {
				mUndoStack.push(command);
			}
		}
	}
	
	public String getUndoName(){
		try{
			return mUndoStack.peek().getName();
		} catch (Exception e){
			return null;
		}
	}

	public String getRedoName(){
		try{
			return mRedoStack.peek().getName();
		} catch (Exception e){
			return null;
		}
	}
	
	public void clear(){
		mUndoStack.clear();
		mRedoStack.clear();
	}

}
