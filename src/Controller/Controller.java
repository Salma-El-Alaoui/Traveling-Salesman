package Controller;

import java.util.Stack;

import Model.Network;

import java.io.File;
import java.util.*;

import Model.Node;
import View.FileChooserView;

/**
 * 
 */
public class Controller {
	/**
     * 
     */
	public Controller() {

	}

	/**
     * 
     */

	protected Stack<Command> mCommandStack;
	
	protected Stack<Command> mUndoStack;

	/**
     * 
     */
	protected Network mNetwork;


	/**
	 * @return
	 */
	public Node getSelectedNode() {
		// TODO implement here
		return null;
	}

	/**
	 * @return
	 */
	public void browseDeliveryClicked() {
		// TODO implement here
	}

	/**
	 * @return
	 */
	public void browseNetworkClicked() {
		// TODO implement here
	}

	
	/**
	 * Add the selected node as a delivery after previousNode
	 * 
	 * @param previousNode
	 *            node after which we insert the delivery
	 */
	public void addDelivery(Node previousNode) {
		Node selectedNode = mNetwork.getSelectedNode();
		Command addCommand = new AddCommand(previousNode, selectedNode);
		if (addCommand.execute()) {
			mCommandStack.push(addCommand);
		}
		// TODO : refresh view
	}

	/**
	 * Remove from the tour the delivery associated with the node
	 * 
	 * @param node
	 *            the node associated with the delivery to remove
	 */
	public void removeDelivery(Node node) {
		Command rmCommand = new RemoveCommand(node);
		if (rmCommand.execute()) {
			mCommandStack.push(rmCommand);
		}
		// TODO : refresh view
	}
	
	/**
	 * Undo the last command executed
	 */
	public void undo()
	{
		if(mCommandStack.isEmpty())
		{
			System.out.println("Empty command stack");
			return;
		}
		
		Command command=mCommandStack.pop();
		
		if(command.undo())
		{
			mUndoStack.push(command);
		}
		else
		{
			System.out.println("Can't undo command");
		}
		// TODO : refresh view
	}
	
	/**
	 * Execute the last command undone
	 */
	public void redo(){
		if(mUndoStack.isEmpty())
		{
			System.out.println("Empty undo command stack");
			return;
		}
		
		Command command=mUndoStack.pop();
		
		if(command.execute())
		{
			mCommandStack.push(command);
		}
		else
		{
			System.out.println("Can't redo command");
		}	
		// TODO : refresh view
	}
	
	public static void main(String args[]) {

		FileChooserView networkChooserView = new FileChooserView();
		File f1 = networkChooserView.paint();
		Network network = new Network();
		network.parseNetworkFile(f1);
		System.out.println(network);

		System.out.println("==============Delivery Request=============");

		FileChooserView deliveryRequestChooserView = new FileChooserView();
		File f2 = deliveryRequestChooserView.paint();
		network.parseDeliveryRequestFile(f2);

		System.out.println(network.getDeliveryRequest());
	}


}
