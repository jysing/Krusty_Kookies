package src.app;

import java.sql.*;
import java.util.*;

/**
 * Database is a class that specifies the interface to the xxx
 * database. Uses JDBC,
 */
public class Database {

	/**
	 * The database connection.
	 */
	private Connection conn;

	/**
	 * Create the database interface object.
	 */
	public Database() {
		conn = null;
	}

	/**
	 * Open a connection to the database, using the specified file.
	 */
	public boolean openConnection(String filename) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Close the connection to the database.
	 */
	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if the connection to the database has been established.
	 *
	 * @return true if the connection has been established.
	 */
	public boolean isConnected() {
		return conn != null;
	}


	/**
	 * For INSERT, UPDATE, DELETE
	 *
	 * @param query to be executed
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or
	 * 				  (2) 0 for SQL statements that return nothing
	 * @throws SQLException an exception
	 */
	private int sendPutQuery(String query) throws SQLException{
		Statement stmt = conn.createStatement();
		return stmt.executeUpdate(query);
	}

	/**
	 * For SELECT
	 *
	 * @param query to be executed
	 * @return ResultSet containing result from SQL query
	 * @throws SQLException an exception
	 */
	private ResultSet sendGetQuery(String query) throws SQLException {
		ResultSet rs;
		Statement stmt = conn.createStatement();
		rs = stmt.executeQuery(query);
		return rs;
	}

	/**
	 * Finds and returns a list of all cookies types in the database
	 *
	 * @return List of all cookie types
	 */
	public String[] getCookieNames(){
		ArrayList<String> cookies = new ArrayList<>();
		String query = "SELECT cookie_name " +
				"FROM Cookie";
		try {
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				cookies.add(rs.getString("cookie_name"));
			}
		} catch (SQLException ex){
			System.err.println("SQL error: " + ex.getMessage());
			cookies.clear();
		}

