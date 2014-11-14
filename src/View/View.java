package View;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * Class View
 */
public interface View {

	/**
	 * @param Graphics
	 *            g
	 */
	public void paint(Graphics g, double scale, int translationX,
			int translationY);

	/**
	 * @param Event
	 *            E
	 */
	public boolean onClick(MouseEvent E);

}