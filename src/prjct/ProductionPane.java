package src.prjct;

import src.app.Database;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.JComponent.*;
import javax.swing.filechooser.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.io.*;

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

	public ProductionPane(Database db) {
		super(new Database());
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
		
		JComboBox dropDown = new JComboBox();
		JSpinner nbrOfPallets = customSpinner(new SpinnerNumberModel(1,1,500,1), 50, 25);
		JButton produce = customButton("Produce",null, 100, 25);

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
	 * Create the center middle panel.
	 *
	 * @return the center middle panel.
	 */
	public JComponent createMiddlePanel() {
		JPanel panel = new JPanel();

		specPalletListModel = new DefaultListModel<String>();

		specPalletList = new JList<String>(specPalletListModel);
		specPalletList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		return panel;
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
			// implement.
		}
	}
}