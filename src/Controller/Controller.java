package Controller;

import java.io.File;
import java.util.Stack;

import Model.InvalidDeliveryRequestFileException;
import Model.InvalidNetworkFileException;
import Model.Network;
import Model.Node;
import View.ErrorDialogView;
import View.FileChooserView;
import View.Frame;

/**
 * 
 */
public class Controller {
	/**
     * 
     */
    public Controller() {
    	mFrame = new Frame(this);
    	mCommandStack = new Stack<Command>();
		mNetwork = new Network();
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
     * 
     */
    protected Frame mFrame;


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
		try {
			FileChooserView deliveryRequestChooserView = new FileChooserView();
			File f2 = deliveryRequestChooserView.paint();
			mNetwork.parseDeliveryRequestFile(f2);

		} catch (InvalidNetworkFileException
				| InvalidDeliveryRequestFileException ex) {
			new ErrorDialogView().paint(ex);
		}

	}

	/**
	 * @return
	 */
	public void browseNetworkClicked() {
		FileChooserView networkChooserView = new FileChooserView();
		File f1 = networkChooserView.paint();
		try {
			mNetwork.parseNetworkFile(f1);
		} catch (InvalidNetworkFileException
				| InvalidDeliveryRequestFileException ex) {
			new ErrorDialogView().paint(ex);
		}

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
		// auto refreshing thanks to Observer pattern
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
		// auto refreshing thanks to Observer pattern
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
		//auto refreshing thanks to Observer pattern
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
		//auto refreshing thanks to Observer pattern
	}

	public static void main(String args[]) {

		FileChooserView networkChooserView = new FileChooserView();
		File f1 = networkChooserView.paint();
		Network network = new Network();
		try {
			network.parseNetworkFile(f1);
			System.out.println(network);

			System.out.println("==============Delivery Request=============");

			FileChooserView deliveryRequestChooserView = new FileChooserView();
			File f2 = deliveryRequestChooserView.paint();
			network.parseDeliveryRequestFile(f2);

			System.out.println(network.getDeliveryRequest());
		} catch (InvalidNetworkFileException
				| InvalidDeliveryRequestFileException ex) {
			new ErrorDialogView().paint(ex);
		}

	}

}
