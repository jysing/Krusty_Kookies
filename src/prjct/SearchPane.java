package src.prjct;

import src.app.Database;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.*;

import java.text.NumberFormat;
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
	 * TextField for searching for pallet_id
	 */
	private JFormattedTextField searchText;

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
	 * The number of the pallet attribute field
	 */
	private static final int PALLET_BLOCKED = 4;

	/**
	 * The number of the pallet attribute field
	 */
	private static final int PALLET_CUSTOMER = 5;

	/**
	 * The number of the pallet attribute field
	 */
	private static final int PALLET_ORDER = 6;

	/**
	 * The number of the pallet attribute field
	 */
	private static final int PALLET_DELIVERY = 7;


	/**
	 * The total number of fields
	 */
	private static final int NBR_FIELDS = 8;


	public SearchPane(Database db) {
		super(db);
	}

	public JComponent createLeftTopPanel() {
		JLabel labelHeader = customLabel("<html><center>Search by date</center></html>",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			Font.BOLD, 18);
		JLabel labelInfo = customLabel("<html><center>Search pallets by production date and cookie type.</center></html>",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			0, 12);

		JLabel fromLabel = customLabel("From: ",
			JLabel.RIGHT, Component.RIGHT_ALIGNMENT,
			Font.BOLD, 12);
		JLabel toLabel = customLabel("To: ",
			JLabel.RIGHT, Component.RIGHT_ALIGNMENT,
			Font.BOLD, 12);

		JPanel panel = new JPanel();

		spinnerFrom = new JSpinner(new SpinnerDateModel());
		spinnerTo = new JSpinner(new SpinnerDateModel());

		spinnerFrom.setEditor(new JSpinner.DateEditor(spinnerFrom, "yyyy/MM/dd"));
		spinnerTo.setEditor(new JSpinner.DateEditor(spinnerTo, "yyyy/MM/dd"));

		Box mainBox = new Box(BoxLayout.Y_AXIS);
		Box box = new Box(BoxLayout.X_AXIS);

		dropDown = new JComboBox();

		JButton search = customButton("Search",new SearchHandlerDate(), 100, 25);

		box.add(Box.createHorizontalStrut(10));
		box.add(fromLabel);
		box.add(spinnerFrom);
		box.add(Box.createHorizontalStrut(5));
		box.add(toLabel);
		box.add(spinnerTo);
		box.add(Box.createHorizontalStrut(20));
		box.add(dropDown);
		box.add(Box.createHorizontalStrut(20));


		mainBox.add(Box.createVerticalStrut(50));
		mainBox.add(labelHeader);
		mainBox.add(labelInfo);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(box);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(search);



		panel.add(mainBox);
		panel.setBorder(new LineBorder(Color.BLACK));

		return panel;
	}

	public JComponent createLeftMiddlePanel() {
		JLabel labelHeader = customLabel("<html><center>Search by ID</center></html>",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			Font.BOLD, 18);
		JLabel labelInfo = customLabel("<html><center>Search pallet by its' pallet ID.</center></html>",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			0, 12);

		JPanel panel = new JPanel();
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		Box box = new Box(BoxLayout.X_AXIS);

		JButton search = customButton("Search",new SearchHandlerId(), 100, 25);

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		searchText = new JFormattedTextField(formatter);



		box.add(Box.createHorizontalStrut(5));
		box.add(searchText);

		mainBox.add(Box.createVerticalStrut(20));
		mainBox.add(labelHeader);
		mainBox.add(labelInfo);
		mainBox.add(Box.createVerticalStrut(10));
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

		Box mainBox = new Box(BoxLayout.X_AXIS);
		Box labelBox = new Box(BoxLayout.Y_AXIS);
		Box attributeBox = new Box(BoxLayout.Y_AXIS);

		String[] labels = new String[NBR_FIELDS];
		labels[PALLET_ID] = "Pallet ID";
		labels[PALLET_COOKIE] = "Cookie Name";
		labels[PALLET_PRODUCTION] = "Production Date";
		labels[PALLET_LOCATION] = "Location";
		labels[PALLET_BLOCKED] = "Blocked";
		labels[PALLET_CUSTOMER] = "Customer";
		labels[PALLET_ORDER] = "Order ID";
		labels[PALLET_DELIVERY] = "Delivery Date";

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

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(panel);

		return p;
	}

	/**
	 * Create the top middle panel.
	 *
	 * @return the top middle panel.
	 */
	public JComponent createTopPanel() {
		JPanel panel = new JPanel();

		JLabel label = customLabel("Pallets",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			Font.BOLD, 16);

		panel.setLayout(new GridLayout(1, 1));
		panel.add(label);

		return panel;
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
	class SearchHandlerDate implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String from = new SimpleDateFormat("yyyy-MM-dd").format(spinnerFrom.getValue());
			String to = new SimpleDateFormat("yyyy-MM-dd").format(spinnerTo.getValue());
			String cookie_name = (String) dropDown.getSelectedItem();
			String[] result = db.getPallets(from, to, cookie_name);
			palletListModel.removeAllElements();
			for(String s: result){
				palletListModel.addElement(s);
			}
			clearFields();
		}
	}

	class SearchHandlerId implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(searchText.getValue() != null){
				String result = db.getPallet(searchText.getText());
				palletListModel.removeAllElements();
				palletListModel.addElement(result);
				clearFields();
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
			if(e.getValueIsAdjusting()){
				String pallet_id = palletList.getSelectedValue();
				pallet_id = pallet_id.split(" : ")[0];
				System.out.println("Search for this and get info: " + pallet_id);

				fields[PALLET_ID].setText(pallet_id);
				fields[PALLET_COOKIE].setText(db.getPalletCookie(pallet_id));
				fields[PALLET_PRODUCTION].setText(db.getPalletProdDate(pallet_id));
				fields[PALLET_LOCATION].setText(db.getPalletLocation(pallet_id));
				fields[PALLET_BLOCKED].setText(db.getPalletBlocked(pallet_id));
				fields[PALLET_CUSTOMER].setText(db.getPalletCustomer(pallet_id));
				fields[PALLET_ORDER].setText(db.getPalletOrder(pallet_id));
				fields[PALLET_DELIVERY].setText(db.getPalletDelivery(pallet_id));
			}
		}
	}
}
