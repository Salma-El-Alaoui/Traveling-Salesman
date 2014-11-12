package Controller;

import java.util.*;

import Model.Node;

/**
 * 
 */
public class RemoveCommand implements Command {

	/**
	 * Node to remove
	 */
	Node mNode;
	
	/**
	 * Node before the one removed
	 */
	Node mPreviousNode;
	
    /**
     * Constructor
     * @param Node nodeToRemove the node associated with the delivery to remove
     */
    public RemoveCommand(Node nodeToRemove) {
		mNode=nodeToRemove;
    }

	@Override
	public boolean execute() {
		mPreviousNode=mNode.getNetwork().removeDelivery(mNode);
		if(mPreviousNode==null) return false;
		return true;
		
	}

	@Override
	public boolean undo() {
		return mNode.getNetwork().addDelivery(mPreviousNode, mNode);	
	}

}