package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import Model.Network;
import Model.Node;

/**
 * 
 */
public class GraphPanel extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener {

	/**
	 * 
	 */
	private static final int DRAG_SPEED= 1;
	
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
	protected int mTempX;
	
	/**
	 * 
	 */
	protected int mTempY;
	

	/**
	 * 
	 */
	protected int mMouseClickY;

	/**
	 * 
	 */
	protected int mMouseClickX;

	/**
	 * 
	 */
	public GraphPanel() {
		this.setPreferredSize(new Dimension(600,600));
		mScale = 600.0/800;
		mTranslationX = 0;
		mTranslationY = 0;
		this.addMouseWheelListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/**
	 * 
	 */
	protected List<NodeView> mListNodeView;

	/**
	 * 
	 */
	protected NetworkView mNetworkView;

	/**
	 * 
	 */
	protected TourView mTourView;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(mNetworkView != null){
			mNetworkView.paint(g, mScale, mTranslationX, mTranslationY);			
		}

		if(mTourView != null){
			mTourView.paint(g, mScale, mTranslationX, mTranslationY);			
		}

		if(mListNodeView != null){
			for(NodeView nv : mListNodeView)
			{
				nv.paint(g, mScale, mTranslationX, mTranslationY);
			}			
		}
	}

	public List<NodeView> getListNodeView() {
		return mListNodeView;
	}

	public void setNetwork(Network n){
		if(n != null){
			mNetworkView = new NetworkView(n, n.getSegmentList());
			mListNodeView = new ArrayList<NodeView>();
			Map<Integer,Node> mapNode = n.getNodesList();
			for(int i=0;i<mapNode.size();i++)
			{
				mListNodeView.add(new NodeView(mapNode.get(i)));
			}			
			if(n.getDeliveryRequest() != null && n.getDeliveryRequest().getTour() != null){
				mTourView = new TourView(n.getDeliveryRequest().getTour());	    		
			}
		} else {
			// TODO
		}
		repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		if(arg0.getWheelRotation() == -1)
		{
			mScale += 0.1;
			if(mScale>4)
			{
				mScale = 4;
			}
		}else if(arg0.getWheelRotation() == 1)
		{
			mScale -= 0.1;
			if(mScale<0.6)
			{
				mScale = 0.6;
			}
		}
		repaint();

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

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

		if(arg0.getX() > getWidth() || arg0.getY()>getHeight() || arg0.getX() < 0 || arg0.getY() < 0)
		{

		}else{
			mTranslationX = mTempX + DRAG_SPEED*(arg0.getX() - mMouseClickX );
			mTranslationY = mTempY + DRAG_SPEED*(arg0.getY() - mMouseClickY );
		}

		repaint();

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

}