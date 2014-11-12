package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import Model.Network;
import Model.Node;

/**
 * 
 */
public class GraphPanel extends JPanel {

    /**
     * 
     */
    public GraphPanel() {
    	this.setPreferredSize(new Dimension(800,800));
    	
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
    
}