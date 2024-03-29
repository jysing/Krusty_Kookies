package src.prjct;

import src.app.Database;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import java.util.*;

/**
 * The GUI pane where the user can load and deliver pallets that have an attached
 * order ID to it (i.e. pallets that are part of an order item). Contains three
 * lists which contains order items, loaded pallets (order items) and delivered 
 * pallets, respectively.
 */
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
    private DefaultListModel<String> loadedListModel;

    /**
     * The specific pallet list.
     */
    private JList<String> loadedList;

    /**
     * The list model for the specific pallet list.
     */
    private DefaultListModel<String> deliveredListModel;

    /**
     * The specific pallet list.
     */
    private JList<String> deliveredList;

	/**
	 * The text fields where the order item data is shown
	 */
	private JTextField[] fields;

	/**
	 * The number of the order item attribute field
	 */
	private static final int ORDER_ID= 0;

	/**
	 * The number of the order item attribute field
	 */
	private static final int ORDER_CUSTOMER = 1;

	/**
	 * The number of the order item attribute field
	 */
	private static final int ORDER_ADDRESS = 2;

	/**
	 * The number of the order item attribute field
	 */
	private static final int ORDER_COOKIE = 3;

	/**
	 * The number of the order item attribute field
	 */
	private static final int ORDER_NBR_OF_PALLETS = 4;

	/**
	 * The number of the order item attribute field
	 */
	private static final int ORDER_DELIVERY = 5;

	/**
	 * The total number of fields
	 */
	private static final int NBR_FIELDS = 6;

	/**
	 *
	 * @param db Database
	 */
	DeliverPane(Database db) {
		super(db);
	}

	/**
	 * Create the left top panel.
	 *
	 * @return the left top panel.
	 */
	public JComponent createLeftTopPanel() {
    	JLabel labelHeader = customLabel("<html><center>Deliver orders</center></html>",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			Font.BOLD, 18);
		JLabel labelInfo = customLabel("<html><center>Select which order item you wish to load to<br>" +
			"a truck and then deliver and print corresponding loading orders. </center></html>",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			0, 12);

    	JPanel panel = new JPanel();
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		Box box = new Box(BoxLayout.X_AXIS);
		
		JButton load = customButton("Load",new LoadHandler(), 100, 25);
		JButton deliver = customButton("Deliver",new DeliverHandler(), 100, 25);

		box.add(Box.createHorizontalStrut(20));
		box.add(load);
		box.add(Box.createHorizontalStrut(20));
		box.add(deliver);

		mainBox.add(Box.createVerticalStrut(50));
		mainBox.add(labelHeader);
		mainBox.add(labelInfo);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(box);

		panel.add(mainBox);
		panel.setBorder(new LineBorder(Color.BLACK));

		return panel;
	}

	/**
	 * Create the left bottom panel.
	 *
	 * @return the left bottom panel.
	 */
	public JComponent createLeftBottomPanel() {
		JPanel panel = new JPanel();

		Box mainBox = new Box(BoxLayout.X_AXIS);
		Box labelBox = new Box(BoxLayout.Y_AXIS);
		Box attributeBox = new Box(BoxLayout.Y_AXIS);

		String[] labels = new String[NBR_FIELDS];
		labels[ORDER_ID] = "Order Id";
		labels[ORDER_CUSTOMER] = "Customer";
		labels[ORDER_ADDRESS] = "Address";
		labels[ORDER_COOKIE] = "Cookie Name";
		labels[ORDER_NBR_OF_PALLETS] = "Nbr of Pallet";
		labels[ORDER_DELIVERY] = "Due Delivery Date";

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
		return panel;
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

		JLabel centerLabel = customLabel("Loaded",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			Font.BOLD, 16);

		JLabel rightLabel = customLabel("Delivered order items",
			JLabel.CENTER, Component.CENTER_ALIGNMENT,
			Font.BOLD, 16);

		panel.setLayout(new GridLayout(1, 2));
		panel.add(leftLabel);
		panel.add(centerLabel);
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

		orderBillsListModel = new DefaultListModel<>();

		orderBillsList = new JList<>(orderBillsListModel);
		orderBillsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		orderBillsList.addListSelectionListener(new orderBillsSelectionListener());
		
		JScrollPane p1 = new JScrollPane(orderBillsList);


		loadedListModel = new DefaultListModel<>();

		loadedList = new JList<>(loadedListModel);
		loadedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		loadedList.addListSelectionListener(new loadedListSelectionListener());

		JScrollPane p2 = new JScrollPane(loadedList);

		deliveredListModel = new DefaultListModel<>();


		deliveredList = new JList<>(deliveredListModel);
		deliveredList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		deliveredList.addListSelectionListener(new deliveredSelectionListener());

		JScrollPane p3 = new JScrollPane(deliveredList);

		panel.setLayout(new GridLayout(1, 2));
		panel.add(p1);
		panel.add(p2);
		panel.add(p3);
		
		return panel;
	}

	/**
	 * Called when user switches to deliver pane
	 */
	public void entryActions() {
		deliveredList();
		orderItemsList();
		loadedItemList();
	}

	private void loadedItemList() {
		loadedListModel.removeAllElements();
	}

	private void orderItemsList(){
		orderBillsListModel.removeAllElements();
		for (String s: db.getOrderItems()) {
			String order_id = s.split(":")[0].trim();
			String cookie_name = s.split(":")[1].trim();
			if(db.connectedToPallet(order_id, cookie_name)){
				orderBillsListModel.addElement(s);
			}

		}
	}

	private void deliveredList() {
        deliveredListModel.removeAllElements();
		for (String s: db.getDeliveredPallets()) {
			if(!deliveredListModel.contains(s)) deliveredListModel.addElement(s);
		}
	}

	/**
	 * A class that listens for clicks on the Load button.
	 */
	class LoadHandler implements ActionListener {

		/**
         * Called when the user clicks the Load button. Loads an order item
         * to a truck.
         * 
         * @param e
         *            The event object (not used).
         */
		@Override
		public void actionPerformed(ActionEvent e) {
			String str = orderBillsList.getSelectedValue();
			if (str != null) {
				String order_id = str.split("[:]")[0].trim();
				String cookie_name = str.split("[:]")[1].trim();

				int totInLoaded = 0;
				int totForOrderID = 0;
				Boolean fulfilled = false;
				for(int i = 0; i < loadedListModel.getSize(); i++) {
     				String inLoaded =  loadedListModel.getElementAt(i);
     				if (inLoaded.split("[:]").length < 3) {
     					fulfilled = true;
     					break;
     				}
     				String currOrderID = inLoaded.split("[:]")[2].trim();
     				String inLoadedCookieName = inLoaded.split("[:]")[1].trim();

     				if (currOrderID.equals(order_id) && inLoadedCookieName.equals(cookie_name)) {
     					fulfilled = true;
     					break;
     				}
     				if (inLoadedCookieName.equals(cookie_name)) {
     					totInLoaded++;
     					String inLoadedForOrderID = inLoaded.split("[:]")[1].trim();
     					if (inLoadedForOrderID.equals(order_id)) totForOrderID++;
     				}
				}
				if (!fulfilled) {
					for (String s : db.load(order_id, cookie_name, totInLoaded, totForOrderID)) {
						loadedListModel.addElement(s);
					}
				}	
			}
		}
	}

	/**
	 * A class that listens for clicks on the Deliver button.
	 */
	class DeliverHandler implements ActionListener {

		/**
         * Called when the user clicks the Deliver button. Marks an order
         * item as delivered. Also opens up a file browser for saving the 
         * loading order as an csv-file.
         * 
         * @param e The event object (not used).
         */
		@Override
		public void actionPerformed(ActionEvent e) {
			String filePath;
			do{
				File selectedFile;
				try {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setAcceptAllFileFilterUsed(false);
					fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".csv","csv"));
					fileChooser.setPreferredSize(new Dimension(700,500));
					fileChooser.showSaveDialog(null);
					selectedFile = fileChooser.getSelectedFile();
					filePath = selectedFile.getPath();

					LinkedList<String[]> orders = new LinkedList<>();
					ArrayList<String> tempList = new ArrayList<>();

					for (int i = 0; i < loadedListModel.size(); i++){
						String item = loadedListModel.getElementAt(i);
						String pallet_id = item.split(":")[0].trim();
						String cookie_name = item.split(":")[1].trim();
						String order_id = item.split(":")[2].trim();
						String derp = order_id + ":" + cookie_name;
						db.setPalletDelivered(pallet_id, order_id); //Gör att Pallet ändras till delivered
						if(!tempList.contains(derp)){
							tempList.add(derp);
							String customer = db.getOrderCustomer(order_id);
							String address = db.getCustomerAddress(order_id);
							String nbrPallets = db.getOrderNbrOfPallets(order_id, cookie_name);
							String[] row = new String[4];
							row[0] = customer;
							row[1] = address;
							row[2] = cookie_name;
							row[3] = nbrPallets;
							orders.add(row);
						}
					}
					new CSVExporter(orders, filePath);
				}
				catch (NullPointerException ex) {
					ex.printStackTrace();
					filePath = null;
				}

			}
			while(filePath == null);

			entryActions();

		}
	}

	/**
	 * A class that listens for clicks in the specific order items list.
	 */
	class orderBillsSelectionListener implements ListSelectionListener {
		/**
         * Called when the user selects a order item in the specific order items list. Fetches
         * pallet information from the database and displays them in the info box.
         * 
         * @param e
         *            The selected list item.
         */
		public void valueChanged(ListSelectionEvent e) {
			if (orderBillsList.isSelectionEmpty()){
				return;
			}
			if(e.getValueIsAdjusting()){
				String selectedValue = orderBillsList.getSelectedValue();
				String order_id = selectedValue.split(":")[0].trim();
				String cookie_name = selectedValue.split(":")[1].trim();

				fields[ORDER_ID].setText(order_id);
				fields[ORDER_CUSTOMER].setText(db.getOrderCustomer(order_id));
				fields[ORDER_ADDRESS].setText(db.getCustomerAddress(order_id));
				fields[ORDER_COOKIE].setText(cookie_name);
				fields[ORDER_NBR_OF_PALLETS].setText(db.getOrderNbrOfPallets(order_id, cookie_name));
				fields[ORDER_DELIVERY].setText(db.getOrderDeliveryDate(order_id));
			}
		}
	}

	/**
	 * A class that listens for clicks in the specific loaded list.
	 */
	class loadedListSelectionListener implements ListSelectionListener {
		/**
         * Called when the user selects a item in the specific delivered list. Fetches
         * pallet information from the database and displays them in the info box.
         * 
         * @param e
         *            The selected list item.
         */
		public void valueChanged(ListSelectionEvent e) {
			if (loadedList.isSelectionEmpty()){
				return;

			}
			if(e.getValueIsAdjusting()){
				String selectedValue = loadedList.getSelectedValue();

				String order_id = selectedValue.split(":")[2].trim();
				String cookie_name = selectedValue.split(":")[1].trim();

				fields[ORDER_ID].setText(order_id);
				fields[ORDER_CUSTOMER].setText(db.getOrderCustomer(order_id));
				fields[ORDER_ADDRESS].setText(db.getCustomerAddress(order_id));
				fields[ORDER_COOKIE].setText(cookie_name);
				fields[ORDER_NBR_OF_PALLETS].setText(db.getOrderNbrOfPallets(order_id, cookie_name));
				fields[ORDER_DELIVERY].setText(db.getOrderDeliveryDate(order_id));

			}
		}
	}

	/**
	 * A class that listens for clicks in the specific delivered list.
	 */
	class deliveredSelectionListener implements ListSelectionListener {
		/**
         * Called when the user selects a item in the specific delivered list. Fetches
         * pallet information from the database and displays them in the info box.
         * 
         * @param e
         *            The selected list item.
         */
		public void valueChanged(ListSelectionEvent e) {
			if (deliveredList.isSelectionEmpty()){
				return;

			}
			if(e.getValueIsAdjusting()){
				String selectedValue = deliveredList.getSelectedValue();
				String order_id = selectedValue.split(":")[1].trim();
				order_id = order_id.split(" ")[0].trim();

				String cookie_name = selectedValue.split(":")[2].trim();
				cookie_name = cookie_name.split("Delivered")[0].trim();



				fields[ORDER_ID].setText(order_id);
				fields[ORDER_CUSTOMER].setText(db.getOrderCustomer(order_id));
				fields[ORDER_ADDRESS].setText(db.getCustomerAddress(order_id));
				fields[ORDER_COOKIE].setText(cookie_name);
				fields[ORDER_NBR_OF_PALLETS].setText(db.getOrderNbrOfPallets(order_id, cookie_name));
				fields[ORDER_DELIVERY].setText(db.getOrderDeliveryDate(order_id));

			}
		}
	}
}