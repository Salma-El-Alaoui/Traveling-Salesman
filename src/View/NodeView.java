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
	 * Color of the warehouse Node
	 */
	private final static Color WAREHOUSE_COLOR = new Color(85, 83, 184);

	/**
	 * Color of a classic Node
	 */
	private final static Color NODE_COLOR = Color.DARK_GRAY;

	/**
	 * Color of a delivery Node
	 */
	private final static Color DELIVERY_COLOR = new Color(68, 148, 15);

	/**
	 * Color of a delivery Node with an error
	 */
	private final static Color DELIVERY_ERROR_COLOR = new Color(148, 24, 15);

	/**
	 * Create the Associated view to the node
	 * @param node Associated Node
	 */
	public NodeView(Node node) {
		mNode = node;
	}

	/**
	 * Scale factor
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
	 * Associated Node
	 */
	protected Node mNode;

	/**
	 * Return the Associated Node
	 * @return Associated Node
	 */
	public Node getNode() {
		return mNode;
	}

	@Override
	public void paint(Graphics g, double scale, int translationX,
			int translationY) {
		Graphics2D g2D = (Graphics2D) g;
		mScale = scale;
		mTranslationX = translationX;
		mTranslationY = translationY;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2D.setColor(getNodeColor());
		g2D.fillOval((int) (scale * (mNode.getX() - DIAMETER / 2))
				+ translationX,
				(int) (scale * (mNode.getY() - DIAMETER / 2) + translationY),
				(int) (scale * DIAMETER), (int) (scale * DIAMETER));
	}

	@Override
	public boolean onClick(MouseEvent arg0) {
		int xClick = arg0.getX();
		int yClick = arg0.getY();
		int xNode = (int) ((mScale * (mNode.getX() - DIAMETER / 2)) + mTranslationX);
		int yNode = (int) ((mScale * (mNode.getY() - DIAMETER / 2)) + mTranslationY);
		if (xClick < xNode + mScale * DIAMETER
				&& xClick > xNode - mScale * DIAMETER
				&& yClick < yNode + mScale * DIAMETER
				&& yClick > yNode - mScale * DIAMETER) {
			return true;
		}
		return false;
	}
	
	/**
	 * Return the color according to the node state
	 * @return Color of the node
	 */
	private Color getNodeColor(){
		Color c;
		if (mNode.hasDelivery()) {
			if (mNode.getDelivery().isInTimeslot()) {
				c = DELIVERY_COLOR;
			} else {
				c = DELIVERY_ERROR_COLOR;
			}
		} else if (mNode.isWarehouse())
		{
			c = WAREHOUSE_COLOR;
		} else {
			c = NODE_COLOR;
		}
		if(mNode.isSelected()){
			c = c.brighter().brighter();
		}
		return c;
	}

}