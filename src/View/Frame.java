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
import Model.Node;

/**
 * Frame that the user will see when he uses the program
 */
public class Frame extends JFrame implements ActionListener, MouseListener {

	/**
	 * Width of the frame
	 */
	private final static int WIDTH = 1000;
	/**
	 * Height of the frame
	 */
	private final static int HEIGHT = 700;
	/**
	 * Ratio of width for informations display
	 */
	private final static double INFOS_WIDTH = 0.2;

	/**
	 * Web content for Tab
	 */
	private final static String TAB = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

	/**
	 * Possible action for the buttons
	 */
	private final static String ACTION_LOAD_MAP = "ACTION_LOAD_MAP";
	/**
	 * Possible action for the buttons
	 */
	private final static String ACTION_LOAD_DELIVERIES = "ACTION_LOAD_DELIVERIES";
	/**
	 * Possible action for the buttons
	 */
	private final static String ACTION_CALCULATE_TOUR = "ACTION_CALCULATE_TOUR";
	/**
	 * Possible action for the buttons
	 */
	private final static String ACTION_EXPORT_ROADMAP = "ACTION_EXPORT_ROADMAP";
	/**
	 * Possible action for the buttons
	 */
	private final static String ACTION_EXIT = "ACTION_EXIT";
	/**
	 * Possible action for the buttons
	 */
	private final static String ACTION_ADD_DELIVERY = "ACTION_ADD_DELIVERY";
	/**
	 * Possible action for the buttons
	 */
	private final static String ACTION_REMOVE_DELIVERY = "ACTION_REMOVE_DELIVERY";
	/**
	 * Possible action for the buttons
	 */
	private final static String ACTION_UNDO = "ACTION_UNDO";
	/**
	 * Possible action for the buttons
	 */
	private final static String ACTION_REDO = "ACTION_REDO";
	/**
	 * Possible action for the buttons
	 */
	private final static String STRING_UNDO = "Annuler";
	/**
	 * Possible action for the buttons
	 */
	private final static String STRING_REDO = "Refaire";

	/**
	 * Button corresponding to the Export Roadmap command
	 */
	private JButton mExportButton;

	/**
	 * Button corresponding to the Load Plan command
	 */
	private JButton mLoadPlanButton;

	/**
	 * Button corresponding to the Load Deliveries command
	 */
	private JButton mLoadDeliveriesButton;

	/**
	 * Button corresponding to the Calculate Tour command
	 */
	private JButton mCalculateTourButton;

	/**
	 * Menu "File" of the Frame
	 */
	private JMenu mMenuFile;

	/**
	 * Menu "Edition" of the Frame
	 */
	private JMenu mMenuEdition;

	/**
	 * Menu "Actions" of the Frame
	 */
	private JMenu mMenuAction;

	/**
	 * Item "Load Map" into "File" menu
	 */
	private JMenuItem mLoadMap;

	/**
	 * Item "Add Delivery" into "Edition" menu
	 */
	private JMenuItem mAddDelivery;

	/**
	 * Item "Remove Delivery" into "Edition" menu
	 */
	private JMenuItem mRemoveDelivery;

	/**
	 * Item "Undo" into "Edition" menu
	 */
	private JMenuItem mUndo;

	/**
	 * Item "Redo" into "Edition" menu
	 */
	private JMenuItem mRedo;

	/**
	 * Item "Load Deliveries" into "File" menu
	 */
	private JMenuItem mloadDeliveries;

	/**
	 * Item "Calculate Tour" into "File" menu
	 */
	private JMenuItem mCalculateTour;

	/**
	 * Item "Export" into "File" menu
	 */
	private JMenuItem mExport;

	/**
	 * Item "Exit" into "File" menu
	 */
	private JMenuItem mExit;

	/**
	 * Menubar of the Frame
	 */
	private JMenuBar mMenuBar;

	/**
	 * Toolbar of the Frame
	 */
	private JToolBar toolbar;

	/**
	 * Label containing Node informations
	 */
	private JLabel mNodeInfos;

	/**
	 * Label containing general informations
	 */
	private JLabel mLabelInfos;

	/**
	 * Panel containing the graph
	 */
	private GraphPanel mPanelGraph;

	/**
	 * List containing all NodeViews
	 */
	public List<NodeView> listNodeView;

