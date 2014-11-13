package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Delivery;
import Model.Node;
import Model.Path;
import Model.Segment;
import Model.Tour;

/**
 * 
 */
public class TourView implements View {

	/**
	 * 
	 */
	public TourView(Tour tour) {
		mMapTraces = new HashMap<Segment, Integer>();
		mTour = tour;
	}

	/**
	 * 
	 */
	protected Tour mTour;

	/**
	 * Map counting traced paths between two given nodes
	 */
	protected Map<Segment, Integer> mMapTraces;

	@Override
	public void paint(Graphics g, double scale, int translationX, int translationY) {

		mMapTraces = new HashMap<Segment, Integer>();
		Graphics2D g2D = (Graphics2D) g;
		List<Path> listPath = mTour.getPathList();
		Delivery d = new Delivery();
		Path p = new Path();
		int diff = 0;

		for(int i=0; i<listPath.size();i++)
		{
			if(i<listPath.size()-1)
			{
				d = mTour.getDeliveryList().get(i);
			}
			p = listPath.get(i);

			List<Segment> listSegment = p.getSegmentList();
			for(Segment s : listSegment)
			{
				//TODO changer mettre Map<Integer, Map<Integer,Integer>>
				Node depNode = s.getDepartureNode();
				Node arrNode = s.getArrivalNode();
				if(mMapTraces.containsKey(s))
				{
					mMapTraces.put(s, mMapTraces.get(s)+1);
					diff = (int)Math.pow(-1, mMapTraces.get(s))*2*mMapTraces.get(s);
				}else
				{
					mMapTraces.put(s, 1);
				}

				g2D.setStroke(new BasicStroke(3));
				g2D.setColor(d.getTimeSlot().getColor());
				g2D.drawLine((int)(scale*depNode.getX())+translationX+diff, (int)(scale*depNode.getY())+translationY+diff, 
						(int)(scale*arrNode.getX())+translationX+diff, (int)(scale*arrNode.getY())+translationY+diff);
			}
		}
	}

	@Override
	public boolean onClick(MouseEvent E) {
		return false;
		// TODO Auto-generated method stub

	}

}