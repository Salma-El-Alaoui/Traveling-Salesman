package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import Model.Network;
import Model.Node;

/**
 * 
 */
public class GraphPanel extends JPanel implements MouseWheelListener,Observer {
	
	public static double scale;

    /**
     * 
     */
    public GraphPanel() {
    	this.setPreferredSize(new Dimension(600,600));
    	scale = 600/800;
    	this.addMouseWheelListener(this);
    	
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
			mNetworkView.paint(g);			
		}

		if(mTourView != null){
			mTourView.paint(g);			
		}
		
		if(mListNodeView != null){
			for(NodeView nv : mListNodeView)
			{
				nv.paint(g);
			}			
		}
	}

	public List<NodeView> getListNodeView() {
		return mListNodeView;
	}
	
	public void setNetwork(Network n){
		if(n != null){
			n.addObserver(this);
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
		if(arg0.getWheelRotation() == 1)
		{
			scale += 0.1;
		}else if(arg0.getWheelRotation() == -1)
		{
			scale -= 0.1;
			if(scale<0.1)
			{
				scale = 0.1;
			}
		}
		repaint();
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
		
	}
    
}