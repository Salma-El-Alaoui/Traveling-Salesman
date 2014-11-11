package View;

import java.awt.Dimension;
import java.awt.Graphics;
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
    public GraphPanel(Network n) {
    	this.setPreferredSize(new Dimension(800,800));
    	mNetworkView = new NetworkView(n, n.getSegmentList());
    	mTourView = new TourView(n.getDeliveryRequest().getTour());
    	
    	Map<Integer,Node> mapNode = n.getNodesList();
    	for(int i=0;i<mapNode.size();i++)
    	{
    		listNodeView.add(new NodeView(mapNode.get(i)));
    	}
    	
    }

    /**
     * 
     */
    protected List<NodeView> listNodeView;
    
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
		mNetworkView.paint(g);
		mTourView.paint(g);
		
		for(NodeView nv : listNodeView)
		{
			nv.paint(g);
		}
	}

	public List<NodeView> getListNodeView() {
		return listNodeView;
	}
	
	
    
    



}