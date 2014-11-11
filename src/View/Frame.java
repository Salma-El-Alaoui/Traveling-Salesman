package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * 
 */
public class Frame extends JFrame {

	private final static int WIDTH = 1366;
	private final static int HEIGHT = 768;
	private final static int INFOS_WIDTH = 300;
	
	/**
     * 
     */
    public Frame() {
    	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(WIDTH,HEIGHT));
		this.setLayout(new BorderLayout());
		
		mMenuBar = new JMenuBar();
		
		JToolBar toolbar = new JToolBar();
		ImageIcon icon = new ImageIcon("img/upload.png");
		JButton loadButton = new JButton(icon);
		toolbar.add(loadButton);
		this.add(toolbar, BorderLayout.NORTH);

		mLabelInfos.setText("Infos générales");
		mNodeInfos.setText("Infos du noeud sélectionné");

		mMenuFile = new JMenu("Fichier");
		mMenuFile.add(new JMenuItem("Charger Plan"));
		mMenuFile.add(new JMenuItem("Charger Demande de livraison"));
		mMenuFile.add(new JMenuItem("Exporter feuille de route"));
		mMenuEdition = new JMenu("Edition");
		mMenuBar.add(mMenuFile);
		mMenuBar.add(mMenuEdition);

		mLabelInfos.setPreferredSize(new Dimension(INFOS_WIDTH,HEIGHT));

		JPanel panelInfos1 = new JPanel();
		panelInfos1.setBackground(Color.green); //TODO On met GraphPanel ici
		this.add(panelInfos1, BorderLayout.CENTER);
		


		JPanel panelInfos = new JPanel();  
		panelInfos.setLayout(new GridLayout(2,1));
		panelInfos.add(mLabelInfos);
		panelInfos.add(mNodeInfos);
		this.add(panelInfos, BorderLayout.EAST);
		
		setJMenuBar(mMenuBar);

		this.setVisible(true);
    }

    /**
     * 
     */
    protected JMenu mMenuFile;
    
    /**
     * 
     */
    protected JMenu mMenuEdition;
    
    /**
     * 
     */
    protected JMenuBar mMenuBar;
    
    /**
     * 
     */
    protected JToolBar toolbar;
    
    /**
     * 
     */
    protected JLabel mNodeInfos;
    
    /**
     * 
     */
    protected JLabel mLabelInfos;

    /**
     * 
     */
    protected GraphPanel mPanelGraph;

    /**
     * 
     */
    public List<NodeView> listNodeView;




    /**
     * @param boolean activated 
     */
    public void activateAddItem(boolean activated) {
        // TODO implement here
    }

    /**
     */
    public void clicBrowseDeliveries() {
        // TODO implement here
    }

    /**
     * @param String error
     */
    public void  displayError(String error) {
        // TODO implement here
    }

    /**
     */
    public void clickBrowseNetwork() {
        // TODO implement here
    }

    /**
     * @return
     */
    public boolean isAddSelected() {
        // TODO implement here
        return false;
    }

}