	/**
	 * Controller used to handle view/model interations
	 */
	private Controller mController;

	/**
	 * Button to Undo
	 */
	private JButton mUndoButton;

	/**
	 * Button to Redo
	 */
	private JButton mRedoButton;

	/**
	 * Button to Add Delivery
	 */
	private JButton mAddDeliveryButton;

	/**
	 * Button to Remove Delivery
	 */
	private JButton mRemoveDeliveryButton;

	/**
	 * Panel displaying the tree
	 */
	private XMLTreePanel mXMLTreePanel;

	/**
	 * Contains the legend image
	 */
	private JLabel mLegend;

	/**
	 * @param controller
	 *            Instance of Controller which will handle view/model
	 *            interactions Constructor of Frame
	 */
	public Frame(Controller controller) {
		mController = controller;
		mPanelGraph = new GraphPanel();
		mXMLTreePanel = new XMLTreePanel(controller);

		setTitle("Optimod'Lyon - Gestion des livraisons");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		mPanelGraph.addMouseListener(this);

		mMenuBar = new JMenuBar();

		mLabelInfos = new JLabel();
		mNodeInfos = new JLabel();

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
		mExportButton.setToolTipText("Exporter feuille de route");
		mExportButton.addActionListener(this);
		toolbar.add(mExportButton);

		toolbar.addSeparator(new Dimension(20, 10));

		icon = new ImageIcon("img/add-user-icon.png");
		mAddDeliveryButton = new JButton(icon);
		mAddDeliveryButton.setActionCommand(ACTION_ADD_DELIVERY);
		mAddDeliveryButton.setToolTipText("Ajouter une livraison");
		mAddDeliveryButton.addActionListener(this);
		mAddDeliveryButton.setEnabled(false);
		toolbar.add(mAddDeliveryButton);

		icon = new ImageIcon("img/remove-user-icon.png");
		mRemoveDeliveryButton = new JButton(icon);
		mRemoveDeliveryButton.setActionCommand(ACTION_REMOVE_DELIVERY);
		mRemoveDeliveryButton.setToolTipText("Supprimer une livraison");
		mRemoveDeliveryButton.addActionListener(this);
		mRemoveDeliveryButton.setEnabled(false);
		toolbar.add(mRemoveDeliveryButton);

		icon = new ImageIcon("img/undo.png");
		mUndoButton = new JButton(icon);
		mUndoButton.setActionCommand(ACTION_UNDO);
		mUndoButton.setToolTipText("Annuler");
		mUndoButton.addActionListener(this);
		mUndoButton.setEnabled(false);
		toolbar.add(mUndoButton);

		icon = new ImageIcon("img/redo.png");
		mRedoButton = new JButton(icon);
		mRedoButton.setActionCommand(ACTION_REDO);
		mRedoButton.setToolTipText("Refaire");
		mRedoButton.addActionListener(this);
		mRedoButton.setEnabled(false);
		toolbar.add(mRedoButton);

		this.add(toolbar, BorderLayout.NORTH);

		mLabelInfos.setText("Infos générales");
		mNodeInfos.setText("<html>Noeud sélectionné : <br>Aucun");

		mMenuEdition = new JMenu("Edition");

		mUndo = new JMenuItem("Annuler");
		mUndo.setActionCommand(ACTION_UNDO);
		mUndo.addActionListener(this);
		mUndo.setEnabled(false);
		mMenuEdition.add(mUndo);

		mRedo = new JMenuItem("Refaire");
		mRedo.setActionCommand(ACTION_REDO);
		mRedo.addActionListener(this);
		mRedo.setEnabled(false);
		mMenuEdition.add(mRedo);

		mMenuEdition.addSeparator();

		mAddDelivery = new JMenuItem("Ajouter la livraison");
		mAddDelivery.setActionCommand(ACTION_ADD_DELIVERY);
		mAddDelivery.addActionListener(this);
		mMenuEdition.add(mAddDelivery);

		mRemoveDelivery = new JMenuItem("Supprimer une livraison");

		mRemoveDelivery.setActionCommand(ACTION_REMOVE_DELIVERY);
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

		mExport = new JMenuItem("Exporter feuille de route");
		mExport.setActionCommand(ACTION_EXPORT_ROADMAP);
		mExport.addActionListener(this);
		mMenuFile.add(mExport);

		mMenuFile.addSeparator();

		mExit = new JMenuItem("Quitter");
		mExit.setActionCommand(ACTION_EXIT);
		mExit.addActionListener(this);
		mMenuFile.add(mExit);

		mMenuAction = new JMenu("Action");
		mCalculateTour = new JMenuItem("Calculer la tournée");
		mCalculateTour.setActionCommand(ACTION_CALCULATE_TOUR);
		mCalculateTour.addActionListener(this);
		mMenuAction.add(mCalculateTour);

		mMenuBar.add(mMenuFile);

		mMenuBar.add(mMenuEdition);

		mMenuBar.add(mMenuAction);

		mLabelInfos.setPreferredSize(new Dimension((int) (INFOS_WIDTH * WIDTH),
				HEIGHT));

		this.add(mPanelGraph, BorderLayout.CENTER);
		this.add(mXMLTreePanel, BorderLayout.WEST);

		mLegend = new JLabel(new ImageIcon("img/legende.png"));

		JPanel panelInfos = new JPanel();
		panelInfos.setLayout(new GridLayout(3, 1));
		panelInfos.add(mLabelInfos);
		panelInfos.add(mNodeInfos);
		panelInfos.add(mLegend);

		mLegend.setVisible(false);
		mNodeInfos.setVisible(false);

		this.add(panelInfos, BorderLayout.EAST);

		setJMenuBar(mMenuBar);
		changeState(Controller.State.NEW);
		this.setVisible(true);

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (mPanelGraph.getListNodeView() != null) {
			for (NodeView nv : mPanelGraph.getListNodeView()) {
				if (nv.onClick(arg0)) {
					String nodeInfos = "<html>Noeud sélectionné : <br>Adresse : "
							+ nv.getNode().getId();
					if (nv.getNode().isWarehouse()) {
						nodeInfos += "<br>Entrepôt";
					} else if (nv.getNode().hasDelivery()) {
						NumberFormat nf = new DecimalFormat("##00");
						int heureDep = nv.getNode().getDelivery()
								.getDepartureHour();
						int minDep = heureDep;
						int secDep = heureDep;
						int heureArr = nv.getNode().getDelivery()
								.getArrivalHour();
						int minArr = heureArr;
						int secArr = heureArr;

						nodeInfos += "<br>Livraison : Oui <br>Intervalle horaire : "
								+ nf.format(heureArr / 3600)
								+ "h"
								+ nf.format(minArr / (3600 * 60))
								+ ":"
								+ nf.format(secArr / (3600 * 60 * 60))
								+ " à "
								+ nf.format(heureDep / 3600)
								+ "h"
								+ nf.format(minDep / (3600 * 60))
								+ ":"
								+ nf.format(secDep / (3600 * 60 * 60))
								+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					} else {
						nodeInfos += "<br>Livraison : Non</html>";
					}
					mNodeInfos.setText(nodeInfos);
					mController.onNodeSelected(nv.getNode());
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (arg0.getActionCommand()) {
		case (ACTION_LOAD_MAP):
			mController.browseNetworkClicked();
			break;
		case (ACTION_LOAD_DELIVERIES):
			mController.browseDeliveryClicked();
			break;
		case (ACTION_CALCULATE_TOUR):
			mController.calculateTourClicked();
			break;
		case (ACTION_EXPORT_ROADMAP):
			mController.saveRoadmapClicked();
			break;
		case (ACTION_ADD_DELIVERY):
			mController.addDeliveryClicked();
			break;
		case (ACTION_EXIT):
			System.exit(0);
			break;
		case (ACTION_REMOVE_DELIVERY):
			mController.removeDeliveryClicked();
			break;
		case (ACTION_REDO):
			mController.redoClicked();
			break;
		case (ACTION_UNDO):
			mController.undoClicked();
			break;
		}
	}

	/**
	 * Method used to write into the label containing node informations
	 * 
	 * @param node
	 *            Node selected in the frame
	 */
	public void setSelectedNode(Node node) {
		String nodeInfos = "<html>Noeud sélectionné : <br>" + TAB
				+ "Adresse : " + node.getId() + "<br>";
		if (node.isWarehouse()) {
			nodeInfos += TAB + "Entrepôt <br>";
		} else if (node.hasDelivery()) {
			String depHour = node.getDelivery().getFormattedDepartureHour();
			String arrHour = node.getDelivery().getFormattedArrivalHour();
			String delHour = node.getDelivery().getFormattedDeliveryHour();

			String startTSHour = node.getDelivery().getTimeSlot()
					.getFormattedStartHour();
			String endTSHour = node.getDelivery().getTimeSlot()
					.getFormattedEndHour();

			nodeInfos += TAB + "Livraison : Oui <br>" + TAB
					+ "Intervalle horaire :<br>" + TAB + TAB + "De "
					+ startTSHour + " à " + endTSHour + "<br>" + TAB
					+ "Horaire : <br>" + TAB + TAB + "Heure d'arrivée : "
					+ arrHour + "<br>" + TAB + TAB + "Heure de livraison : "
					+ delHour + "<br>" + TAB + TAB + "Heure de départ : "
					+ depHour + "<br></html>";
		} else {
			nodeInfos += TAB + "Livraison : Non</html>";
		}
		mNodeInfos.setText(nodeInfos);
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
	 * Set the network of the frame
	 */
	public void setNetwork(Network n) {
		mPanelGraph.setNetwork(n);
		mXMLTreePanel.setNetwork(n);
	}

	/**
	 * Update the frame depending on the state
	 * 
	 * @param state
	 *            current state
	 */
	public void changeState(Controller.State state) {
		switch (state) {
		case NEW:
			mLoadPlanButton.setEnabled(true);
			mLoadMap.setEnabled(true);
			mLoadDeliveriesButton.setEnabled(false);
			mloadDeliveries.setEnabled(false);
			mCalculateTour.setEnabled(false);
			mCalculateTourButton.setEnabled(false);
			mExportButton.setEnabled(false);
			mExport.setEnabled(false);
			mAddDeliveryButton.setEnabled(false);
			mRemoveDeliveryButton.setEnabled(false);
			mAddDelivery.setEnabled(false);
			mRemoveDelivery.setEnabled(false);
			mAddDelivery.setText("Ajouter la livraison");
			mLabelInfos
					.setText("<html>Vous pouvez charger un réseau via le menu fichier ou la barre d'actions </html>");
			break;
		case NETWORK_LOADED:
			mLoadPlanButton.setEnabled(true);
			mLoadMap.setEnabled(true);
			mLoadDeliveriesButton.setEnabled(true);
			mloadDeliveries.setEnabled(true);
			mCalculateTour.setEnabled(false);
			mCalculateTourButton.setEnabled(false);
			mExportButton.setEnabled(false);
			mExport.setEnabled(false);
			mAddDeliveryButton.setEnabled(false);
			mRemoveDeliveryButton.setEnabled(false);
			mAddDelivery.setEnabled(false);
			mRemoveDelivery.setEnabled(false);
			mLegend.setVisible(true);
			mNodeInfos.setVisible(true);
			mAddDelivery.setText("Ajouter la livraison");
			mLabelInfos
					.setText("<html>Vous pouvez charger une demande de livraisons via le menu fichier ou la barre d'actions</html>");
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
			mAddDeliveryButton.setEnabled(false);
			mRemoveDeliveryButton.setEnabled(false);
			mAddDelivery.setEnabled(false);
			mRemoveDelivery.setEnabled(false);
			mAddDelivery.setText("Ajouter la livraison");
			mLabelInfos
					.setText("<html>Vous pouvez générer la tournée via le menu fichier ou la barre d'actions</html>");
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
			mAddDeliveryButton.setEnabled(false);
			mRemoveDeliveryButton.setEnabled(false);
			mAddDelivery.setEnabled(false);
			mRemoveDelivery.setEnabled(false);
			mAddDelivery.setText("Ajouter la livraison");
			mLabelInfos
					.setText("<html>Vous pouvez supprimer une livraison en cliquant dessus puis sur Supprimer la livraison<br><br>"
							+ "Vous pouvez ajouter une livraison en cliquant sur le noeud à ajouter puis sur Ajouter la livraison<br><br>"
							+ "Vous pouvez générer la feuille de route via le menu Fichier ou la barre d'actions</html>");
			break;
		case TOUR_NODE_SELECTED:
			mLoadPlanButton.setEnabled(true);
			mLoadMap.setEnabled(true);
			mLoadDeliveriesButton.setEnabled(true);
			mloadDeliveries.setEnabled(true);
			mExportButton.setEnabled(true);
			mCalculateTour.setEnabled(true);
			mCalculateTourButton.setEnabled(true);
			mExport.setEnabled(true);
			mAddDeliveryButton.setEnabled(false);
			mRemoveDeliveryButton.setEnabled(true);
			mAddDelivery.setEnabled(false);
			mRemoveDelivery.setEnabled(true);
			mAddDelivery.setText("Ajouter la livraison");
			mLabelInfos
					.setText("<html>Vous pouvez supprimer la livraison sélectionnée en cliquant sur Supprimer la livraison<br><br>"
							+ "Vous pouvez générer la feuille de route via le menu Fichier ou la barre d'actions</html>");
			break;
		case WAREHOUSE_SELECTED:
			mLoadPlanButton.setEnabled(true);
			mLoadMap.setEnabled(true);
			mLoadDeliveriesButton.setEnabled(true);
			mloadDeliveries.setEnabled(true);
			mExportButton.setEnabled(true);
			mCalculateTour.setEnabled(true);
			mCalculateTourButton.setEnabled(true);
			mExport.setEnabled(true);
			mAddDelivery.setEnabled(false);
			mRemoveDelivery.setEnabled(false);
			mAddDelivery.setText("Ajouter la livraison");
			mLabelInfos
					.setText("<html>Vous pouvez générer la feuille de route via le menu Fichier ou la barre d'actions</html>");
			break;
		case OTHER_NODE_SELECTED:
			mLoadPlanButton.setEnabled(true);
			mLoadMap.setEnabled(true);
			mLoadDeliveriesButton.setEnabled(true);
			mloadDeliveries.setEnabled(true);
			mCalculateTour.setEnabled(true);
			mCalculateTourButton.setEnabled(true);
			mExportButton.setEnabled(true);
			mExport.setEnabled(true);
			mAddDeliveryButton.setEnabled(true);
			mRemoveDeliveryButton.setEnabled(false);
			mAddDelivery.setEnabled(true);
			mRemoveDelivery.setEnabled(false);
			mAddDelivery.setText("Ajouter la livraison");
			mLabelInfos
					.setText("<html>Vous pouvez ajouter une livraison sur le noeud sélectionné en cliquant sur Ajouter la livraison<br><br>"
							+ "Vous pouvez générer la feuille de route via le menu Fichier ou la barre d'actions</html>");
			break;
		case ADDING_DELIVERY:
			mLoadPlanButton.setEnabled(false);
			mLoadMap.setEnabled(false);
			mLoadDeliveriesButton.setEnabled(false);
			mloadDeliveries.setEnabled(false);
			mExportButton.setEnabled(false);
			mExport.setEnabled(false);
			mAddDelivery.setEnabled(true);
			mAddDeliveryButton.setEnabled(true);
			mCalculateTour.setEnabled(false);
			mCalculateTourButton.setEnabled(false);
			mRemoveDeliveryButton.setEnabled(false);
			mRemoveDelivery.setEnabled(false);
			mAddDelivery.setText("Annuler ajout livraison");
			mLabelInfos
					.setText("<html>Cliquez sur la livraison après laquelle vous voulez insérer la nouvelle livraison</html>");
			break;
		}
	}

	/**
	 * Set undo/redo buttons
	 * 
	 * @param undoMessage
	 * @param redoMessage
	 */
	public void setUndoRedo(String undoMessage, String redoMessage) {
		if (undoMessage != null) {
			mUndo.setText(STRING_UNDO + " " + undoMessage);
			mUndo.setEnabled(true);
			mUndoButton.setEnabled(true);
		} else {
			mUndo.setText(STRING_UNDO);
			mUndo.setEnabled(false);
			mUndoButton.setEnabled(false);
		}

		if (redoMessage != null) {
			mRedo.setText(STRING_REDO + " " + redoMessage);
			mRedo.setEnabled(true);
			mRedoButton.setEnabled(true);
		} else {
			mRedo.setText(STRING_REDO);
			mRedo.setEnabled(false);
			mRedoButton.setEnabled(false);
		}
	}
}