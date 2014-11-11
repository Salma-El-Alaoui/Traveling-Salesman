package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

/**
 * 
 */
public class GraphPanel extends JPanel {

    /**
     * 
     */
    public GraphPanel() {
    	this.setPreferredSize(new Dimension(500,500)); //TODO A voir avec Zied
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
    
    



}