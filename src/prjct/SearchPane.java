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
import java.text.SimpleDateFormat;

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
	 * Drop down menu containing all cookie types
	 */
	private JComboBox dropDown;

	/**
	 * DateSpinner for time interval
	 */
	private JSpinner spinnerFrom;

	/**
	 * DateSpinner for time interval
	 */
	private JSpinner spinnerTo;


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

		spinnerFrom = new JSpinner(new SpinnerDateModel());
		spinnerTo = new JSpinner(new SpinnerDateModel());

		spinnerFrom.setEditor(new JSpinner.DateEditor(spinnerFrom, "yyyy/MM/dd"));
		spinnerTo.setEditor(new JSpinner.DateEditor(spinnerTo, "yyyy/MM/dd"));

		Box mainBox = new Box(BoxLayout.Y_AXIS);
		Box box = new Box(BoxLayout.X_AXIS);

		dropDown = new JComboBox();

		JButton search = customButton("Search",new SearchPane.SearchHandler1(), 100, 25);

		box.add(Box.createHorizontalStrut(10));
		box.add(spinnerFrom);

		box.add(spinnerTo);
		box.add(Box.createHorizontalStrut(20));
		box.add(dropDown);
		box.add(Box.createHorizontalStrut(20));


		mainBox.add(Box.createVerticalStrut(50));
		mainBox.add(box);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(search);



		panel.add(mainBox);
		panel.setBorder(new LineBorder(Color.BLACK));

		return panel;
	}

	public JComponent createLeftMiddlePanel() {
		JPanel panel = new JPanel();
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		Box box = new Box(BoxLayout.X_AXIS);

		JButton search = customButton("Search",new SearchPane.SearchHandler2(), 100, 25);

		JTextField searchText = new JTextField();
		searchText.setEditable(true);


		box.add(Box.createHorizontalStrut(5));
		box.add(searchText);

		mainBox.add(Box.createVerticalStrut(50));
		mainBox.add(box);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(search);

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
		updateCookieList();
	}

	private void clearFields(){
		for (int i = 0; i < fields.length; i++){
			fields[i].setText("");
		}
	}

	private void clearList(){
		palletListModel.removeAllElements();
	}

	private void updateCookieList(){
		dropDown.removeAllItems();
		dropDown.addItem("All");
		for(String s: db.getCookieNames()){
			dropDown.addItem(s);
		}
	}


	/**
	 * A class that listens for clicks on the produce-button.
	 */
	class SearchHandler1 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Search by date interval");

			String from = new SimpleDateFormat("yyyy-MM-dd").format(spinnerFrom.getValue());
			String to = new SimpleDateFormat("yyyy-MM-dd").format(spinnerTo.getValue());
			System.out.println(from + " : " + to);
			String[] skit = db.getPallets(from, to);
			palletListModel.removeAllElements();
			for(String s: skit){
				palletListModel.addElement(s);
			}

		}
	}

	class SearchHandler2 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Search by pallet_id");
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
			if(e.getValueIsAdjusting()){
			String pallet_id = palletList.getSelectedValue();
			pallet_id = pallet_id.split(" : ")[0];
			System.out.println(pallet_id);
			/*Pallet p;
			p = db.trackPalletObject(pallet_id);

				fields[PALLET_ATTR_0].setText(p.cookie_name);
				fields[PALLET_ATTR_1].setText(p.location);
				fields[PALLET_ATTR_2].setText(p.production_date);

			*/
			}
		}
	}
}
