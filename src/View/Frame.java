package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class Frame extends JFrame implements ActionListener {

	private final static int WIDTH = 1366;
	private final static int HEIGHT = 768;
	private final static int INFOS_WIDTH = 300;
	
	private final static String ACTION_LOAD_MAP = "ACTION_LOAD_MAP";
	private final static String ACTION_LOAD_DELIVERIES = "ACTION_LOAD_DELIVERIES";
	private final static String ACTION_EXPORT_ROADMAP = "ACTION_EXPORT_ROADMAP";
	private final static String ACTION_ADD_DELIVERY = "ACTION_ADD_DELIVERY";
	
	/**
     * 
     */
    public Frame() {
    	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(WIDTH,HEIGHT));
		this.setLayout(new BorderLayout());
		
		mMenuBar = new JMenuBar();
		mLabelInfos=new JLabel();
		mNodeInfos=new JLabel();
		mPanelGraph= new GraphPanel();
		
		JToolBar toolbar = new JToolBar();
		ImageIcon icon = new ImageIcon("img/load_plan.png");
		JButton loadPlanButton = new JButton(icon);
		loadPlanButton.setActionCommand(ACTION_LOAD_MAP);
		toolbar.add(loadPlanButton);
		
		icon = new ImageIcon("img/load_deliveries.png");
		JButton loadDeliveriesButton = new JButton(icon);
		loadPlanButton.setActionCommand(ACTION_LOAD_DELIVERIES);
		toolbar.add(loadDeliveriesButton);
		
		icon = new ImageIcon("img/export.png");
		JButton exportButton = new JButton(icon);
		loadPlanButton.setActionCommand(ACTION_EXPORT_ROADMAP);
		toolbar.add(exportButton);
		
		this.add(toolbar, BorderLayout.NORTH);

		
		mLabelInfos.setText("Infos générales");
		mNodeInfos.setText("Infos du noeud sélectionné");

		mMenuEdition = new JMenu("Edition");
		mAddDelivery=new JMenuItem("Ajouter une livraison");
		mAddDelivery.setEnabled(false);
		mAddDelivery.setActionCommand(ACTION_ADD_DELIVERY);
		mAddDelivery.addActionListener(this);
		mMenuEdition.add(mAddDelivery);
		
		JMenuItem removeDelivery=new JMenuItem("Supprimer une livraison");
		removeDelivery.setEnabled(false);
		removeDelivery.addActionListener(this);
		mMenuEdition.add(removeDelivery);
		
		mMenuFile = new JMenu("Fichier");
		JMenuItem loadMap = new JMenuItem("Charger Plan");
		loadMap.setActionCommand(ACTION_LOAD_MAP);
		loadMap.addActionListener(this);
		mMenuFile.add(loadMap);
		
		JMenuItem loadDeliveries = new JMenuItem("Charger Demande de livraison");
		loadDeliveries.setActionCommand(ACTION_LOAD_DELIVERIES);
		loadDeliveries.addActionListener(this);
		mMenuFile.add(loadDeliveries);
		
		JMenuItem export = new JMenuItem("Exporter feuille de route");
		export.setActionCommand(ACTION_EXPORT_ROADMAP);
		export.addActionListener(this);
		mMenuFile.add(export);
		
		mMenuBar.add(mMenuFile);
		
		mMenuBar.add(mMenuEdition);

		mLabelInfos.setPreferredSize(new Dimension(INFOS_WIDTH,HEIGHT));

		this.add(mPanelGraph, BorderLayout.CENTER);
	

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
    
    protected JMenuItem mAddDelivery;
    
    protected boolean mAddDeliveryClicked;
    
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
    * Enable or disable click on the add delivery menu item
    * @param activated true to enable, false to disable
    */
    public void activateAddItem(boolean activated) {
        mAddDelivery.setEnabled(activated);
        mAddDeliveryClicked=false;
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
     * Return true if the add delivery menu item has been selected
     * @return true if the add delivery menu item has been selected
     */
    public boolean isAddSelected() {
        return mAddDeliveryClicked;
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand())
		{
		case(ACTION_LOAD_MAP) :
			break;
		case(ACTION_LOAD_DELIVERIES):
			break;
		case(ACTION_EXPORT_ROADMAP):
			break;
		case(ACTION_ADD_DELIVERY):
			mAddDeliveryClicked=true;
			break;
		}
	}
	
	public static void main(String[] args)
	{
		Frame frame=new Frame();
		frame.setVisible(true);
	}
	

}