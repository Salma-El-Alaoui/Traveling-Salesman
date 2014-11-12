package Controller;

import java.util.*;

import Model.Node;

/**
 * 
 */
public class AddCommand implements Command {

	/**
	 * 
	 */
	protected Node mPreviousNode;
	
	/**
	 * 
	 */
	protected Node mSelectedNode;
	
	
    /**
     * 
     */
    public AddCommand(Node previousNode, Node selectedNode) {
    	mPreviousNode=previousNode;
    	mSelectedNode=selectedNode;
    }

	@Override
	public boolean execute() {
		return mPreviousNode.getNetwork().addDelivery(mPreviousNode, mSelectedNode);

	}

	@Override
	public boolean undo() {
		if(mPreviousNode.getNetwork().removeDelivery(mSelectedNode)!=null)
		{
			return true;
		}
		return false;
	}

}