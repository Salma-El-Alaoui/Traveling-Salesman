package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Model.Network;
import Model.Node;

/**
 * Class GraphPanel
 */

public class GraphPanel extends JPanel implements MouseWheelListener,
		MouseMotionListener, MouseListener, Observer {

	/**
	 * Speed at which the panel gets dragged
	 */
	private static final int DRAG_SPEED = 1;

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
	 * Used to prevent the graph from returning to his basic position when
	 * dragged
	 */
	protected int mTempX;

	/**
	 * Used to prevent the graph from returning to his basic position when
	 * dragged
	 */
	protected int mTempY;

	/**
	 * Coordinate in Y of the click
	 */
	protected int mMouseClickY;

	/**
	 * Coordinate in X of the click
	 */
	protected int mMouseClickX;

	/**
	 * List of NodeView displayed in the graph
	 */
	protected List<NodeView> mListNodeView;

	/**
	 * NetworkView displayed in the graph
	 */
	protected NetworkView mNetworkView;

	/**
	 * TourView displayed in the graph
	 */
	protected TourView mTourView;

	/**
	 * Set if the Optimod screen is displayed
	 */
	protected boolean mOptimodDrawn;
	
	/**
	 * Image for welcome screen
	 */
	protected Image mOptimodImage;

	/**
	 * Constructor of GraphPanel
	 */
	public GraphPanel() {
		this.setPreferredSize(new Dimension(700, 600));
		mScale = 600.0 / 900;
		mTranslationX = 0;
		mTranslationY = 0;
		mTempX = 0;
		mTempY = 0;
		mOptimodDrawn = true;
		try {
			mOptimodImage = ImageIO.read(new File("img/optimod.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addMouseWheelListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (mOptimodDrawn) {
			g.drawImage(mOptimodImage,(this.getWidth()-mOptimodImage.getWidth(null))/2,(this.getHeight()-mOptimodImage.getHeight(null))/2, null);
		} else {
			if (mNetworkView != null) {
				mNetworkView.paint(g, mScale, mTranslationX, mTranslationY);
			}
			if (mTourView != null) {
				mTourView.paint(g, mScale, mTranslationX, mTranslationY);
			}
			if (mListNodeView != null) {
				for (NodeView nv : mListNodeView) {
					nv.paint(g, mScale, mTranslationX, mTranslationY);
				}
			}
		}
	}

	/**
	 * 
	 * @return The list of NodeViews
	 */
	public List<NodeView> getListNodeView() {
		return mListNodeView;
	}

	/**
	 * Set the Network of GraphPanel
	 * 
	 * @param n
	 */
	public void setNetwork(Network n) {
		if (n != null) {
			n.addObserver(this);
			mNetworkView = new NetworkView(n, n.getSegmentList());
			mListNodeView = new ArrayList<NodeView>();
			mTourView = null;
			Map<Integer, Node> mapNode = n.getNodesList();
			for (int i = 0; i < mapNode.size(); i++) {
				mListNodeView.add(new NodeView(mapNode.get(i)));
			}
		} else {
			mNetworkView = null;
			mListNodeView = null;
			mTourView = null;
			mListNodeView = null;
		}
		repaint();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof Network) {
			Network n = (Network) arg0;
			if (n.getDeliveryRequest() != null
					&& n.getDeliveryRequest().getTour() != null) {
				mTourView = new TourView(n.getDeliveryRequest().getTour());
			} else {
				mTourView = null;
			}
		}
		repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		if (arg0.getWheelRotation() == -1) {
			mScale += 0.1;
			if (mScale > 4) {
				mScale = 4;
			}
		} else if (arg0.getWheelRotation() == 1) {
			mScale -= 0.1;
			if (mScale < 0.6) {
				mScale = 0.6;
			}
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mMouseClickX = arg0.getX();
		mMouseClickY = arg0.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mTempX = mTranslationX;
		mTempY = mTranslationY;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (arg0.getX() > getWidth() || arg0.getY() > getHeight()
				|| arg0.getX() < 0 || arg0.getY() < 0) {

		} else {
			mTranslationX = mTempX + DRAG_SPEED * (arg0.getX() - mMouseClickX);
			mTranslationY = mTempY + DRAG_SPEED * (arg0.getY() - mMouseClickY);
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	public void setOptimodDrawn(boolean drawn) {
		mOptimodDrawn = drawn;
	}

}