package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import Model.Node;

/**
 * 
 */
public class NodeView implements View {

	/**
	 * 
	 */
	public final static int DIAMETER = 14;

	/**
	 * 
	 */
	protected boolean mIsSelected = false;
	
	/**
	 * 
	 */
	protected double mScale;
	
	/**
	 * 
	 */
	protected int mTranslationX;
	
	/**
	 * 
	 */
	protected int mTranslationY;


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
	public void paint(Graphics g, double scale, int translationX, int translationY) {
		Graphics2D g2D = (Graphics2D) g;
		mScale = scale;
		mTranslationX = translationX;
		mTranslationY = translationY;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(mNode.hasDelivery())
		{
			g2D.setColor(Color.yellow);
		}else if(mNode.isWarehouse()) //TODO faire la méthode
		{
			g2D.setColor(Color.blue);
		}else
		{
			g2D.setColor(new Color(122,122,122));
		}
		g2D.fillOval((int)(scale*(mNode.getX()-DIAMETER/2))+translationX, (int)(scale*(mNode.getY()-DIAMETER/2)+translationY), 
				(int)(scale*DIAMETER), (int)(scale*DIAMETER));
	}

	@Override
	public boolean onClick(MouseEvent arg0) {
		int xClick = arg0.getX();
		int yClick = arg0.getY();
		int xNode = (int)((mScale*(mNode.getX()-DIAMETER/2))+mTranslationX);
		int yNode = (int)((mScale*(mNode.getY()-DIAMETER/2))+mTranslationY);
		if(xClick<xNode+mScale*DIAMETER && xClick>xNode-mScale*DIAMETER 
				&& yClick<yNode+mScale*DIAMETER && yClick>yNode-mScale*DIAMETER)
		{
			//TODO check command
			setNodeSelected();
			return true;
		
		}
		return false;

	}

}