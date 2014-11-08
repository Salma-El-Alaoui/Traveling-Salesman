package View;

import java.awt.Event;
import java.awt.Graphics;

import Model.Delivery;
import Model.Tour;

/**
 * 
 */
public class TourView implements View {

    /**
     * 
     */
    public TourView() {
    }

    /**
     * 
     */
    protected Tour mTour;

    /**
     * @return Tour associated with TourView
     */
    public Tour getTour() {
        // TODO si null, etc...
        return mTour; 
    }

	@Override
	public void paint(Graphics g) {
		for(int i=0;i<mTour.getmDeliveryList().size();i++)
		{
			Delivery dFirst = mTour.getmDeliveryList().get(i);
			Delivery dSecond;
			if(i == mTour.getmDeliveryList().size()-1)
			{
				dSecond =  mTour.getmDeliveryList().get(0);
			}else
			{
				dSecond =  mTour.getmDeliveryList().get(i+1);
			}
			g.setColor(dFirst.getTimeSlot().getColor());
			g.drawLine(dFirst.getNode().getX(), dFirst.getNode().getY(), dSecond.getNode().getY(), dSecond.getNode().getY());
		}

	}

	@Override
	public void onClick(Event E) {
		// TODO Auto-generated method stub
		
	}

}