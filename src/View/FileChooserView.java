package View;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * 
 */
public class FileChooserView {
	private JFileChooser jFileChooserXML;
	private JFileChooser jSaveFileChooser;

	private class XMLFileFilter extends FileFilter {
		private final String suffix = "xml";

		private final String description = "XML files(*.xml)";

		private boolean isXML(String suffix) {
			return suffix.equals(this.suffix);
		}

		public boolean accept(File f) {
			if (f.isDirectory())
				return true;
			String suffix = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix != null && isXML(suffix);
		}

		public String getDescription() {
			return description;
		}

	}

	/**
	 * 
	 */
	public FileChooserView() {
	}

	public File paintOpen() {
		jFileChooserXML = new JFileChooser();

		int returnVal;

		XMLFileFilter filter = new XMLFileFilter();
		jFileChooserXML.setFileFilter(filter);
		returnVal = jFileChooserXML.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());

		}

		return null;
	}

	public FileWriter paintSave() {
		jSaveFileChooser = new JFileChooser();

		int userSelection = jSaveFileChooser.showSaveDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = jSaveFileChooser.getSelectedFile();

			String fileToSaveString = fileToSave.getAbsolutePath();
			int fileToSaveNameLength = fileToSaveString.length();

			// remove .txt that the user wrote
			if (fileToSaveString.substring(fileToSaveNameLength - 4,
					fileToSaveNameLength).equals(".txt")) {
				String tmp = fileToSaveString.substring(0,
						fileToSaveNameLength - 4);
				fileToSaveString = tmp;
			}
			try {
				FileWriter fw = new FileWriter(fileToSaveString + ".txt");
				return fw;
			} catch (IOException e) {
				return null;
			}
		}
		return null;
	}
}