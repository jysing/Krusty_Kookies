package src.prjct;

import src.app.Database;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

public class ProductionPane extends BasicPane {

	/**
     * The list model for the specific pallet list.
     */
    private DefaultListModel<String> specPalletListModel;

    /**
     * The specific pallet list.
     */
    private JList<String> specPalletList;

    /**
     * The list model for the all pallets list.
     */
    private DefaultListModel<String> allPalletListModel;

    /**
     * The all pallets list.
     */
    private JList<String> allPalletList;

	/**
	 * Spinner for number of crates to be produced
	 */
	private JSpinner nbrOfPallets;

	/**
	 * Drop down menu containing all cookie types
	 */
	private JComboBox dropDown;

	/**
	 * The text fields where the pallet data is shown
	 */
	private JTextField[] fields;

	/**
	 * The number of the pallet attribute field
	 */
	private static final int PALLET_ID = 0;

	/**
	 * The number of the pallet attribute field
	 */
	private static final int PALLET_COOKIE = 1;

	/**
	 * The number of the pallet attribute field
	 */
	private static final int PALLET_PRODUCTION = 2;

	/**
	 * The number of the pallet attribute field
	 */
	private static final int PALLET_LOCATION = 3;


	/**
	 * The total number of fields
	 */
	private static final int NBR_FIELDS = 4;

	/**
	 *
	 * @param db
	 */
	public ProductionPane(Database db) {
		super(db);
	}

	/**
	 * Create the left top panel. Contains tools for producing pallets containing
	 * various cookies.
	 * 
	 * @return the left top panel.
	 */
	public JComponent createLeftTopPanel() {
		JLabel labelHeader = customLabel("<html><center>Produce pallets</center></html>",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			Font.BOLD, 18);
		JLabel labelInfo = customLabel("<html><center>Select cookie type and number of pallets you wish to produce.</center></html>",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			0, 12);

		JLabel nbrLabel = customLabel("No of pallets: ",
			JLabel.RIGHT, Component.RIGHT_ALIGNMENT,
			Font.BOLD, 12);

		JPanel panel = new JPanel();
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		Box box = new Box(BoxLayout.X_AXIS);
		
		dropDown = new JComboBox();
		DropDownListener ddListener = new DropDownListener();
		dropDown.addItemListener(ddListener);

		nbrOfPallets = customSpinner(new SpinnerNumberModel(1,1,500,1), 50, 25);
		JButton produce = customButton("Produce",new ProduceHandler(), 100, 25);

		box.add(nbrLabel);
		box.add(nbrOfPallets);
		box.add(Box.createHorizontalStrut(200));
		box.add(produce);

		mainBox.add(Box.createVerticalStrut(50));
		mainBox.add(labelHeader);
		mainBox.add(labelInfo);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(dropDown);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(box);

		panel.add(mainBox);
		panel.setBorder(new LineBorder(Color.BLACK));

		return panel;
	}

	/**
	 * Create the left bottom panel.
	 *
	 * @return the left bottom panel.
	 */
	public JComponent createLeftBottomPanel() {
		JPanel panel = new JPanel();

		Box mainBox = new Box(BoxLayout.X_AXIS);
		Box labelBox = new Box(BoxLayout.Y_AXIS);
		Box attributeBox = new Box(BoxLayout.Y_AXIS);

		String[] labels = new String[NBR_FIELDS];
		labels[0] = "Pallet Id";
		labels[1] = "Cookie Type";
		labels[2] = "Production date";
		labels[3] = "Location";

		for(int i = 0; i < NBR_FIELDS; i++) {
			JLabel l = customLabel(labels[i],
			JLabel.LEFT, Component.RIGHT_ALIGNMENT,
			Font.BOLD, 14);
			labelBox.add(l);
		}

		fields = new JTextField[NBR_FIELDS];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new JTextField(20);
			fields[i].setEditable(false);
			attributeBox.add(fields[i]);
		}

