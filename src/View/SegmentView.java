package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import Model.Segment;

/**
 * Class SegmentView
 */
public class SegmentView implements View {

	/**
	 * Segment corresponding to the SegmentView
	 */
	protected Segment mSegment;

	/**
	 * Constructor of SegmentView
	 * 
	 * @param seg
	 *            Segment for the View
	 */
	public SegmentView(Segment seg) {
		mSegment = seg;
	}

	@Override
	public void paint(Graphics g, double scale, int translationX,
			int translationY) {

		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		int x1 = mSegment.getDepartureNode().getX();
		int y1 = mSegment.getDepartureNode().getY();
		int x2 = mSegment.getArrivalNode().getX();
		int y2 = mSegment.getArrivalNode().getY();

		g2D.setStroke(new BasicStroke((int) (scale * (NodeView.DIAMETER - 5))));
		g2D.setColor(Color.BLACK);
		g2D.drawLine((int) (scale * x1) + translationX, (int) (scale * y1)
				+ translationY, (int) (scale * x2) + translationX,
				(int) (scale * y2) + translationY);

		g2D.setStroke(new BasicStroke((int) (scale * (NodeView.DIAMETER - 6))));
		g2D.setColor(Color.WHITE);
		g2D.drawLine((int) (scale * x1) + translationX, (int) (scale * y1)
				+ translationY, (int) (scale * x2) + translationX,
				(int) (scale * y2) + translationY);
	}

	@Override
	public boolean onClick(MouseEvent E) {
		return false;

	}

}