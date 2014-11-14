package Controller;

import Model.Node;

/**
 * Command that adds a delivery to th existing tour
 */
public class AddCommand implements Command {

	/**
	 * Command's name
	 */
	private static final String ADD_NAME = "Ajout Livraison";

	/**
	 * Node corresponding to the delivery in the tour before the one to add
	 */
	private Node mPreviousNode;

	/**
	 * Node on which we want to add a delivery
	 */
	private Node mSelectedNode;

	/**
	 * Constructor of AddCommand
	 * 
	 * @param previousNode
	 *            Node corresponding to the delivery in the tour before the one
	 *            to add
	 * @param selectedNode
	 *            Node on which we want to add a delivery
	 */
	public AddCommand(Node previousNode, Node selectedNode) {
		mPreviousNode = previousNode;
		mSelectedNode = selectedNode;
	}

	@Override
	public boolean execute() {
		return mPreviousNode.getNetwork().addDelivery(mPreviousNode,
				mSelectedNode);
	}

	@Override
	public boolean undo() {
		if (mPreviousNode.getNetwork().removeDelivery(mSelectedNode) != null) {
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return ADD_NAME;
	}

}