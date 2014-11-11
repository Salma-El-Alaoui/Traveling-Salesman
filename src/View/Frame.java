package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class Frame extends JFrame implements ActionListener, MouseListener {

	private final static int WIDTH = 1366;
	private final static int HEIGHT = 768;
	private final static int INFOS_WIDTH = 300;

	private final static String ACTION_LOAD_MAP = "ACTION_LOAD_MAP";
	private final static String ACTION_LOAD_DELIVERIES = "ACTION_LOAD_DELIVERIES";
	private final static String ACTION_EXPORT_ROADMAP = "ACTION_EXPORT_ROADMAP";

	/**
	 * 
	 */
	public Frame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(WIDTH,HEIGHT));
		this.setLayout(new BorderLayout());

		mMenuBar = new JMenuBar();

		JToolBar toolbar = new JToolBar();
		ImageIcon icon = new ImageIcon("img/load_plan.png");
		mLoadPlanButton = new JButton(icon);
		mLoadPlanButton.setActionCommand(ACTION_LOAD_MAP);
		mLoadPlanButton.setToolTipText("Charger plan");
		toolbar.add(mLoadPlanButton);

		icon = new ImageIcon("img/load_deliveries.png");
		mLoadDeliveriesButton = new JButton(icon);
		mLoadDeliveriesButton.setActionCommand(ACTION_LOAD_DELIVERIES);
		mLoadDeliveriesButton.setToolTipText("Charger demandes de livraisons");
		toolbar.add(mLoadDeliveriesButton);

		icon = new ImageIcon("img/export.png");
		mExportButton = new JButton(icon);
		mExportButton.setActionCommand(ACTION_EXPORT_ROADMAP);
		mExportButton.setToolTipText("Exporter feuilles de route");
		toolbar.add(mExportButton);

		this.add(toolbar, BorderLayout.NORTH);

		mLabelInfos = new JLabel();
		mLabelInfos.setText("Infos générales");
		mNodeInfos = new JLabel();
		mNodeInfos.setText("Infos du noeud sélectionné");

		mMenuEdition = new JMenu("Edition");

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
	protected JButton mExportButton;

	/**
	 * 
	 */
	protected JButton mLoadPlanButton;

	/**
	 * 
	 */
	protected JButton mLoadDeliveriesButton;

	/**
	 * 
	 */
	protected GraphPanel mPanelGraph;

	/**
	 * 
	 */
	protected String mLabelText;




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
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		for(NodeView nv : mPanelGraph.getListNodeView())
		{
			nv.onClick(arg0);
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}