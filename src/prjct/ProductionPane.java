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

	/**
	 * Spinner for number of crates to be produced
	 */
	private JSpinner nbrOfPallets;

	/**
	 * Drop down menu containing all cookie types
	 */
	private JComboBox dropDown;

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
		JButton produce = customButton("Produce",new ProducePalletListener(), 100, 25);

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
	 * Called when user switches to production pane
	 */
	public void entryActions() {
		updateCookieList();
	}

	private void updateCookieList(){
		dropDown.removeAllItems();
		for(String s: db.getCookieNames()){
			dropDown.addItem(s);
		}
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


	class ProducePalletListener implements ActionListener {

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
			//implement
		}
	}

	/**
     * A class that listens for button clicks.
     */
	class ProduceHandler implements ActionListener {
		/**
         * Called when the user clicks the Produce button.
         * 
         * @param e
         *            The event object (not used).
         */
		public void actionPerformed(ActionEvent e) {
			// implement.
		}
	}
}