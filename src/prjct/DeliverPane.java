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

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import java.util.*;
import java.text.*;

public class DeliverPane extends BasicPane {

	/**
     * The list model for the specific pallet list.
     */
    private DefaultListModel<String> orderBillsListModel;

    /**
     * The specific pallet list.
     */
    private JList<String> orderBillsList;

    /**
     * The list model for the specific pallet list.
     */
    private DefaultListModel<String> deliveredListModel;

    /**
     * The specific pallet list.
     */
    private JList<String> deliveredList;

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

	public DeliverPane(Database db) {
		super(db);
	}

	public JComponent createLeftTopPanel() {
    	JPanel panel = new JPanel();
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		Box box = new Box(BoxLayout.X_AXIS);
		
		dropDown = new JComboBox();
		DropDownListener ddListener = new DropDownListener();
		dropDown.addItemListener(ddListener);

		JButton deliver = customButton("Deliver",new BlockHandler(), 100, 25);

		box.add(Box.createHorizontalStrut(20));
		box.add(deliver);

		mainBox.add(Box.createVerticalStrut(50));
		mainBox.add(dropDown);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(box);

		panel.add(mainBox);
		panel.setBorder(new LineBorder(Color.BLACK));

		return panel;
	}

	/**
	 * Called when user switches to deliver pane
	 */
	public void entryActions() {
		
	}

	/**
	 * Create the top middle panel.
	 *
	 * @return the top middle panel.
	 */
	public JComponent createTopPanel() {
		JPanel panel = new JPanel();

		JLabel leftLabel = customLabel("Order items",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			Font.BOLD, 16);

		JLabel rightLabel = customLabel("Delivered orders",
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

		orderBillsListModel = new DefaultListModel<String>();

		orderBillsList = new JList<String>(orderBillsListModel);
		orderBillsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		orderBillsList.addListSelectionListener(new orderBillsSelectionListener());
		
		JScrollPane p1 = new JScrollPane(orderBillsList);

		deliveredListModel = new DefaultListModel<String>();

		deliveredList = new JList<String>(deliveredListModel);
		deliveredList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		deliveredList.addListSelectionListener(new deliveredSelectionListener());

		JScrollPane p2 = new JScrollPane(deliveredList);

		panel.setLayout(new GridLayout(1, 2));
		panel.add(p1);
		panel.add(p2);
		
		return panel;
	}

	class BlockHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}

	/**
	 * A class that listens for clicks in the specific pallet list.
	 */
	class orderBillsSelectionListener implements ListSelectionListener {
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

	/**
	 * A class that listens for clicks in the specific pallet list.
	 */
	class deliveredSelectionListener implements ListSelectionListener {
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

    		if (evt.getStateChange() == ItemEvent.SELECTED) {
    			// Do something...
    		}
    	}
    }
}