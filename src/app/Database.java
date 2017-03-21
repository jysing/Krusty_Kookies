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
	 *	List of all existing orders
	 *
	 *
	 */
	public void updateOrder_bills(String from_date, String to_date) {

	} 

	/**
	 *	
	 *
	 *
	 */
	public void loading_order(String costumer_ID, String date)

	/**
	 * 	List of all existing pallets in frezzer
	 *
	 *
	 */
	public void updateFrezzer() {

	}

	public void updateDelivered(String from_date, String to_date) {

	}

	/**
	 * Villka pallets som levererats till kund 	
	 * samt datum för leveranserna
	 *
	 */
	public void deliveredPallets(String costumer) {

	}

	/**
	 *	Produce pallets
	 *
	 *
	 */
	public void producePallets(String cookie_name, int nbr_pallets) {

	}

	/**
	 * Blockera 	
	 *
	 *
	 */
	public void blockCookieType(String name, String from_date, String to_date) {

	}

	/**
	 * Lista över blockerade cookies	
	 *
	 *
	 */
	public void blockedCookieType() {

	}

	/**
	 * Lista över blockerade pallets	
	 * 
	 *
	 */
	public void blockedPallets() {

	}

	/**
	 *	Eventuellt ta in pallet_ID array
	 *
	 *
	 */
	public void exportLoadingOrder() {

	}

	public void trackPallet(int palletID) {

	}

	public void trackPallets(String from_date, String to_date) {

	}
}
