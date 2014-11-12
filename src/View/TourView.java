package View;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import Model.Delivery;
import Model.Node;
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
	public void paint(Graphics g) {
		for(int i=0;i<mTour.getmDeliveryList().size();i++)
		{
			Delivery dFirst = mTour.getmDeliveryList().get(i);
			g.setColor(dFirst.getTimeSlot().getColor());
			
			Delivery dSecond;
			if(i == mTour.getmDeliveryList().size()-1)
			{
				dSecond =  mTour.getmDeliveryList().get(0);
			}else
			{
				dSecond =  mTour.getmDeliveryList().get(i+1);
			}
			Couple c = new Couple(dFirst.getNode(), dSecond.getNode()); //TODO A verifier, pas sûr
			if(mMapTraces.containsKey(c))
			{
				mMapTraces.put(c, mMapTraces.get(c)+1);
				int diff = (int)Math.pow(-1, mMapTraces.get(c))*mMapTraces.get(c);
				
				g.drawLine(dFirst.getNode().getX()+diff, dFirst.getNode().getY()+diff, dSecond.getNode().getY()+diff, dSecond.getNode().getY()+diff);
			}else
			{
				mMapTraces.put(c, 1);
				g.drawLine(dFirst.getNode().getX(), dFirst.getNode().getY(), dSecond.getNode().getY(), dSecond.getNode().getY());
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
		}

	}

}