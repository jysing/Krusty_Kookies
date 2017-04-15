package src.prjct;

import src.app.Database;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

/**
 * CrustyGUI is the user interface to the guest database. It sets up the main
 * window and connects to the database.
 */
class CrustyGUI {

	/**
	 * db is the database object.
	 */
	private Database db;

	/**
	 * tabbedPane is the contents of the window.
	 */
	private JTabbedPane tabbedPane;

	/**
	 * Create a GUI object and connect to the database.
	 *
	 * @param db Database
	 */
	CrustyGUI(Database db) {
		this.db = db;

		JFrame frame = new JFrame("Crusty Cookies");
		tabbedPane = new JTabbedPane();

		/* --- set up the tabs --- */
		/* --- Tab 1: Production --- */
		ProductionPane productionPane = new ProductionPane(db);
		tabbedPane.addTab("Production", null, productionPane, "Produce orders");
		tabbedPane.setTabComponentAt(0, tabLabel(tabbedPane.getTitleAt(0)));

		/* --- Tab 2: Block --- */
		BlockPane blockPane = new BlockPane(db);
		tabbedPane.addTab("Block", null, blockPane, "Block cookies and pallets");
		tabbedPane.setTabComponentAt(1, tabLabel(tabbedPane.getTitleAt(1)));

		/* --- Tab 3: Search --- */
		SearchPane searchPane = new SearchPane(db);
		tabbedPane.addTab("Search", null, searchPane, "Search pallets");
		tabbedPane.setTabComponentAt(2, tabLabel(tabbedPane.getTitleAt(2)));

		/* --- Tab 3: Deliver --- */
		DeliverPane deliverPane = new DeliverPane(db);
		tabbedPane.addTab("Deliver", null, deliverPane, "Deliver pallets");
		tabbedPane.setTabComponentAt(3, tabLabel(tabbedPane.getTitleAt(3)));

		tabbedPane.setSelectedIndex(0);
		tabbedPane.addChangeListener(new ChangeHandler());
		
		/* --- set up the frame --- */
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		frame.addWindowListener(new WindowHandler());
		frame.setSize(1100, 1000);
		frame.setVisible(true);

		productionPane.entryActions();
	}

	/*
	 * A custom tab label.
	 */
	private JLabel tabLabel(String title) {
		JLabel lab = new JLabel();
		lab.setText(title);
		lab.setPreferredSize(new Dimension(150,50));
		lab.setHorizontalAlignment(JLabel.CENTER);
		lab.setFont(new Font("bolder_bigger", Font.BOLD, 16));
		return lab;
	}

	/**
	 * ChangeHandler is a listener class, called when a user switches
	 * panes.
	 */
	class ChangeHandler implements ChangeListener {

		/**
		 * Called when the user switches panes. The entry actions of the new
		 * pane ae performed.
		 *
		 * @param e
		 *				The change event (not used).
		 */
		public void stateChanged (ChangeEvent e) {
			BasicPane selectedPane = (BasicPane) tabbedPane.getSelectedComponent();
			selectedPane.entryActions();
		}
	}

	/**
	 * WindowHandler is a listener class, called when the user exits the
	 * application.
	 */
	class WindowHandler extends WindowAdapter {

		/**
		 * Called when the user exits the application. Closes the connection to
		 * the database.
		 *
		 * @param e The window event (not used).
		 */
		public void windowClosing(WindowEvent e) {
			db.closeConnection();
			System.exit(0);
		}
	}
}