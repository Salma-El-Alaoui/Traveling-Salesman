package Controller;

import java.io.File;
import java.util.Stack;

import Model.InvalidDeliveryRequestFileException;
import Model.InvalidNetworkFileException;
import Model.Network;
import Model.Node;
import Model.WarningDeliveryRequestFile;
import View.ErrorDialogView;
import View.FileChooserView;
import View.Frame;
import View.WarningDialogView;

/**
 * 
 */
public class Controller {

	public enum State {
		NEW, NETWORK_LOADED, DELIVERY_REQUEST_LOADED, TOUR_CALCULATED, TOUR_NODE_SELECTED, OTHER_NODE_SELECTED, ADDING_DELIVERY
	}

	private State mState;

	/**
     * 
     */
	public Controller() {
		mState = State.NEW;
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
	 * Change the controller's state and update the frame
	 * 
	 * @param state
	 *            new state
	 */
	private void setState(State state) {
		mState = state;
		mFrame.changeState(mState);
	}

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
		}  catch (WarningDeliveryRequestFile wa){
			new WarningDialogView().paint(wa);
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
		} catch (WarningDeliveryRequestFile wa){
			new WarningDialogView().paint(wa);
		}

	}

	/**
	 * Update the Controller's state when add delivery menu item is clicked
	 */
	public void addDeliveryClicked() {
		if (mState.equals(State.OTHER_NODE_SELECTED)) {
			setState(State.ADDING_DELIVERY);
		} else if (mState.equals(State.ADDING_DELIVERY)) {
			setState(State.OTHER_NODE_SELECTED);
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
		setState(State.DELIVERY_REQUEST_LOADED);
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
	public void undo() {
		if (mCommandStack.isEmpty()) {
			System.out.println("Empty command stack");
			return;
		}

		Command command = mCommandStack.pop();

		if (command.undo()) {
			mUndoStack.push(command);
		} else {
			System.out.println("Can't undo command");
		}
		// auto refreshing thanks to Observer pattern
	}

	/**
	 * Execute the last command undone
	 */
	public void redo() {
		if (mUndoStack.isEmpty()) {
			System.out.println("Empty undo command stack");
			return;
		}

		Command command = mUndoStack.pop();

		if (command.execute()) {
			mCommandStack.push(command);
		} else {
			System.out.println("Can't redo command");
		}
		// auto refreshing thanks to Observer pattern
	}

	public void loadNetworkXML() {
		FileChooserView networkChooserView = new FileChooserView();
		File f1 = networkChooserView.paint();
		mNetwork = new Network();
		try {
			mNetwork.parseNetworkFile(f1);
			mFrame.setNetwork(mNetwork);
			setState(State.NETWORK_LOADED);
		} catch (InvalidNetworkFileException
				| InvalidDeliveryRequestFileException ex) {
			new ErrorDialogView().paint(ex);
		} catch (WarningDeliveryRequestFile wa){
			new WarningDialogView().paint(wa);
		}
		
	}

	public void loadDeliveriesXML() {
		FileChooserView deliveryRequestChooserView = new FileChooserView();
		File f2 = deliveryRequestChooserView.paint();
		try {
			mNetwork.parseDeliveryRequestFile(f2); // Updates the network model => refreshes GraphPanel
			setState(State.DELIVERY_REQUEST_LOADED);
		} catch (InvalidNetworkFileException
				| InvalidDeliveryRequestFileException ex) {
			new ErrorDialogView().paint(ex);
		} catch (WarningDeliveryRequestFile wa){
			new WarningDialogView().paint(wa);
		}

		
	}
	
	public void calculateTour(){
		mNetwork.getDeliveryRequest().calculateTour();
		setState(State.TOUR_CALCULATED);
	}

}
