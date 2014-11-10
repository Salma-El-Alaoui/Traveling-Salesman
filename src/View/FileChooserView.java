package View;


import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * 
 */
public class FileChooserView {
	private JFileChooser jFileChooserXML;

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

	public File paint() {
		char mode = 'o';
		jFileChooserXML = new JFileChooser();
		XMLFileFilter filter = new XMLFileFilter();
		jFileChooserXML.setFileFilter(filter);
		jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal;
		if (mode == 'o')
			returnVal = jFileChooserXML.showOpenDialog(null);
		else
			returnVal = jFileChooserXML.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION)
			return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
		return null;
	}

}