		mainBox.add(labelBox);
		mainBox.add(attributeBox);
		panel.add(mainBox);
		return panel;
	}

	/**
	 * Create the top middle panel.
	 *
	 * @return the top middle panel.
	 */
	public JComponent createTopPanel() {
		JPanel panel = new JPanel();

		JLabel leftLabel = customLabel("Selected",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			Font.BOLD, 16);

		JLabel rightLabel = customLabel("All",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			Font.BOLD, 16);

		panel.setLayout(new GridLayout(1, 2));
		panel.add(leftLabel);
		panel.add(rightLabel);

		return panel;
	}

	/**
	 * Create the center middle panel.
	 *
	 * @return the center middle panel.
	 */
	public JComponent createMiddlePanel() {
		JPanel panel = new JPanel();

		specPalletListModel = new DefaultListModel<>();

		specPalletList = new JList<>(specPalletListModel);
		specPalletList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		specPalletList.addListSelectionListener(new SpecPalletSelectionListener());
		JScrollPane p1 = new JScrollPane(specPalletList);

		allPalletListModel = new DefaultListModel<>();

		allPalletList = new JList<>(allPalletListModel);
		allPalletList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		allPalletList.addListSelectionListener(new AllPalletSelectionListener());
		JScrollPane p2 = new JScrollPane(allPalletList);

		panel.setLayout(new GridLayout(1, 2));
		panel.add(p1);
		panel.add(p2);

		return panel;
	}

	/**
	 * Called when user switches to production pane
	 */
	public void entryActions() {
		updateCookieList();
		updateAllPalletList();
		clearFields();
	}

	private void clearFields(){
		for (int i = 0; i < fields.length; i++){
			fields[i].setText("");
		}
	}

	private void updateAllPalletList() {
        allPalletListModel.removeAllElements();
		for (String s: db.getAllPalletsInFreezer()){
            allPalletListModel.addElement(s);
		}
	}

	private void updateSpecPalletList() {
		specPalletListModel.removeAllElements();
		String cookie = (String) dropDown.getSelectedItem();

		for (String s: db.getAllPalletsInFreezer(cookie)){
			specPalletListModel.addElement(s);
		}
	}

	private void updateCookieList(){
		dropDown.removeAllItems();
		for(String s: db.getCookieNames()){
			dropDown.addItem(s);
		}
	}

	/**
	 * A class that listens for clicks on the produce-button.
	 */
	class ProduceHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int number = (Integer) nbrOfPallets.getValue();
			String cookie = (String) dropDown.getSelectedItem();
			if(number > 0 && cookie != null){
				db.producePallets(cookie, number);

			}
			updateAllPalletList();
			updateSpecPalletList();
			clearFields();
		}
	}

	/**
	 * A class that listens for clicks in the specific pallet list.
	 */
	class SpecPalletSelectionListener implements ListSelectionListener {
		/**
         * Called when the user selects a pallet in the specific pallet list. Fetches
         * pallet information from the database and displays them in the info box.
         * 
         * @param e
         *            The selected list item.
         */
		public void valueChanged(ListSelectionEvent e) {
			if (specPalletList.isSelectionEmpty()){
				return;
			}
			if(e.getValueIsAdjusting()){
				updateAllPalletList();
				String pallet_id = specPalletList.getSelectedValue();
				pallet_id = pallet_id.split(" : ")[0];

				fields[PALLET_ID].setText(pallet_id);
				fields[PALLET_COOKIE].setText(db.getPalletCookie(pallet_id));
				fields[PALLET_PRODUCTION].setText(db.getPalletProdDate(pallet_id));
				fields[PALLET_LOCATION].setText(db.getPalletLocation(pallet_id));
			}
		}
	}

	/**
	 * A class that listens for clicks in the all pallets list.
	 */
	class AllPalletSelectionListener implements ListSelectionListener {
		/**
         * Called when the user selects a pallet in the all pallet list. Fetches
         * pallet information from the database and displays them in the info box.
         * 
         * @param e
         *            The selected list item.
         */
		public void valueChanged(ListSelectionEvent e) {
			if (allPalletList.isSelectionEmpty()){
				return;
			}
			if(e.getValueIsAdjusting()){
				updateSpecPalletList();
				String pallet_id = allPalletList.getSelectedValue();
				pallet_id = pallet_id.split(" : ")[0];

				fields[PALLET_ID].setText(pallet_id);
				fields[PALLET_COOKIE].setText(db.getPalletCookie(pallet_id));
				fields[PALLET_PRODUCTION].setText(db.getPalletProdDate(pallet_id));
				fields[PALLET_LOCATION].setText(db.getPalletLocation(pallet_id));
			}
		}
	}

	class DropDownListener implements ItemListener {
		@Override
		// This method is called only if a new item has been selected in dropDown.
		public void itemStateChanged(ItemEvent evt) {
			JComboBox cb = (JComboBox) evt.getSource();

			Object item = evt.getItem();

			if (evt.getStateChange() == ItemEvent.SELECTED) updateSpecPalletList();
		}
	}
}