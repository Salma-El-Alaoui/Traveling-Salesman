package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

import Controller.Controller;
import Model.Network;

/**
 * 
 */
public class Frame extends JFrame implements ActionListener, MouseListener {

	private final static int WIDTH = 1000;
	private final static int HEIGHT = 700;
	private final static double INFOS_WIDTH = 0.2;

	private final static String ACTION_LOAD_MAP = "ACTION_LOAD_MAP";
	private final static String ACTION_LOAD_DELIVERIES = "ACTION_LOAD_DELIVERIES";
	private final static String ACTION_CALCULATE_TOUR = "ACTION_CALCULATE_TOUR";
	private final static String ACTION_EXPORT_ROADMAP = "ACTION_EXPORT_ROADMAP";
	private final static String ACTION_EXIT = "ACTION_EXIT";
	private final static String ACTION_ADD_DELIVERY = "ACTION_ADD_DELIVERY";

	/**
	 * 
	 */
	public Frame(Controller controller) {		
		mController = controller;
		mPanelGraph = new GraphPanel();

		setTitle("Traveling Salesman");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(WIDTH,HEIGHT));
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		mPanelGraph.addMouseListener(this);

		mMenuBar = new JMenuBar();

		mLabelInfos=new JLabel();
		mNodeInfos=new JLabel();

		JToolBar toolbar = new JToolBar();
		ImageIcon icon = new ImageIcon("img/load_plan.png");
		mLoadPlanButton = new JButton(icon);
		mLoadPlanButton.setActionCommand(ACTION_LOAD_MAP);
		mLoadPlanButton.setToolTipText("Charger plan");
		mLoadPlanButton.addActionListener(this);
		toolbar.add(mLoadPlanButton);

		icon = new ImageIcon("img/load_deliveries.png");
		mLoadDeliveriesButton = new JButton(icon);
		mLoadDeliveriesButton.setActionCommand(ACTION_LOAD_DELIVERIES);
		mLoadDeliveriesButton.setToolTipText("Charger demandes de livraisons");
		mLoadDeliveriesButton.addActionListener(this);
		toolbar.add(mLoadDeliveriesButton);

		icon = new ImageIcon("img/chart_line_edit.png");
		mCalculateTourButton = new JButton(icon);
		mCalculateTourButton.setActionCommand(ACTION_CALCULATE_TOUR);
		mCalculateTourButton.setToolTipText("Calculer la tournée");
		mCalculateTourButton.addActionListener(this);
		toolbar.add(mCalculateTourButton);


		icon = new ImageIcon("img/export.png");
		mExportButton = new JButton(icon);
		mExportButton.setActionCommand(ACTION_EXPORT_ROADMAP);
		mExportButton.setToolTipText("Exporter feuilles de route");
		mExportButton.addActionListener(this);
		toolbar.add(mExportButton);

		this.add(toolbar, BorderLayout.NORTH);


		mLabelInfos.setText("Infos générales");
		mNodeInfos.setText("<html>Noeud sélectionné : <br>Aucun");

		mMenuEdition = new JMenu("Edition");

		mAddDelivery=new JMenuItem("Ajouter une livraison");
		mAddDelivery.setActionCommand(ACTION_ADD_DELIVERY);
		mAddDelivery.addActionListener(this);
		mMenuEdition.add(mAddDelivery);

		mRemoveDelivery=new JMenuItem("Supprimer une livraison");
		mRemoveDelivery.addActionListener(this);
		mMenuEdition.add(mRemoveDelivery);

		mMenuFile = new JMenu("Fichier");
		mLoadMap = new JMenuItem("Charger Plan");
		mLoadMap.setActionCommand(ACTION_LOAD_MAP);
		mLoadMap.addActionListener(this);
		mMenuFile.add(mLoadMap);

		mloadDeliveries = new JMenuItem("Charger Demande de livraison");
		mloadDeliveries.setActionCommand(ACTION_LOAD_DELIVERIES);
		mloadDeliveries.addActionListener(this);
		mMenuFile.add(mloadDeliveries);

		mCalculateTour = new JMenuItem("Calculer la tournée");
		mCalculateTour.setActionCommand(ACTION_CALCULATE_TOUR);
		mCalculateTour.addActionListener(this);
		mMenuFile.add(mCalculateTour);

		mExport = new JMenuItem("Exporter feuille de route");
		mExport.setActionCommand(ACTION_EXPORT_ROADMAP);
		mExport.addActionListener(this);
		mMenuFile.add(mExport);
		
		mExit = new JMenuItem("Quitter");
		mExit.setActionCommand(ACTION_EXIT);
		mExit.addActionListener(this);
		mMenuFile.add(mExit);

		mMenuBar.add(mMenuFile);

		mMenuBar.add(mMenuEdition);

		mLabelInfos.setPreferredSize(new Dimension((int)(INFOS_WIDTH*WIDTH),HEIGHT));

		this.add(mPanelGraph, BorderLayout.WEST);


		JPanel panelInfos = new JPanel();  
		panelInfos.setLayout(new GridLayout(2,1));
		panelInfos.add(mLabelInfos);
		panelInfos.add(mNodeInfos);
		this.add(panelInfos, BorderLayout.EAST);

