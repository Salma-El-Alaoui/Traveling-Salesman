package Controller;

import java.io.File;
import java.util.*;

import Model.Network;
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
	protected Set<Command> mCommandList;

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

	public static void main(String args[]) {

		FileChooserView networkChooserView = new FileChooserView();
		File f1 = networkChooserView.paint();
		Network network = new Network();
		network.parseNetworkFile(f1);
		System.out.println(network);
		FileChooserView deliveryRequestChooserView = new FileChooserView();
		File f2 = deliveryRequestChooserView.paint();
		network.parseDeliveryRequestFile(f2);

	}
}