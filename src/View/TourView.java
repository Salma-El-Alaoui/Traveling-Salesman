package View;

import java.awt.BasicStroke;
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
 * Class TourView
 */
public class TourView implements View {


	/**
	*	Tour corresponding to the View
	 */
	protected Tour mTour;

	/**
	 * Map counting traced paths between two given nodes
	 */
	protected Map<Segment, Integer> mMapTraces;

	/**
	 * Constructor of TourView
	 * @param tour Tour for the View
	 */

	public TourView(Tour tour) {
		mMapTraces = new HashMap<Segment, Integer>();
		mTour = tour;
	}


	@Override
	public void paint(Graphics g, double scale, int translationX,
			int translationY) {

		mMapTraces = new HashMap<Segment, Integer>();
		Graphics2D g2D = (Graphics2D) g;
		List<Path> listPath = mTour.getPathList();
		Path p = new Path();
		int diff = 0;

		for (int i = 0; i < listPath.size(); i++) {
			if (i < listPath.size() - 1) {
				Delivery d = mTour.getDeliveryList().get(i);
				g2D.setColor(d.getTimeSlot().getColor());
			}
			p = listPath.get(i);
			List<Segment> listSegment = p.getSegmentList();

			for (Segment s : listSegment) {
				diff = 0;
				Node depNode = s.getDepartureNode();
				Node arrNode = s.getArrivalNode();

				if (mMapTraces.get(s) != null) {
					diff = mMapTraces.get(s);
					diff +=2 ;
					mMapTraces.put(s, diff);
				} else {
					diff = 1;
					mMapTraces.put(s, diff);
				}

				double dX = arrNode.getX() - depNode.getX();
				double dY = arrNode.getY() - depNode.getY();

				double norm = Math.sqrt(dX*dX + dY*dY);

				g2D.setStroke(new BasicStroke((float) (scale)));
				g2D.translate(dY/norm * scale * diff, - dX/norm * scale * diff);
				g2D.drawLine((int) (scale * depNode.getX()) + translationX,
						(int) (scale * depNode.getY()) + translationY,
						(int) (scale * arrNode.getX()) + translationX,
						(int) (scale * arrNode.getY()) + translationY);
				g2D.translate(- dY/norm * scale * diff, dX/norm * scale * diff);

			}
		}
	}

	@Override
	public boolean onClick(MouseEvent E) {
		return false;
		// TODO Auto-generated method stub

	}

}