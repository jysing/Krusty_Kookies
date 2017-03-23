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
	 * Create the databace interface object.
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
	 * @throws SQLException
	 */
	private int sendPutQuery(String query) throws SQLException{
		Statement stmt = conn.createStatement();
		return stmt.executeUpdate(query);
	}

	/**
	 * For SELECT
	 *
	 * @param query to be executed
	 * @return Resultset containing result from SQL query
	 * @throws SQLException
	 */
	private ResultSet sendGetQuery(String query) throws SQLException {
		ResultSet rs = null;
		Statement stmt = conn.createStatement();
		rs = stmt.executeQuery(query);
		return rs;
	}

	public String[] getCookieNames(){
		ArrayList<String> cookies = new ArrayList<String>();
		String query = "SELECT cookie_name " +
				"FROM Cookie";
		try {
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				cookies.add(rs.getString("cookie_name"));
			}
		} catch (SQLException ex){
			System.err.println("SQL error: " + ex.getMessage());
			cookies = null;
		}

		return cookies.toArray(new String[cookies.size()]);
	}


	/**
	 * List of all existing orders.
	 * TODO: Test
	 *
	 * @param from_date start date of interval
	 * @param to_date end date of interval
	 * @return list of Order_Bill
	 */
	public String[] updateOrder_bills(String from_date, String to_date) {
		ArrayList<String> bills = new ArrayList<String>();
		String query = "SELECT order_id " +
						"FROM Order_Bill " +
						"WHERE delivery_date BETWEEN " + from_date + " AND " + to_date;
		try {
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				bills.add(rs.getString("order_id")); //Alternativt konvertera till int
				System.out.println("Order_Bill: " + rs.getString("date"));
			}
		} catch (SQLException ex){
				System.err.println("SQL error: " + ex.getMessage());
				bills = null;
			}

		return bills.toArray(new String[bills.size()]);

	}

	/**
	 * Retrieves information about a order.
	 * TODO: Change so it returns an object. Add information about pallets.
	 * @param order_id
	 * @return Information about order_id
	 */
	public String[] loading_order(int order_id){
		ArrayList<String> order = new ArrayList<String>();
		String query = "SELECT * " +
				"FROM Order_Bill INNER JOIN OrderItems ON Order_Bill.order_id = OrderItems.order_id " +
				"WHERE order_id = " + order_id;
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				order.add(rs.getString("customer_name"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			order = null;
		}
		return order.toArray((new String[order.size()]));
	}


	/**
	 * Retrieves information about a Customers orders
	 * with a specific delivery date.
	 *
	 * TODO: Redefine method
	 * @param customer_ID Customer id to search
	 * @param date Delivery date of order
	 * @return
	 */
	public String[] loading_order(String customer_ID, String date){
		return null;
	}

	/**
	 * 	List of all existing pallets in location freezer.
	 *
	 * @return list of pallets in freezer
	 */
	public String[] updateFrezzer() {
		return null;
	}

	/**
	 * List of all pallets that are delivered in a time interval.
	 *
	 * @param from_date start date of interval
	 * @param to_date end date of interval
	 * @return list of pallets delivered in time intervall
	 */
	public String[] updateDelivered(String from_date, String to_date) {
		return null;
	}

	/**
	 * See which pallets that have been delivered to a customer_ID
	 * and what dates they were delivered.
	 *
	 * @param customer_ID customer id to search
	 * @return list of pallets
	 */
	public String[] deliveredPallets(String customer_ID) {
		return null;
	}


	/**
	 * Produces nbr_pallets of cookie_name and stores them in the freezer.
	 *
	 * @param cookie_name Cookie to be produced
	 * @param nbr_pallets Number of pallets to be produced of a cookie type
	 */
	public void producePallets(String cookie_name, int nbr_pallets) {
		/*
		SELECT ingredient_name, amount
		FROM Cookie INNER JOIN RecipeItems ON cookie_name
		WHERE Cookie.cookie_name = cookie_name AND RecipeItems.cookie_name = cookie.name

		Då fås info om ingredienser och mängd. Sätt in i query nedan

		Update the ingredient

		UPDATE Ingredient
           set amount = CASE
                WHEN ingredient_name = 'Butter' THEN amount - 54*(SELECT amount from RecipeItems WHERE cookie_name = 'Berliner' AND ingredient_name = 'Butter')
                WHEN ingredient_name = 'Eggs' THEN amount - 54*(SELECT amount from RecipeItems WHERE cookie_name = 'Berliner' AND ingredient_name = 'Eggs')
                ELSE amount END
         */
		 /*

         Efter uppdatering av inventariet är det dags att skapa Pallets'
         INSERT INTO Pallet (cookie_name, order_id, production_date, location, is_blocked)
    		VALUES ('cookie_name', NULL ,'2017-02-12', 'Freezer', NULL);
		 */

		String query = "INSERT stuff";
		int result = -1;
		try{
			result = sendPutQuery(query);

		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			result = -1;
		}

	}

	/**
	 * Block pallets of a cookie_name in a specific time interval.
	 *
	 * @param cookie_name cookie to be blocked
	 * @param from_date start date of interval
	 * @param to_date end date of interval
	 */
	public void blockCookieType(String cookie_name, String from_date, String to_date) {

	}

	/**
	 * Returns a list of blocked cookies.
	 *
	 * @return list of blocked cookies
	 */
	public String[] blockedCookieType() {
		return null;
	}

	/**
	 * Returns a list of pallets that have been blocked.
	 *
	 * @return list of blocked pallets
	 */
	public String[] blockedPallets() {
		return null;
	}

	/**
	 *	Eventuellt ta in pallet_ID array
	 *
	 *
	 */
	public void exportLoadingOrder() {

	}

	/**
	 * Returns information about a pallet. The information contains production date, type of cookie
	 *
	 * @param palletID pallet to be found
	 * @return information about pallet
	 */
	public String trackPallet(int palletID) {
		return null;
	}

	/**
	 * Returns a list of pallets that were produced in a time interval.
	 *
	 * @param from_date start date of interval
	 * @param to_date end date of interval
	 * @return list of pallets
	 */
	public String[] trackPallets(String from_date, String to_date) {
		return null;
	}
}