		return cookies.toArray(new String[cookies.size()]);
	}


	/**
	 * List of all existing orders.
	 *
	 * @param from_date start date of interval
	 * @param to_date end date of interval
	 * @return list of Order_Bill
	 */
	public String[] getOrderBills(String from_date, String to_date) {
		ArrayList<String> bills = new ArrayList<>();
		String query = "SELECT order_id " +
						"FROM Order_Bill " +
						"WHERE delivery_date BETWEEN " + from_date + " AND " + to_date;
		try {
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				bills.add(rs.getString("order_id"));
			}
		} catch (SQLException ex){
				System.err.println("SQL error: " + ex.getMessage());
				bills.clear();
			}

		return bills.toArray(new String[bills.size()]);

	}

	/**
	 * 	List of all existing pallets in location freezer.
	 *
	 * @return list of pallets in freezer
	 */
	public String[] getAllPalletsInFreezer(String cookie_name) {
		ArrayList<String> pallets = new ArrayList<>();
		String query = "SELECT pallet_id, cookie_name " +
				"FROM Pallet " +
				"WHERE is_blocked IS NOT 1 AND location = 'Freezer'";
		if(!cookie_name.equals("")) query += " AND cookie_name = '" + cookie_name + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				pallets.add(rs.getString("pallet_id") + " : " + rs.getString("cookie_name"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallets.clear();
		}
		return pallets.toArray((new String[pallets.size()]));
	}

	/**
	 * Produces nbr_pallets of cookie_name and stores them in the freezer.
	 *
	 * @param cookie_name Cookie to be produced
	 * @param nbr_pallets Number of pallets to be produced of a cookie type
	 */
	public boolean producePallets(String cookie_name, int nbr_pallets) {

		String queryIngredient = "SELECT ingredient_name " +
				"FROM Cookie INNER JOIN RecipeItems ON Cookie.cookie_name = RecipeItems.cookie_name " +
				"WHERE Cookie.cookie_name = '" + cookie_name + "'";

		String queryUpdateIngredients = "UPDATE Ingredient " +
				"set amount = CASE ";
		try{
			ResultSet rs = sendGetQuery(queryIngredient);
			while(rs.next()) {
				String ingredient = rs.getString("ingredient_name");
				queryUpdateIngredients += " WHEN ingredient_name = '" + ingredient + "' THEN amount - 54 * " + nbr_pallets + " *" +
						"(SELECT amount from RecipeItems WHERE cookie_name = '" + cookie_name + "' AND ingredient_name = '" + ingredient + "')";

			}
			queryUpdateIngredients += " ELSE amount END";
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			return false;
		}


		try{
			int nbrUpdatedLines = sendPutQuery(queryUpdateIngredients);
			if (nbrUpdatedLines < 1) return false;
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
		}

		String query = "INSERT INTO Pallet (cookie_name, production_date, location) VALUES ";
		for (int i = 0; i < nbr_pallets; i++) {
			query += " ('" + cookie_name + "', date() , 'Freezer') ";
			if(i != nbr_pallets-1) query += ", ";
		}
		query += ";";

		try{
			sendPutQuery(query);
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Block pallets of a cookie_name in a specific time interval.
	 *
	 * @param cookie_name cookie to be blocked
	 * @param from_date start date of interval
	 * @param to_date end date of interval
	 */
	public boolean blockCookieType(String cookie_name, String from_date, String to_date) {
		String query = "UPDATE Pallet " +
    		"set is_blocked = CASE " + 
    		"WHEN cookie_name = '" + cookie_name + "' " + 
    		"AND location IS NOT 'Delivered' " + 
    		"AND production_date BETWEEN '" + from_date + "' AND '" + to_date + "' " + 
    		"THEN 1 " +
    		"ELSE is_blocked END";
		try{
			int nbrUpdatedLines = sendPutQuery(query);
			if (nbrUpdatedLines > 0) return true;
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
		}
		return false;
	}


	/**
	 * Returns a list of pallets that have been blocked.
	 *
	 * @return list of blocked pallets
	 */
	public String[] getAllBlockedPallets(String cookie_name) {
		ArrayList<String> pallets = new ArrayList<>();
		String query = "SELECT pallet_id, cookie_name, production_date " +
			"FROM PALLET " + 
			"WHERE is_blocked = 1";

		if(!cookie_name.equals("All")) {
			query += " AND cookie_name = '" + cookie_name + "'";
		}

		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()) {
				pallets.add("ID: " + rs.getString("pallet_id") + ",    Cookie: " + rs.getString("cookie_name") + ",    Produced: " + rs.getString("production_date"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallets.clear();
		}
		return pallets.toArray((new String[pallets.size()]));
	}

	/**
	 * Finds and returns a String with a pallets id and it's cookie type
	 *
	 * @param pallet_id ID to search for
	 * @return pallets id and cookie type
	 */
	public String getPallet(String pallet_id){
    	String pallet = "";
		String query = "SELECT pallet_id, cookie_name " +
				"FROM PALLET " +
				"WHERE pallet_id = '" + pallet_id + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				pallet = (rs.getString("pallet_id") + " : " + rs.getString("cookie_name"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallet = "";
		}
		return pallet;

	}

	/**
	 * Returns location of a specific pallet with pallet id pallet_id
	 *
	 * @param pallet_id ID to search for
	 * @return cookie type
	 */
	public String getPalletCookie(String pallet_id){
		String pallet = "";
		String query = "SELECT cookie_name " +
				"FROM PALLET " +
				"WHERE pallet_id = '" + pallet_id + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				pallet = (rs.getString("cookie_name"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallet = "";
		}
		return pallet;
	}

	/**
	 * Returns production date of a specific pallet with pallet id pallet_id
	 *
	 * @param pallet_id ID to search for
	 * @return pallets production  date
	 */
	public String getPalletProdDate(String pallet_id){

		String pallet = "";
		String query = "SELECT production_date " +
				"FROM PALLET " +
				"WHERE pallet_id = '" + pallet_id + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				pallet = (rs.getString("production_date"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallet = "";
		}
		return pallet;
	}

	/**
	 * Returns location of a specific pallet with pallet id pallet_id
	 *
	 * @param pallet_id ID to search for
	 * @return pallets location
	 */
	public String getPalletLocation(String pallet_id){
		String pallet = "";
		String query = "SELECT location " +
				"FROM PALLET " +
				"WHERE pallet_id = '" + pallet_id + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				pallet = (rs.getString("location"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallet = "";
		}
		return pallet;
	}

	/**
	 *  Retrieve information if a specific pallet is blocked
	 *
	 * @param pallet_id ID to search for
	 * @return Blocked status
	 */
	public String getPalletBlocked(String pallet_id){
		String pallet = "Not Blocked";
		String query = "SELECT is_blocked " +
				"FROM PALLET " +
				"WHERE pallet_id = '" + pallet_id + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				if(rs.getInt("is_blocked") == 1) {
					pallet = "Blocked";
				}
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallet = "";
		}
		return pallet;
	}

	/**
	 *  Retrieve information of customer linked to a specific pallet
	 *
	 * @param pallet_id ID to search for
	 * @return Pallets customer
	 */
	public String getPalletCustomer(String pallet_id){
		String pallet = "";
		String query = "SELECT customer_name " +
				"FROM Pallet INNER JOIN Order_Bill ON Pallet.order_id = Order_Bill.order_id " +
				"WHERE pallet_id = '" + pallet_id + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				pallet = (rs.getString("customer_name"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallet = "";
		}
		return pallet;
	}

	/**
	 *  Retrieve information of order id linked to a specific pallet
	 *
	 * @param pallet_id ID to search for
	 * @return Pallets order id
	 */
	public String getPalletOrder(String pallet_id){
		String pallet = "";
		String query = "SELECT Pallet.order_id " +
				"FROM Pallet INNER JOIN Order_Bill ON Pallet.order_id = Order_Bill.order_id " +
				"WHERE pallet_id = '" + pallet_id + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				pallet = (rs.getString("order_id"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallet = "";
		}
		return pallet;
	}

	/**
	 *  Retrieve information of delivery date of a specific pallet
	 *
	 * @param pallet_id ID to search for
	 * @return Pallets delivery date
	 */
	public String getPalletDelivery(String pallet_id){
		String pallet = "";
		String query = "SELECT delivery_date " +
				"FROM Pallet INNER JOIN Order_Bill ON Pallet.order_id = Order_Bill.order_id " +
				"WHERE pallet_id = '" + pallet_id + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				pallet = (rs.getString("delivery_date"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallet = "";
		}
		return pallet;
	}


	/**
	 * Returns a list of pallets that were produced in a time interval.
	 *
	 * @param from_date start date of interval
	 * @param to_date end date of interval
	 * @return list of pallets
	 */
	public String[] getPallets(String from_date, String to_date, String cookie_name) {
		ArrayList<String> pallets = new ArrayList<>();
		String query = "SELECT pallet_id, cookie_name " +
			"FROM PALLET " + 
			"WHERE production_date BETWEEN '" + from_date + "' AND '" + to_date + "'";
		if(!cookie_name.equals("All")) {
			query += " AND cookie_name = '" + cookie_name + "'";
		}
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				pallets.add(rs.getString("pallet_id") + " : " + rs.getString("cookie_name"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallets.clear();
		}
		return pallets.toArray((new String[pallets.size()]));
	}

	/**
	 * 	Returns a list of order items
	 *
	 * @return List of all existing order items
	 */
	public String[] getOrderItems() {
		ArrayList<String> pallets = new ArrayList<>();
		String query = "SELECT order_id, cookie_name " +
				"FROM OrderItems ";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				pallets.add(rs.getString("order_id") + ":" + rs.getString("cookie_name"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallets.clear();
		}
		return pallets.toArray((new String[pallets.size()]));
	}

	/**
	 * 	Retrieves a list of all delivered påallets from the database
	 *
	 * @return List of all delivered pallets
	 */
	public String[] getDeliveredPallets() {
		ArrayList<String> pallets = new ArrayList<>();
		String query = "SELECT order_id, cookie_name, delivery_date " +
				"FROM Pallet INNER JOIN Order_Bill USING (order_id)" +
				"WHERE location = 'Delivered' and is_blocked IS NOT 1";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				pallets.add("ID: " + rs.getString("order_id") + "    Cookie: " + rs.getString("cookie_name") + "    Delivered: " + rs.getString("delivery_date"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallets.clear();
		}
		return pallets.toArray((new String[pallets.size()]));
	}

	/**
	 * Checks wether an order item can be loaded unto the truck and temporarily binds it to an specific order.
	 *
	 * @param order_id Order items to verify loading capability
	 * @param cookie_name cookie type
	 * @param inLoading Number of pallets of a specific cookie type currently in the loading area
	 * @param totForOrderID Total for the whole order
	 * @return List of pallets ready to be loaded
	 */
	public String[] load(String order_id, String cookie_name, int inLoading, int totForOrderID) {
		ArrayList<String> pallets = new ArrayList<>();
		int wanted = 0;
		int available = 0;
		int toLoad;

		String query = "SELECT nbrPallet " +
				"FROM OrderItems " + 
				"WHERE order_id = '" + order_id + "' AND cookie_name = '" + cookie_name + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				wanted = rs.getInt("nbrPallet") - totForOrderID;
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
		}

		query = "SELECT COUNT() AS available " +
				"FROM Pallet " +
				"WHERE is_blocked IS NOT 1 AND location = 'Freezer' " + 
				"AND cookie_name = '" + cookie_name + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				available = rs.getInt("available") - inLoading;
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
		}

		if (available < wanted) {
			pallets.clear();
			return pallets.toArray((new String[pallets.size()]));
		}
		else toLoad = wanted;
		
		query = "SELECT pallet_id, cookie_name, production_date " +
				"FROM Pallet " + 
				"WHERE cookie_name = '" + cookie_name + "' AND location = 'Freezer' " +
				"ORDER BY production_date";

		try{
			ResultSet rs = sendGetQuery(query);
			for (int i = 0; i < inLoading; i++) rs.next();
			for (int j = inLoading; j < (toLoad+inLoading); j++) {
				rs.next();
				pallets.add(rs.getInt("pallet_id") + ":" + rs.getString("cookie_name") + ":" + order_id);
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallets.clear();
		}
		return pallets.toArray((new String[pallets.size()]));
	}

	/**
	 * Returns the customer of a order.
	 *
	 * @param order_id ID to search for
	 * @return customer
	 */
	public String getOrderCustomer(String order_id) {
		String query = "SELECT customer_name" +
				" FROM Order_Bill" +
				" WHERE order_id =  '" + order_id + "'";

		try{
			ResultSet rs = sendGetQuery(query);
			return (rs.getString("customer_name"));
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			return "";
		}
	}

	/**
	 * Returns the customer address of a order.
	 *
	 * @param order_id ID to search for
	 * @return customer address
	 */
	public String getCustomerAddress(String order_id) {
		String query = "SELECT address, country" +
				" FROM Customer INNER JOIN Order_Bill" +
				" ON Order_Bill.customer_name = Customer.customer_name" +
				" WHERE order_id =  '" + order_id + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			return rs.getString("address") + " " + rs.getString("country");
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			return "";
		}
	}

	/**
	 * Returns the # of pallets in a order item.
	 *
	 * @param order_id ID to search for
	 * @param cookie_name Cookie type
	 * @return Nbr of Pallets in order item
	 */
	public String getOrderNbrOfPallets(String order_id, String cookie_name) {
		String query = "SELECT nbrPallet " +
				"FROM OrderItems " +
				"WHERE order_id = '"+order_id+"' AND cookie_name = '" + cookie_name + "'";
		try{
			ResultSet rs = sendGetQuery(query);
			return (rs.getString("nbrPallet"));
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			return "";
		}
	}

	/**
	 * Returns the delivery date of a order.
	 *
	 * @param order_id ID to search for
	 * @return delivery date
	 */
	public String getOrderDeliveryDate(String order_id) {
		String query = "SELECT delivery_date " +
				"FROM Order_Bill " +
				"WHERE order_id = '"+order_id+"'";
		try{
			ResultSet rs = sendGetQuery(query);
			return (rs.getString("delivery_date"));

		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			return "";
		}
	}

	/**
	 *
	 * Updates a pallets location to delivered and connects it to an order id
	 *
	 * @param pallet_id Pallet to be updated
	 * @param order_id ID to connect the pallet to
	 * @return if the connection was successful
	 */
	public boolean setPalletDelivered(String pallet_id, String order_id) {
		String query = "UPDATE Pallet " +
				"SET location = 'Delivered', order_id = '" + order_id + "' WHERE pallet_id = '"+ pallet_id+"'";
		try{
			int rs = sendPutQuery(query);
			if (rs != 1) return false;
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Finds out if there's pallet connected to an order item.
	 * @param order_id Order Id to search for
	 * @param cookie_name Cookie type
	 * @return true if there's a match, otherwise false
	 */
	public boolean connectedToPallet(String order_id, String cookie_name) {
		String query = "SELECT cookie_name, order_id " +
				"FROM Pallet " +
				"WHERE order_id = '" + order_id + "' ";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				if(rs.getString("order_id").equals(order_id) && rs.getString("cookie_name").equals(cookie_name)){
					return false;
				}
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			return false;
		}
		return true;
	}
}
