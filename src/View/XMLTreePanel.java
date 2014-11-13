package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.w3c.dom.Document;

import Model.Network;
import Model.XMLTreeModel;
import Model.XMLTreeNode;

public class XMLTreePanel extends JPanel implements Observer {

	private JTree tree;
	private XMLTreeModel model;
	private Network n;
	private final String TITLE = "Demande de livraison initiale";
	
	public XMLTreePanel() {
		
		
		setLayout(new BorderLayout());
		
		model = new XMLTreeModel();
		tree = new JTree();
		tree.setModel(model);
		tree.setShowsRootHandles(true);
		tree.setEditable(false);
		
		
		setPreferredSize(new Dimension(200,100));
		
		JScrollPane pane = new JScrollPane(tree);
		pane.setPreferredSize(new Dimension(200,400));

		add(pane, "West");
		
		final JTextField text = new JTextField(TITLE);
		text.setEditable(false);
		add(text, "North");
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				Object lpc = e.getPath().getLastPathComponent();
				if (lpc instanceof XMLTreeNode) {
					text.setText( ((XMLTreeNode)lpc).getText() );
				}
			}
		});
		
	}
	
	/* methods that delegate to the custom model */
	public void setDocument(Document document) {
		model.setDocument(document);
	}
	public Document getDocument() {
		return model.getDocument();
	}
	public void setNetwork(Network n) {
		this.n = n;
		n.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("Here");
		if (arg0 instanceof Network){
			Network n = (Network) arg0;
			if(n.getNodesList().size() != 0 && (arg1 != null)){
				System.out.println("There");
				Document doc  = (Document) arg1;
				System.out.println(doc);
				setDocument(doc);
			}
		}
		
	}
	
}
