package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import Model.Segment;


/**
 * 
 */
public class SegmentView implements View {
	
    /**
     * 
     */
    protected Segment mSegment;

    /**
     * 
     */
    public SegmentView(Segment seg) {
    	mSegment = seg;
    }

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		int diameter = 20;

		int x1 = mSegment.getDepartureNode().getX();
		int y1 = mSegment.getDepartureNode().getY();
		int x2 = mSegment.getArrivalNode().getX();
		int y2 = mSegment.getArrivalNode().getY();
		
		
		//Check in which zone of a trigonometric circle the arrival node is
		
		if(y2<y1 && x2<x1)//Up left
		{
			g.drawLine(x1-diameter, y1, x2, y2+diameter);
			g.drawLine(x1, y1-diameter, x2+diameter, y2);
		}else if(y2>y1 && x2<x1)//Bottom left
		{
			g.drawLine(x1-diameter, y1, x2, y2-diameter);
			g.drawLine(x1, y1+diameter, x2+diameter, y2);
		}else if(y2>y1 && x2>x1)//Bottom right
		{
			g.drawLine(x1, y1+diameter, x2-diameter, y2);
			g.drawLine(x1+diameter, y1, x2, y2-diameter);
		}else if(y2<y1 && x2>x1)//Up right
		{
			g.drawLine(x1+diameter, y1, x2, y2+diameter);
			g.drawLine(x1, y1-diameter, x2-diameter, y2);
		}
		
		//Limit points
		if(y1==y2)
		{
			g.drawLine(x1, y1-diameter, x2, y2-diameter);
			g.drawLine(x1, y1+diameter, x2, y2+diameter);
		}
		if(x1==x2)
		{
			g.drawLine(x1-diameter, y1, x2-diameter, y2);
			g.drawLine(x1+diameter, y1, x2+diameter, y2);
		}
		
	}

	@Override
	public void onClick(MouseEvent E) {
		// TODO Auto-generated method stub
		
	}

}