package Controller;

import java.util.Stack;

import Model.Network;
import Model.Node;

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
     * @param previousNode node after which we insert the delivery
     */
    public void addDelivery(Node previousNode)
    {
    	Node selectedNode=mNetwork.getSelectedNode();
    	Command addCommand=new AddCommand(previousNode,selectedNode);
    	mCommandStack.add(addCommand);
    	addCommand.execute();
    	//TODO : refresh view
    }

}