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

public class ProductionPane extends BasicPane {

	public ProductionPane(Database db) {
		super(new Database());
	}

	/**
	 * Create the left top panel. Contains tools for determining the number
	 * of tables to be used in the seating.
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
	 * Create the left middle panel.
	 *
	 * @return the left middle panel.
	 */
	public JComponent createLeftMiddlePanel() {
		JPanel panel = new JPanel();

		return panel;
	}

	/**
	 * Create the left bottom panel.
	 *
	 * @return the left bottom panel.
	 */
	public JComponent createLeftBottomPanel() {
		JPanel panel = new JPanel();

		return panel;
	}
}