package View;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import Model.Network;
import Model.Segment;

/**
 * 
 */
public class NetworkView implements View {

    /**
     * 
     */
    public NetworkView(Network network, List<Segment> listSegment) {
    	mNetwork = network;
    	listSegmentView = new ArrayList<SegmentView>();
    	for(Segment s : listSegment)
    	{
    		listSegmentView.add(new SegmentView(s));
    	}
    	
    }

    /**
     * 
     */
    protected Network mNetwork;

    /**
     * 
     */
    public List<SegmentView> listSegmentView;

	@Override
	public void paint(Graphics g, double scale, int translationX, int translationY) {
		for(int i=0; i<listSegmentView.size();i++)
		{
			listSegmentView.get(i).paint(g, scale, translationX, translationY);
		}
	}

	@Override
	public boolean onClick(MouseEvent E) {
		return false;		
	}

}