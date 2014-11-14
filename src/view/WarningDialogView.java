package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class WarningDialogView {

	public void paint(Exception ex) {
		try {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), ex
					.getClass().getSimpleName(), JOptionPane.WARNING_MESSAGE);
		} catch (ArrayIndexOutOfBoundsException eob) {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), ex
					.getClass().getSimpleName(), JOptionPane.WARNING_MESSAGE);
		}

	}
}
