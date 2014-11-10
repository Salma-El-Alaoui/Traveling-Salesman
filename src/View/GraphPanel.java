package View;

import java.awt.Graphics;
import java.util.*;

import javax.swing.JPanel;

/**
 * 
 */
public class GraphPanel extends JPanel {

    /**
     * 
     */
    public GraphPanel() {
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