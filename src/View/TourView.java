package View;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
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
		mMapTraces = new HashMap<String, Integer>();
		mTour = tour;
	}

	/**
	 * 
	 */
	protected Tour mTour;

	/**
	 * Map counting traced paths between two given nodes
	 */
	protected Map<String, Integer> mMapTraces;

	@Override
	public void paint(Graphics g, double scale, int translationX, int translationY) {

		mMapTraces = new HashMap<String, Integer>();
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
				diff = 0;
				Node depNode = s.getDepartureNode();
				Node arrNode = s.getArrivalNode();
				String str ="";
				if(mMapTraces.containsKey(arrNode.getId()+";"+depNode.getId()))
				{
					str = arrNode.getId()+";"+depNode.getId();
					mMapTraces.put(str, mMapTraces.get(str)+1);
					diff = (int)Math.pow(-1, mMapTraces.get(str))*mMapTraces.get(str) + 1;
				}else if(mMapTraces.containsKey(depNode.getId()+";"+arrNode.getId()))
				{
					str = depNode.getId()+";"+arrNode.getId();
					mMapTraces.put(str, mMapTraces.get(str)+1);
				}else{
					str = depNode.getId()+";"+arrNode.getId();
					mMapTraces.put(depNode.getId()+";"+arrNode.getId(), 1);
				}

				Stroke dashed = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{8*mMapTraces.get(str)}, 0);
				g2D.setStroke(dashed);
				g2D.setColor(d.getTimeSlot().getColor());
				g2D.drawLine((int)(scale*depNode.getX())+translationX, (int)(scale*depNode.getY())+translationY, 
						(int)(scale*arrNode.getX())+translationX, (int)(scale*arrNode.getY())+translationY);
			}
		}
	}

	@Override
	public boolean onClick(MouseEvent E) {
		return false;
		// TODO Auto-generated method stub

	}

}