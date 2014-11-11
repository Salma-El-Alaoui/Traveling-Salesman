package View;

import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

import Model.Node;

/**
 * 
 */
public class NodeView implements View {

    /**
     * 
     */
    public NodeView() {
    }

    /**
     * 
     */
    protected Node mNode;

    /**
     * @return
     */
    public void setNodeSelected() {
        // TODO implement here
    }

    /**
     * @return
     */
    public Node getNode() {
        // TODO implement here
        return null;
    }

    /**
     * @param boolean 
     * @return
     */
    public void activateAdd(boolean addActivated) {
        // TODO implement here
    }

    /**
     * @param Node node
     * @return
     */
    public void addDelivery(Node node) {
        // TODO implement here
    }

	@Override
	public void paint(Graphics g) {
		if(mNode.hasDelivery())
		{
			g.setColor(Color.red);
		}else
		{
			g.setColor(Color.black);
		}
		g.fillOval(mNode.getX(), mNode.getY(), 20, 20);
		
	}

	@Override
	public void onClick(Event E) {
		// TODO Auto-generated method stub
		
	}

}