		setJMenuBar(mMenuBar);
		changeState(Controller.State.NEW);
		this.setVisible(true);

	}

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
	protected JButton mCalculateTourButton;



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
	protected JMenuItem mLoadMap;

	protected JMenuItem mAddDelivery;

	protected JMenuItem mRemoveDelivery;

	protected JMenuItem mloadDeliveries;

	protected JMenuItem mCalculateTour;

	protected JMenuItem mExport;
	
	protected JMenuItem mExit;

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
	 *
	 */
	private Controller mController;


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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand())
		{
		case(ACTION_LOAD_MAP) :
			mController.loadNetworkXML();
			break;
		case(ACTION_LOAD_DELIVERIES):
			mController.loadDeliveriesXML();
			break;
		case(ACTION_CALCULATE_TOUR):
			mController.calculateTour();
		case(ACTION_EXPORT_ROADMAP):
			break;
		case(ACTION_ADD_DELIVERY):
			mController.addDeliveryClicked();	
			break;
		case(ACTION_EXIT):
			System.exit(0);
		}
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(mPanelGraph.getListNodeView() != null)
		{
			for(NodeView nv : mPanelGraph.getListNodeView())
			{
				if(nv.onClick(arg0))
				{
					String nodeInfos = "<html>Noeud sélectionné : <br>Adresse : "+nv.getNode().getId();
					if(nv.getNode().isWarehouse())
					{
						nodeInfos +="<br>Entrepôt";
					}else if(nv.getNode().hasDelivery())
					{
						NumberFormat nf = new DecimalFormat("##00");
						int heureDep = nv.getNode().getDelivery().getDepartureHour();
						int minDep = heureDep;
						int secDep = heureDep;
						int heureArr = nv.getNode().getDelivery().getArrivalHour();
						int minArr = heureArr;
						int secArr = heureArr;

						nodeInfos += "<br>Livraison : Oui <br>Intervalle horaire : "+nf.format(heureArr/3600)+"h"+nf.format(minArr/(3600*60))+":"+nf.format(secArr/(3600*60*60))
									+" à "+nf.format(heureDep/3600)+"h"+nf.format(minDep/(3600*60))+":"+nf.format(secDep/(3600*60*60))+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}else
					{
						nodeInfos += "<br>Livraison : Non</html>";
					}
					mNodeInfos.setText(nodeInfos);
				}
			}
		}

		repaint();

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	/**
	 * 
	 */
	public void setNetwork(Network n){
		mPanelGraph.setNetwork(n);
	}


	/**
	 * Update the frame depending on the state
	 * @param state current state
	 */
	public void changeState(Controller.State state)
	{
		switch (state){
		case NEW:
			mLoadPlanButton.setEnabled(true);
			mLoadMap.setEnabled(true);
			mLoadDeliveriesButton.setEnabled(false);
			mloadDeliveries.setEnabled(false);
			mCalculateTour.setEnabled(false);
			mCalculateTourButton.setEnabled(false);
			mExportButton.setEnabled(false);
			mExport.setEnabled(false);
			mAddDelivery.setEnabled(false);
			mRemoveDelivery.setEnabled(false);
			mAddDelivery.setText("Ajouter une livraison");
			break;
		case NETWORK_LOADED:
			mLoadPlanButton.setEnabled(true);
			mLoadMap.setEnabled(true);
			mLoadDeliveriesButton.setEnabled(true);
			mloadDeliveries.setEnabled(true);
			mExportButton.setEnabled(false);
			mExport.setEnabled(false);
			mAddDelivery.setEnabled(false);
			mRemoveDelivery.setEnabled(false);
			mAddDelivery.setText("Ajouter une livraison");
			break;
		case DELIVERY_REQUEST_LOADED:
			mLoadPlanButton.setEnabled(true);
			mLoadMap.setEnabled(true);
			mLoadDeliveriesButton.setEnabled(true);
			mloadDeliveries.setEnabled(true);
			mCalculateTour.setEnabled(true);
			mCalculateTourButton.setEnabled(true);
			mExportButton.setEnabled(false);
			mExport.setEnabled(false);
			mAddDelivery.setEnabled(false);
			mRemoveDelivery.setEnabled(false);
			mAddDelivery.setText("Ajouter une livraison");
			break;
		case TOUR_CALCULATED:
			mLoadPlanButton.setEnabled(true);
			mLoadMap.setEnabled(true);
			mLoadDeliveriesButton.setEnabled(true);
			mloadDeliveries.setEnabled(true);
			mCalculateTour.setEnabled(true);
			mCalculateTourButton.setEnabled(true);
			mExportButton.setEnabled(true);
			mExport.setEnabled(true);
			mAddDelivery.setEnabled(false);
			mRemoveDelivery.setEnabled(false);
			mAddDelivery.setText("Ajouter une livraison");
			break;
		case TOUR_NODE_SELECTED:
			mLoadPlanButton.setEnabled(true);
			mLoadMap.setEnabled(true);
			mLoadDeliveriesButton.setEnabled(true);
			mloadDeliveries.setEnabled(true);
			mExportButton.setEnabled(true);
			mExport.setEnabled(true);
			mAddDelivery.setEnabled(false);
			mRemoveDelivery.setEnabled(true);
			mAddDelivery.setText("Ajouter une livraison");
			break;
		case OTHER_NODE_SELECTED:
			mLoadPlanButton.setEnabled(true);
			mLoadMap.setEnabled(true);
			mLoadDeliveriesButton.setEnabled(true);
			mloadDeliveries.setEnabled(true);
			mExportButton.setEnabled(true);
			mExport.setEnabled(true);
			mAddDelivery.setEnabled(true);
			mRemoveDelivery.setEnabled(false);
			mAddDelivery.setText("Ajouter une livraison");
			break;
		case ADDING_DELIVERY:
			mLoadPlanButton.setEnabled(false);
			mLoadMap.setEnabled(false);
			mLoadDeliveriesButton.setEnabled(false);
			mloadDeliveries.setEnabled(false);
			mExportButton.setEnabled(false);
			mExport.setEnabled(false);
			mAddDelivery.setEnabled(true);
			mRemoveDelivery.setEnabled(false);
			mAddDelivery.setText("Annuler ajout livraison");
			break;
			
		}
	}

}