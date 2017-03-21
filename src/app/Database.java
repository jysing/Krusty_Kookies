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
	 * List of all existing orders.
	 *
	 * @param from_date start date of interval
	 * @param to_date end date of interval
	 * @return list of Order_Bill
	 */
	public String[] updateOrder_bills(String from_date, String to_date) {
		return null;
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
