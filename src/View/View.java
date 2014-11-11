package View;

import java.awt.Graphics;
import java.awt.event.MouseEvent;



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
    public void onClick(MouseEvent E);

}