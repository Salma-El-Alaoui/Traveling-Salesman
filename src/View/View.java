package View;

import java.awt.Event;
import java.awt.Graphics;



/**
 * 
 */
public interface View {

    /**
     * @param Graphics g
     */
    public void paint(Graphics g);

    /**
     * @param Event E
     */
    public void onClick(Event E);

}