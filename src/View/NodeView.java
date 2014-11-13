package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import Model.Node;

/**
 * Class NodeView
 */
public class NodeView implements View {

	/**
	 * Diameter of the circle
	 */
	public final static int DIAMETER = 14;

	/**
	 * Color of the warehouse
	 */
	private final static Color WAREHOUSE_COLOR = Color.BLUE;

	/**
	 * Color of simple node
	 */
	private final static Color NODE_COLOR = Color.GRAY;

	/**
	 * Color of delivered node
	 */
	private final static Color DELIVERY_COLOR = Color.YELLOW;

	/**
	 * Color of node with delivery error
	 */
	private final static Color DELIVERY_ERROR_COLOR = Color.RED;

	/**
	 * If the node is selected or not
	 */
	protected boolean mIsSelected = false;

	/**
	 * Scale used for the zoom
	 */
	protected double mScale;

	/**
	 * Variable used for the translation of the graph
	 */
	protected int mTranslationX;

	/**
	 * Variable used for the translation of the graph
	 */
	protected int mTranslationY;

	/**
	 * Node corresponding to the NodeView
	 */
	protected Node mNode;


	/**
	 * Constructor of NodeView
	 * @param node Node for the View
	 */
	public NodeView(Node node) {
		mNode = node;
	}


	/**
	 * @return current Node
	 */
	public Node getNode() {
		// TODO implement here
		return mNode;
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
			if(mNode.getDelivery().isInTimeslot()){
				g2D.setColor(DELIVERY_COLOR);				
			} else {
				g2D.setColor(DELIVERY_ERROR_COLOR);
			}
		}else if(mNode.isWarehouse()) //TODO faire la méthode
		{
			g2D.setColor(WAREHOUSE_COLOR);
		}else
		{
			g2D.setColor(NODE_COLOR);
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

			return true;

		}
		return false;

	}

}