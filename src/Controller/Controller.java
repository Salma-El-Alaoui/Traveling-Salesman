package Controller;

import java.io.File;
import java.io.FileWriter;
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
		mInvoker = new Invoker();
		mNetwork = new Network();
	}

	/**
	 * 
	 */
	private Invoker mInvoker;

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

	
	private void updateUndoRedoFrame(){
		mFrame.setUndoRedo(mInvoker.getUndoName(), mInvoker.getRedoName());


	}



	public void onNodeSelected(Node node) {
		if(mState == State.ADDING_DELIVERY&&(node.hasDelivery() || node.isWarehouse())){
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
		mNetwork.setSelectedNode(node);
		mFrame.setSelectedNode(node);
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

	public void undoClicked() {
		mInvoker.undo();
		updateUndoRedoFrame();
		mNetwork.networkChanged();
	}

	public void redoClicked() {
		mInvoker.redo();
		updateUndoRedoFrame();
		mNetwork.networkChanged();
	}

	public void browseNetworkClicked() {
		int flagWarning = 0;
		
		FileChooserView networkChooserView = new FileChooserView();
		File f1 = networkChooserView.paintOpen();
		mNetwork = new Network();
		
		// User has canceled loading network
		if (f1!=null){
			try {
				mNetwork.parseNetworkFile(f1);
				flagWarning++;
				mFrame.setNetwork(mNetwork);
				mInvoker.clear();
				updateUndoRedoFrame();
				setState(State.NETWORK_LOADED);
			} catch (InvalidNetworkFileException
					| InvalidDeliveryRequestFileException ex) {
				new ErrorDialogView().paint(ex);
			} catch (WarningDeliveryRequestFile wa){
				if(flagWarning==0){
					mFrame.setNetwork(mNetwork);
					mInvoker.clear();
					updateUndoRedoFrame();
					setState(State.NETWORK_LOADED);
				}
				new WarningDialogView().paint(wa);
			}
		}

	}

	public void browseDeliveryClicked() {
		int flagWarning = 0;
		
		FileChooserView deliveryRequestChooserView = new FileChooserView();
		File f2 = deliveryRequestChooserView.paintOpen();
		// User cancel loading
		if (f2!=null){
			try {
				mNetwork.clearNodeContent();
				mNetwork.parseDeliveryRequestFile(f2); // Updates the network model => refreshes GraphPanel
				flagWarning++;
				setState(State.DELIVERY_REQUEST_LOADED);
				mInvoker.clear();
				updateUndoRedoFrame();
			} catch (InvalidNetworkFileException
					| InvalidDeliveryRequestFileException ex) {
				new ErrorDialogView().paint(ex);
			} catch (WarningDeliveryRequestFile wa){
				if(flagWarning==0){
					setState(State.DELIVERY_REQUEST_LOADED);
					mInvoker.clear();
					updateUndoRedoFrame();
				}
				new WarningDialogView().paint(wa);
			}
		}
	}

	public void calculateTourClicked(){
		mNetwork.getDeliveryRequest().calculateTour();
		mInvoker.clear();
		updateUndoRedoFrame();
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
		mInvoker.addAndExecute(addCommand);
		mNetwork.networkChanged();
		setState(State.TOUR_CALCULATED);
		updateUndoRedoFrame();
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
		mInvoker.addAndExecute(rmCommand);
		mNetwork.networkChanged();
		setState(State.TOUR_CALCULATED);
		updateUndoRedoFrame();
		// auto refreshing thanks to Observer pattern
	}
	
	public void saveRoadmapClicked(){
		FileChooserView roadMapSaverView = new FileChooserView();
		FileWriter fw = roadMapSaverView.paintSave();
		mNetwork.getDeliveryRequest().createRoadMap(fw);
	}

}
