package View;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
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
	public void paint(Graphics g) {
		for(int i=0; i<listSegmentView.size();i++)
		{
			listSegmentView.get(i).paint(g);
		}
	}

	@Override
	public void onClick(MouseEvent E) {
		// TODO Auto-generated method stub
		
	}

}