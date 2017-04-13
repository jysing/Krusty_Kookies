package src.prjct;

import src.app.Database;

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

	public BlockPane(Database db) {
		super(new Database());
	}

	public JComponent createLeftTopPanel() {
		SpinnerDateModel dateModel = new SpinnerDateModel();
    	JSpinner spinnerFrom = new JSpinner(dateModel);
    	JSpinner spinnerTo = new JSpinner(dateModel);

    	spinnerFrom.setEditor(new JSpinner.DateEditor(spinnerFrom, "yyyy/MM/dd"));
    	spinnerTo.setEditor(new JSpinner.DateEditor(spinnerTo, "yyyy/MM/dd"));

    	JPanel panel = new JPanel();
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		Box box = new Box(BoxLayout.X_AXIS);
		
		dropDown = new JComboBox();
		JButton block = customButton("Block",new BlockHandler(), 100, 25);

		box.add(Box.createHorizontalStrut(200));
		box.add(block);
		box.add(spinnerFrom);
		box.add(spinnerTo);

		mainBox.add(Box.createVerticalStrut(50));
		mainBox.add(dropDown);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(box);

		panel.add(mainBox);
		panel.setBorder(new LineBorder(Color.BLACK));

		return panel;
	}

	class BlockHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}
