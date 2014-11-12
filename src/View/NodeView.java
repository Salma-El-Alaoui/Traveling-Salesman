package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import Model.Node;

/**
 * 
 */
public class NodeView implements View {

	/**
	 * 
	 */
	public final static int DIAMETER = 10;
	
	/**
	 * 
	 */
	protected boolean mIsSelected = false;
	
	
    /**
     * 
     */
    public NodeView(Node node) {
    	mNode = node;
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
        return mNode;
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
			g.setColor(Color.orange);
		}else if(mNode.isWarehouse()) //TODO faire la méthode
		{
			g.setColor(Color.blue);
		}else
		{
			g.setColor(Color.black);
		}
		g.fillOval(mNode.getX()-DIAMETER/2, mNode.getY()-DIAMETER/2, DIAMETER, DIAMETER);
	}

	@Override
	public void onClick(MouseEvent arg0) {
		int xClick = arg0.getX();
		int yClick = arg0.getY();
		int xNode = mNode.getX();
		int yNode = mNode.getY();
		if(xClick<xNode+DIAMETER && xClick<xNode-DIAMETER 
				&& yClick<yNode+DIAMETER && yClick<yNode-DIAMETER)
		{
			//TODO check command
			setNodeSelected();
		}
		
	}

}