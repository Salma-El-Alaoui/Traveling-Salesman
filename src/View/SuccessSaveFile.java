package View;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SuccessSaveFile {

	public void paint(Exception ex) {
		try {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(),
					"Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
		} catch (ArrayIndexOutOfBoundsException eob) {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(),
					"Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
		}

	}
}
