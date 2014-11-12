package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int x1 = mSegment.getDepartureNode().getX();
		int y1 = mSegment.getDepartureNode().getY();
		int x2 = mSegment.getArrivalNode().getX();
		int y2 = mSegment.getArrivalNode().getY();
		
		g2D.setStroke(new BasicStroke(NodeView.DIAMETER-3));
		g2D.setColor(Color.black);
		g2D.drawLine(x1, y1, x2, y2);
		
		g2D.setStroke(new BasicStroke(NodeView.DIAMETER-5));
		g2D.setColor(Color.lightGray);
		g2D.drawLine(x1, y1, x2, y2);
		
	}

	@Override
	public void onClick(MouseEvent E) {
		// TODO Auto-generated method stub
		
	}

}