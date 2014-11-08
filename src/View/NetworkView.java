package View;

import java.awt.Event;
import java.awt.Graphics;
import java.util.*;

import Model.Network;

/**
 * 
 */
public class NetworkView implements View {

    /**
     * 
     */
    public NetworkView() {
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
	public void onClick(Event E) {
		// TODO Auto-generated method stub
		
	}

}