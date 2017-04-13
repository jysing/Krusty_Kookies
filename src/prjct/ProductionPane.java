package src.prjct;

import src.app.Database;
import src.app.Pallet;

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
	 * The number of the pallet attribute 0 field
	 */
	private static final int PALLET_ATTR_0 = 0;

	/**
	 * The number of the pallet attribute 1 field
	 */
	private static final int PALLET_ATTR_1 = 1;

	/**
	 * The number of the pallet attribute 2 field
	 */
	private static final int PALLET_ATTR_2 = 2;

	/**
	 * The number of the pallet attribute 3 field
	 */
	private static final int PALLET_ATTR_3 = 3;

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
		JPanel panel = new JPanel();
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		Box box = new Box(BoxLayout.X_AXIS);
		
		dropDown = new JComboBox();

		nbrOfPallets = customSpinner(new SpinnerNumberModel(1,1,500,1), 50, 25);
		JButton produce = customButton("Produce",new ProduceHandler(), 100, 25);
		//JButton deliver = customButton("Deliver",new ProduceHandler(), 100, 25);

		box.add(nbrOfPallets);
		box.add(Box.createHorizontalStrut(200));
		box.add(produce);
		//box.add(deliver);

		mainBox.add(Box.createVerticalStrut(50));
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
	 * @return An empty panel.
	 */
	public JComponent createLeftBottomPanel() {
		JPanel panel = new JPanel();

		Box mainBox = new Box(BoxLayout.X_AXIS);
		Box labelBox = new Box(BoxLayout.Y_AXIS);
		Box attributeBox = new Box(BoxLayout.Y_AXIS);

		for(int i = 0; i < NBR_FIELDS; i++) {
			JLabel l = customLabel("Attribute 5",
			JLabel.LEFT, Component.CENTER_ALIGNMENT,
			0, 12);
			l.add(labelBox);
		}

		fields = new JTextField[NBR_FIELDS];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new JTextField(20);
			fields[i].setEditable(false);
			attributeBox.add(fields[i]);
		}

//		String[] texts = new String[NBR_FIELDS];
//		texts[PALLET_ATTR_0] = "Attr 0";
//		texts[PALLET_ATTR_1] = "Attr 1";
//		texts[PALLET_ATTR_2] = "Attr 2";
//		texts[PALLET_ATTR_3] = "Attr 3";

//		fields = new JTextField[NBR_FIELDS];
//		for (int i = 0; i < fields.length; i++) {
//			fields[i] = new JTextField(20);
//			fields[i].setEditable(false);
//		}

//		JPanel left = new JPanel();
//		left.setLayout(new GridLayout(texts.length, 1));
//		for (int i = 0; i < texts.length; i++) {
//			JLabel label = new JLabel(texts[i] + "\n		", JLabel.RIGHT);
//			panel.add(label);
//		}

//		JPanel right = new JPanel();
//		right.setLayout(new GridLayout(fields.length, 1));
//		for (int i = 0; i < fields.length; i++) {
//			right.add(fields[i]);
//		}
//		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
//		panel.add(left);
//		panel.add(right);

//		JPanel p = new JPanel();
//		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
//		p.add(panel);

		labelBox.setBorder(new LineBorder(Color.BLACK));
		attributeBox.setBorder(new LineBorder(Color.BLACK));
		mainBox.add(labelBox);
		mainBox.add(attributeBox);
		panel.add(mainBox);
		return panel;
	}

	/**
	 * Create the center middle panel.
	 *
	 * @return the center middle panel.
	 */
	public JComponent createMiddlePanel() {
		JPanel panel = new JPanel();

		specPalletListModel = new DefaultListModel<String>();

		specPalletList = new JList<String>(specPalletListModel);
		specPalletList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		specPalletList.addListSelectionListener(new SpecPalletSelectionListener());
		JScrollPane p1 = new JScrollPane(specPalletList);

		allPalletListModel = new DefaultListModel<String>();

		allPalletList = new JList<String>(allPalletListModel);
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
			// implement.
			System.out.println("YOLLO");
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
			String pallet_id = allPalletList.getSelectedValue();
			Pallet p;
			p = db.trackPalletObject(pallet_id);
			if(e.getValueIsAdjusting()){
				//System.out.println("Laddar in film");
				fields[PALLET_ATTR_0].setText(p.cookie_name);
				fields[PALLET_ATTR_1].setText(p.location);
				fields[PALLET_ATTR_2].setText(p.production_date);
				fields[PALLET_ATTR_3].setText(Integer.toString(p.order_id));
			}
			//entryActions();
			//System.out.println("Hämtar film");
		}
	}
}