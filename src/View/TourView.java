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
		mMapTraces = new HashMap<Couple, Integer>();
		mTour = tour;
	}

	/**
	 * 
	 */
	protected Tour mTour;

	/**
	 * Map counting traced paths between two given nodes
	 */
	protected Map<Couple, Integer> mMapTraces;

	@Override
	public void paint(Graphics g, double scale, int translationX, int translationY) {

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
				Node depNode = s.getDepartureNode();
				Node arrNode = s.getArrivalNode();
				Couple c = new Couple(depNode, arrNode);
				if(mMapTraces.containsKey(c))
				{
					mMapTraces.put(c, mMapTraces.get(c)+1);
					diff = (int)Math.pow(-1, mMapTraces.get(c))*mMapTraces.get(c);
				}else
				{
					mMapTraces.put(c, 1);
				}

				g2D.setStroke(new BasicStroke(3));
				g2D.setColor(d.getTimeSlot().getColor());
				g2D.drawLine((int)(scale*depNode.getX())+translationX+diff, (int)(scale*depNode.getY())+translationY+diff, 
						(int)(scale*arrNode.getX())+translationX+diff, (int)(scale*arrNode.getY())+translationY+diff);
			}
		}
	}

	@Override
	public void onClick(MouseEvent E) {
		// TODO Auto-generated method stub

	}

	/**
	 * Class used to count number of paths already traced between two nodes
	 */
	public class Couple
	{
		/**
		 * 
		 */
		Node departureNode;

		/**
		 * 
		 */
		Node arrivalNode;

		/**
		 * 
		 */
		public Couple(Node n1, Node n2)
		{
			departureNode = n1;
			arrivalNode = n2;
			int nbTraces = 0;
		}

	}

}