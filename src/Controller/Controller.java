package Controller;

import java.io.File;
import java.io.FileWriter;

import org.w3c.dom.Document;

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
 * Controller
 */
public class Controller {

	public enum State {
		NEW, NETWORK_LOADED, DELIVERY_REQUEST_LOADED, TOUR_CALCULATED, TOUR_NODE_SELECTED, WAREHOUSE_SELECTED, OTHER_NODE_SELECTED, ADDING_DELIVERY
	}

	/**
	 * Current state of Controller
	 */
	private State mState;

	/**
	 * Constructor
	 */
	public Controller() {
		mState = State.NEW;
		mFrame = new Frame(this);
		mInvoker = new Invoker();
		mNetwork = new Network();
	}

	/**
	 * Commands' invoker
	 */
	private Invoker mInvoker;

	/**
	 * Current network
	 */
	protected Network mNetwork;

	/**
	 * Current frame
	 */
	protected Frame mFrame;

	/**
	 * Changes the controller's state and update the frame
	 * 
	 * @param state
	 *            new state
	 */
	private void setState(State state) {
		mState = state;
		mFrame.changeState(mState);
	}

	/**
	 * Updates the undo redo buttons of the frame
	 */
	private void updateUndoRedoFrame() {
		mFrame.setUndoRedo(mInvoker.getUndoName(), mInvoker.getRedoName());

	}

	/**
	 * Handles the way to behave when a node is selected depending on the
	 * current state
	 * 
	 * @param node
	 *            Node selected
	 */
	public void onNodeSelected(Node node) {
		if (mState == State.ADDING_DELIVERY
				&& (node.hasDelivery() || node.isWarehouse())) {
			addDelivery(node);
		}
		if (mState == State.TOUR_CALCULATED
				|| mState == State.OTHER_NODE_SELECTED
				|| mState == State.WAREHOUSE_SELECTED
				|| mState == State.TOUR_NODE_SELECTED
				|| mState == State.ADDING_DELIVERY) {
			if (node.hasDelivery()) {
				setState(Controller.State.TOUR_NODE_SELECTED);
			} else if (node.isWarehouse()) {
				setState(State.WAREHOUSE_SELECTED);
			} else {
				setState(Controller.State.OTHER_NODE_SELECTED);
			}

		}
		mNetwork.setSelectedNode(node);
		mNetwork.networkChanged();
		mFrame.setSelectedNode(node);
	}

	/**
	 * Update the Controller's state when add delivery menu item is clicked
	 */
	public void addDeliveryClicked() {
		if (mState.equals(State.OTHER_NODE_SELECTED)) {
			setState(State.ADDING_DELIVERY);
		} else if (mState == State.ADDING_DELIVERY) {
			if (mNetwork.getSelectedNode().hasDelivery()) {
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
	 * Undo the last command executed when the undo button is clicked
	 */
	public void undoClicked() {
		mInvoker.undo();
		updateUndoRedoFrame();
		mNetwork.networkChanged();
	}

	/**
	 * Redo the last command undone when the redo button is clicked
	 */
	public void redoClicked() {
		mInvoker.redo();
		updateUndoRedoFrame();
		mNetwork.networkChanged();
	}

	/**
	 * Opens a window to select the network and loads it
	 */
	public void browseNetworkClicked() {
		int flagWarning = 0;

		FileChooserView networkChooserView = new FileChooserView();
		File f1 = networkChooserView.paintOpen();

		// User has canceled loading network
		if (f1 != null) {
			try {
				mNetwork = new Network();
				mNetwork.parseNetworkFile(f1);
				flagWarning++;
				mFrame.setNetwork(mNetwork);
				mInvoker.clear();
				updateUndoRedoFrame();
				setState(State.NETWORK_LOADED);
			} catch (InvalidNetworkFileException
					| InvalidDeliveryRequestFileException ex) {
				new ErrorDialogView().paint(ex);
			} catch (WarningDeliveryRequestFile wa) {
				if (flagWarning == 0) {
					mFrame.setNetwork(mNetwork);
					mInvoker.clear();
					updateUndoRedoFrame();
					setState(State.NETWORK_LOADED);
				}
				new WarningDialogView().paint(wa);
			}
		}

	}

	/**
	 * Opens a window to select the delivery request and loads it
	 */
	public void browseDeliveryClicked() {
		int flagWarning = 0;

		FileChooserView deliveryRequestChooserView = new FileChooserView();
		File f2 = deliveryRequestChooserView.paintOpen();
		// User cancel loading
		if (f2 != null) {
			try {
				mNetwork.clearNodeContent();
				mNetwork.parseDeliveryRequestFile(f2); // Updates the network
														// model => refreshes
														// GraphPanel
				flagWarning++;
				setState(State.DELIVERY_REQUEST_LOADED);
				mInvoker.clear();
				updateUndoRedoFrame();
			} catch (InvalidNetworkFileException
					| InvalidDeliveryRequestFileException ex) {
				new ErrorDialogView().paint(ex);
			} catch (WarningDeliveryRequestFile wa) {
				if (flagWarning == 0) {
					setState(State.DELIVERY_REQUEST_LOADED);
					mInvoker.clear();
					updateUndoRedoFrame();
				}
				new WarningDialogView().paint(wa);
			}
		}
	}

	/**
	 * Calculates the tour when the associated button is clicked
	 */
	public void calculateTourClicked() {
		try {
			mNetwork.getDeliveryRequest().calculateTour();
		} catch (Exception e) {
			Exception ex = new Exception(
					"Erreur inconnue lors du calcul de la tournée, les fichiers sont peut-être incorrects.");
			new WarningDialogView().paint(ex);
			return;
		}
		mInvoker.clear();
		updateUndoRedoFrame();
		if (mNetwork.getSelectedNode() != null) {
			if (mNetwork.getSelectedNode().hasDelivery()) {
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
		if (!mInvoker.addAndExecute(rmCommand)) {
			Exception e = new Exception(
					"Impossible de supprimer le dernier noeud");
			new WarningDialogView().paint(e);
		}
		mNetwork.networkChanged();
		setState(State.TOUR_CALCULATED);
		updateUndoRedoFrame();
		// auto refreshing thanks to Observer pattern
	}

	/**
	 * Generate the roadmap when the associated button is clicked
	 */
	public void saveRoadmapClicked() {
		FileChooserView roadMapSaverView = new FileChooserView();
		FileWriter fw = roadMapSaverView.paintSave();
		mNetwork.getDeliveryRequest().createRoadMap(fw);
	}

}
