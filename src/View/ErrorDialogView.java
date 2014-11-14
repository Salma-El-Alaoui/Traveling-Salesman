package View;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Error dialog
 */
public class ErrorDialogView {
	
	public void paint(Exception ex){
		try{
			JOptionPane.showMessageDialog(new JFrame(),
				    ex.getMessage().split(":")[1],
				    ex.getClass().getSimpleName(),
				    JOptionPane.ERROR_MESSAGE);
		} catch (ArrayIndexOutOfBoundsException eob){
			JOptionPane.showMessageDialog(new JFrame(),
				    ex.getMessage(),
				    ex.getClass().getSimpleName(),
				    JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	
}
