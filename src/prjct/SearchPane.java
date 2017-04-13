package src.prjct;

import src.app.Database;
import src.app.Pallet;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.JComponent.*;
import javax.swing.filechooser.*;

import java.awt.*;
import java.awt.event.*;

import java.io.*;

public class SearchPane extends BasicPane {



	/**
	 * The list model for the all pallets list.
	 */
	private DefaultListModel<String> palletListModel;

	/**
	 * The all pallets list.
	 */
	private JList<String> palletList;

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


	public SearchPane(Database db) {
		super(db);
	}

	public JComponent createLeftTopPanel() {
		JPanel panel = new JPanel();
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		Box box = new Box(BoxLayout.X_AXIS);

		dropDown = new JComboBox();
		nbrOfPallets = customSpinner(new SpinnerNumberModel(1,1,500,1), 50, 25);
		JButton produce = customButton("Produce",new SearchPane.SearchHandler1(), 100, 25);

		box.add(nbrOfPallets);
		box.add(Box.createHorizontalStrut(200));
		box.add(produce);

		mainBox.add(Box.createVerticalStrut(50));
		mainBox.add(dropDown);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(box);

		panel.add(mainBox);
		panel.setBorder(new LineBorder(Color.BLACK));

		return panel;
	}

	public JComponent createLeftMiddlePanel() {
		JPanel panel = new JPanel();
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		Box box = new Box(BoxLayout.X_AXIS);

		dropDown = new JComboBox();
		nbrOfPallets = customSpinner(new SpinnerNumberModel(1,1,500,1), 50, 25);
		JButton produce = customButton("Search",new SearchPane.SearchHandler2(), 100, 25);

		box.add(nbrOfPallets);
		box.add(Box.createHorizontalStrut(200));
		box.add(produce);

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

		String[] texts = new String[NBR_FIELDS];
		texts[PALLET_ATTR_0] = "Attr 0";
		texts[PALLET_ATTR_1] = "Attr 1";
		texts[PALLET_ATTR_2] = "Attr 2";
		texts[PALLET_ATTR_3] = "Attr 6";

		fields = new JTextField[NBR_FIELDS];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new JTextField(20);
			fields[i].setEditable(false);
		}

		JPanel left = new JPanel();
		left.setLayout(new GridLayout(texts.length, 1));
		for (int i = 0; i < texts.length; i++) {
			JLabel label = new JLabel(texts[i] + "\n		", JLabel.RIGHT);
			panel.add(label);
		}

		JPanel right = new JPanel();
		right.setLayout(new GridLayout(fields.length, 1));
		for (int i = 0; i < fields.length; i++) {
			right.add(fields[i]);
		}
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(left);
		panel.add(right);

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(panel);

		return p;
	}

	/**
	 * Create the center middle panel.
	 *
	 *  TODO: Add title to List
	 * @return the center middle panel.
	 */
	public JComponent createMiddlePanel() {
		JPanel panel = new JPanel();

		palletListModel = new DefaultListModel<>();

		palletList = new JList<>(palletListModel);
		palletList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		palletList.addListSelectionListener(new SearchPane.PalletSelectionListener());
		JScrollPane pane = new JScrollPane(palletList);

		panel.setLayout(new GridLayout(1, 1));

		panel.add(pane);
		return panel;
	}

	/**
	 * Called when user switches to production pane
	 */
	public void entryActions() {
		clearList();
		clearFields();
	}

	private void clearFields(){
		for (int i = 0; i < fields.length; i++){
			fields[i].setText("");
		}
	}

	private void clearList(){
		palletListModel.removeAllElements();
	}


	/**
	 * A class that listens for clicks on the produce-button.
	 */
	class SearchHandler1 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int number = (Integer) nbrOfPallets.getValue();
			String cookie = (String) dropDown.getSelectedItem();
			if(number > 0 && cookie != null){
				db.producePallets(cookie, number);
			}
		}
	}

	class SearchHandler2 implements ActionListener {

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
	 * A class that listens for clicks in the all pallets list.
	 */
	class PalletSelectionListener implements ListSelectionListener {
		/**
		 * Called when the user selects a pallet in the all pallet list. Fetches
		 * pallet information from the database and displays them in the info box.
		 *
		 * @param e
		 *            The selected list item.
		 */
		public void valueChanged(ListSelectionEvent e) {
			if (palletList.isSelectionEmpty()){
				return;
			}
			String pallet_id = palletList.getSelectedValue();
			Pallet p;
			p = db.trackPalletObject(pallet_id);
			if(e.getValueIsAdjusting()){
				fields[PALLET_ATTR_0].setText(p.cookie_name);
				fields[PALLET_ATTR_1].setText(p.location);
				fields[PALLET_ATTR_2].setText(p.production_date);
			}

		}
	}
}
