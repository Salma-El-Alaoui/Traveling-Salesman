package view;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import model.Network;
import model.Segment;

/**
 * Class NetworkView
 */
public class NetworkView implements View {

	/**
	 * Network corresponding to the NetworkView
	 */
	private Network mNetwork;

	/**
	 * Liste of Segment included in the Network
	 */
	private List<SegmentView> listSegmentView;

	/**
	 * Constructor of NetworkView
	 * 
	 * @param network
	 *            Network for the View
	 * @param listSegment
	 *            List of Segment in Network
	 */
	public NetworkView(Network network, List<Segment> listSegment) {
		mNetwork = network;
		listSegmentView = new ArrayList<SegmentView>();
		for (Segment s : listSegment) {
			listSegmentView.add(new SegmentView(s));
		}

	}

	@Override
	public void paint(Graphics g, double scale, int translationX,
			int translationY) {
		for (int i = 0; i < listSegmentView.size(); i++) {
			listSegmentView.get(i).paint(g, scale, translationX, translationY);
		}
	}

	@Override
	public boolean onClick(MouseEvent E) {
		return false;
	}

}