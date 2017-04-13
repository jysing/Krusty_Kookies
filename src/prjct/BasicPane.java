package src.prjct;

import src.app.Database;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

/**
 * BasicPane is a pane in the user interface. It consists of three
 * horizontically aligned subpanels.Each subpanel in turn consists
 * of three vertically aligned subpanels. Subclasses can choose to
 * configure these panels as they wish.
 * 
 * The class contains a reference to the database object, so subclasses
 * can communicate with the database.
 */
public class BasicPane extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * The database object.
	 */
	Database db;

	/**
	* Create a BasicPane object.
	*
	* @param db
	*				The database object.
	*/
	public BasicPane(Database db) {
		this.db = db;

		setLayout(new BorderLayout());				// from superclass Container (--> JComponent --> JPanel)

		/* --- The three sections of the frame --- */
		JComponent centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		JComponent rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		JComponent leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());

		/* --- Center section of the frame --- */
		JComponent topPanel = createTopPanel();
		JComponent bottomPanel = createBottomPanel();
		JComponent middlePanel = createMiddlePanel();

		/* --- Right section of the frame --- */
		JComponent rightTopPanel = createRightTopPanel();
		JComponent rightBottomPanel = createRightBottomPanel();
		JComponent rightMiddlePanel = createRightMiddlePanel();

		/* --- Left section of the frame --- */
		JComponent leftTopPanel = createLeftTopPanel();
		JComponent leftBottomPanel = createLeftBottomPanel();
		JComponent leftMiddlePanel = createLeftMiddlePanel();

		centerPanel.add(topPanel, BorderLayout.NORTH);
		centerPanel.add(bottomPanel, BorderLayout.SOUTH);
		centerPanel.add(middlePanel, BorderLayout.CENTER);

		leftPanel.add(leftTopPanel, BorderLayout.NORTH);
		leftPanel.add(leftBottomPanel, BorderLayout.SOUTH);
		leftPanel.add(leftMiddlePanel, BorderLayout.CENTER);

		rightPanel.add(rightTopPanel, BorderLayout.NORTH);
		rightPanel.add(rightBottomPanel, BorderLayout.SOUTH);
		rightPanel.add(rightMiddlePanel, BorderLayout.CENTER);

		add(centerPanel, BorderLayout.CENTER);		// from superclass Container (--> JComponent --> JPanel)
		add(rightPanel, BorderLayout.EAST);
		add(leftPanel, BorderLayout.WEST);
	}

	/* --- THE TOP PANELS --- */

	/**
	 * Create the center top panel.
	 *
	 * @return An empty panel.
	 */
	public JComponent createTopPanel() {
		return new JPanel();
	}

	/**
	 * Create the right top panel.
	 *
	 * @return An empty panel.
	 */
	public JComponent createRightTopPanel() {
		return new JPanel();
	}

	/**
	 * Create the left top panel.
	 *
	 * @return An empty panel.
	 */
	public JComponent createLeftTopPanel() {
		return new JPanel();
	}

	/* --- THE BOTTOM PANELS --- */

	/**
	 * Create the center bottom panel.
	 *
	 * @return An empty panel.
	 */
	public JComponent createBottomPanel() {
		return new JPanel();
	}

	/**
	 * Create the right bottom panel.
	 *
	 * @return An empty panel.
	 */
	public JComponent createRightBottomPanel() {
		return new JPanel();
	}

	/**
	 * Create the left bottom panel.
	 *
	 * @return An empty panel.
	 */
	public JComponent createLeftBottomPanel() {
		return new JPanel();
	}

	/* --- THE MIDDLE PANELS --- */

	/**
	 * Create the center middle panel.
	 *
	 * @return An empty panel.
	 */
	public JComponent createMiddlePanel() {
		return new JPanel();
	}

	/**
	 * Create the right middle panel.
	 *
	 * @return An empty panel.
	 */
	public JComponent createRightMiddlePanel() {
		return new JPanel();
	}

	/**
	 * Create the left middle panel.
	 *
	 * @return An empty panel.
	 */
	public JComponent createLeftMiddlePanel() {
		return new JPanel();
	}

	/**
	 * Perform the entry actions of the pane.
	 */
	public void entryActions() {}

	/**
	 * A custom JButton object.
	 */
	protected JButton customButton(String label, ActionListener actHand, int width, int height) {
		JButton b = new JButton(label);
		b.setFont(new Font("custom_button",Font.BOLD, 14));
		b.setMinimumSize(new Dimension(width,height));
		b.setMaximumSize(new Dimension(width,height));
		b.setPreferredSize(new Dimension(width,height));
		b.addActionListener(actHand);
		b.setAlignmentX(Component.CENTER_ALIGNMENT);

		return b;
	}

	/**
	 * A custom JSpinner object.
	 */
	protected JSpinner customSpinner(SpinnerNumberModel spinnerNumberModel, int width, int height) {
		JSpinner s = new JSpinner(spinnerNumberModel);
		s.setFont(new Font("custom_spinner",Font.BOLD, 14));
		s.setMinimumSize(new Dimension(width,height));
		s.setMaximumSize(new Dimension(width,height));
		s.setPreferredSize(new Dimension(width, height));
		s.setAlignmentX(Component.CENTER_ALIGNMENT);

		return s;
	}

	/**
	 * A custom JLabel object.
	 */
	protected JLabel customLabel(String text, int labelAlignment, float componentAlignment, int font, int size) {
		JLabel l = new JLabel(text, labelAlignment);
		l.setFont(new Font("custom_label",font, size));
		l.setAlignmentX(componentAlignment);

		return l;
	}
}
