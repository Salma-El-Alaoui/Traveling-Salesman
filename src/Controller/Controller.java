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

	/**
	 * 
	 */
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
	 * 
	 */
	public void onNodeSelected(Node node) {
		mNetwork.setSelectedNode(node);
		mFrame.setSelectedNode(node);
		if(mState == State.ADDING_DELIVERY && node.hasDelivery()){
			addDelivery(node);
		} 
		if(mState == State.TOUR_CALCULATED 
				|| mState == State.OTHER_NODE_SELECTED 
				|| mState == State.TOUR_NODE_SELECTED
				|| mState == State.ADDING_DELIVERY) {
			if(node.hasDelivery()){
				setState(Controller.State.TOUR_NODE_SELECTED);			
			} else {
				setState(Controller.State.OTHER_NODE_SELECTED);			
			}			
		}
	}

	/**
	 * Update the Controller's state when add delivery menu item is clicked
	 */
	public void addDeliveryClicked() {
		if (mState.equals(State.OTHER_NODE_SELECTED)) {
			setState(State.ADDING_DELIVERY);
		} else if(mState == State.ADDING_DELIVERY){
			if(mNetwork.getSelectedNode().hasDelivery()){
				setState(Controller.State.TOUR_NODE_SELECTED);			
			} else {
				setState(Controller.State.OTHER_NODE_SELECTED);			
			}			

		}
	}

	/**
	 * Update the Controller's state when remove delivery menu item is clicked
	 */
	public void removeDeliveryClicked() {
		if (mState == State.TOUR_NODE_SELECTED) {
			removeDelivery(mNetwork.getSelectedNode());
		}
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

	public void browseNetworkClicked() {
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
		}
		
	}

	public void browseDeliveryClicked() {
		FileChooserView deliveryRequestChooserView = new FileChooserView();
		File f2 = deliveryRequestChooserView.paint();
		try {
			mNetwork.parseDeliveryRequestFile(f2); // Updates the network model => refreshes GraphPanel
			setState(State.DELIVERY_REQUEST_LOADED);
		} catch (InvalidNetworkFileException
				| InvalidDeliveryRequestFileException ex) {
			new ErrorDialogView().paint(ex);
		}

		
	}

	public void calculateTourClicked(){
		mNetwork.getDeliveryRequest().calculateTour();
		if(mNetwork.getSelectedNode() != null){
			if(mNetwork.getSelectedNode().hasDelivery()){
				setState(State.TOUR_NODE_SELECTED);
			} else {
				setState(State.OTHER_NODE_SELECTED);
			}
		} else {
			setState(State.TOUR_CALCULATED);			
		}
	}

	/**
	 * Add the selected node as a delivery after previousNode
	 * 
	 * @param previousNode
	 *            node after which we insert the delivery
	 */
	private void addDelivery(Node previousNode) {
		Node selectedNode = mNetwork.getSelectedNode();
		Command addCommand = new AddCommand(previousNode, selectedNode);
		if (addCommand.execute()) {
			mCommandStack.push(addCommand);
		}
		setState(State.TOUR_CALCULATED);
		// auto refreshing thanks to Observer pattern
	}

	/**
	 * Remove from the tour the delivery associated with the node
	 * 
	 * @param node
	 *            the node associated with the delivery to remove
	 */
	private void removeDelivery(Node node) {
		Command rmCommand = new RemoveCommand(node);
		if (rmCommand.execute()) {
			mCommandStack.push(rmCommand);
		}
		setState(State.TOUR_CALCULATED);
		// auto refreshing thanks to Observer pattern
	}
	
}
