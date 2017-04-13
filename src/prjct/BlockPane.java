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

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import java.util.*;
import java.text.*;

public class BlockPane extends BasicPane {

	/**
     * The list model for the specific pallet list.
     */
    private DefaultListModel<String> blockedPalletListModel;

    /**
     * The specific pallet list.
     */
    private JList<String> blockedPalletList;

	/**
	 * Spinner for number of crates to be produced
	 */
	private JSpinner nbrOfPallets;

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

	/**
	 *
	 * @param db
	 */
	public BlockPane(Database db) {
		super(db);
	}

	/**
	 * Create the left top panel.
	 * 
	 * @return the left top panel.
	 */
	public JComponent createLeftTopPanel() {
		JLabel labelHeader = customLabel("<html><center>Block pallets</center></html>",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			Font.BOLD, 18);
		JLabel labelInfo = customLabel("<html><center>Choose cookie type and the date interval during which<br>" +
			"the cookies were produced that you would like to block.</center></html>",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			0, 12);

		JLabel fromLabel = customLabel("From: ",
			JLabel.RIGHT, Component.RIGHT_ALIGNMENT,
			Font.BOLD, 12);
		JLabel toLabel = customLabel("To: ",
			JLabel.RIGHT, Component.RIGHT_ALIGNMENT,
			Font.BOLD, 12);

		spinnerFrom = new JSpinner(new SpinnerDateModel());
		spinnerTo = new JSpinner(new SpinnerDateModel());

    	spinnerFrom.setEditor(new JSpinner.DateEditor(spinnerFrom, "yyyy/MM/dd"));
    	spinnerTo.setEditor(new JSpinner.DateEditor(spinnerTo, "yyyy/MM/dd"));

    	JPanel panel = new JPanel();
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		Box box = new Box(BoxLayout.X_AXIS);
		
		dropDown = new JComboBox();
		DropDownListener ddListener = new DropDownListener();
		dropDown.addItemListener(ddListener);

		JButton block = customButton("Block",new BlockHandler(), 100, 25);

		box.add(Box.createHorizontalStrut(200));
		box.add(fromLabel);
		box.add(spinnerFrom);
		box.add(Box.createHorizontalStrut(10));
		box.add(toLabel);
		box.add(spinnerTo);
		box.add(Box.createHorizontalStrut(20));
		box.add(block);

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
     * Updates the cookie list.
     */
	private void updateCookieList(){
		dropDown.removeAllItems();
		dropDown.addItem("All");
		for(String s: db.getCookieNames()){
			dropDown.addItem(s);
		}
	}

	/**
     * Updates the blocked pallet list.
     */
	private void updateBlockedPalletList() {
        blockedPalletListModel.removeAllElements();
        String cookie = (String) dropDown.getSelectedItem();
        if (cookie.isEmpty()) cookie = "All";
		for (String s: db.getAllBlockedPallets(cookie)) {
            blockedPalletListModel.addElement(s);
		}
	}

	/**
	 * Called when user switches to block pane
	 */
	public void entryActions() {
		updateCookieList();
		updateBlockedPalletList();
	}

	/**
	 * Create the top middle panel.
	 *
	 * @return the top middle panel.
	 */
	public JComponent createTopPanel() {
		JPanel panel = new JPanel();

		JLabel label = customLabel("Blocked pallets",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			Font.BOLD, 16);

		panel.setLayout(new GridLayout(1, 1));
		panel.add(label);

		return panel;
	}

	/**
	 * Create the center middle panel.
	 *
	 * @return the center middle panel.
	 */
	public JComponent createMiddlePanel() {
		JPanel panel = new JPanel();

		blockedPalletListModel = new DefaultListModel<String>();

		blockedPalletList = new JList<String>(blockedPalletListModel);
		blockedPalletList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		blockedPalletList.addListSelectionListener(new blockedPalletSelectionListener());
		JScrollPane p1 = new JScrollPane(blockedPalletList);

		panel.setLayout(new GridLayout(1, 2));
		panel.add(p1);
		
		return panel;
	}

	/**
	 * A class that listens for clicks on the Block button.
	 */
	class BlockHandler implements ActionListener {
		@Override
		/**
         * Called when the user clicks the Block button. Blocks
         * pallets matching the search criteria.
         * 
         * @param e
         *            The event object (not used).
         */
		public void actionPerformed(ActionEvent e) {
			String from = new SimpleDateFormat("yyyy-MM-dd").format(spinnerFrom.getValue());
			String to = new SimpleDateFormat("yyyy-MM-dd").format(spinnerTo.getValue());
			String cookie = (String) dropDown.getSelectedItem();

			db.blockCookieType(cookie, from, to);
			updateBlockedPalletList();
		}
	}

	/**
	 * A class that listens for clicks in the specific pallet list.
	 */
	class blockedPalletSelectionListener implements ListSelectionListener {
		/**
         * Called when the user selects a pallet in the specific pallet list. Fetches
         * pallet information from the database and displays them in the info box.
         * 
         * @param e
         *            The selected list item.
         */
		public void valueChanged(ListSelectionEvent e) {
			// implement if needed.
		}
	}

	class DropDownListener implements ItemListener {
		@Override
		// This method is called only if a new item has been selected in dropDown.
  		public void itemStateChanged(ItemEvent evt) {
    		JComboBox cb = (JComboBox) evt.getSource();

    		Object item = evt.getItem();

    		if (evt.getStateChange() == ItemEvent.SELECTED) updateBlockedPalletList();
    	}
    }
}