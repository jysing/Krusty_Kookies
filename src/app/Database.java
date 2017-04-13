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
	 * TODO: Test
	 *
	 * @param from_date start date of interval
	 * @param to_date end date of interval
	 * @return list of Order_Bill
	 */
	public String[] getOrderBills(String from_date, String to_date) {
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
	 * 	List of all existing pallets in location freezer.
	 * TODO: Concaterna cookie_name med pallet_id
	 * @return list of pallets in freezer
	 */
	public String[] getAllPalletsInFreezer() {
		ArrayList<String> pallets = new ArrayList<String>();
		String query = "SELECT pallet_id " +
				"FROM Pallet " +
				"WHERE is_blocked IS NOT 1 AND location = 'Freezer'";
		try{
			ResultSet rs = sendGetQuery(query);
			while(rs.next()){
				pallets.add(rs.getString("pallet_id"));
			}
		}catch(SQLException ex){
			System.err.println(ex.getMessage());
			pallets = null;
		}
		return pallets.toArray((new String[pallets.size()]));
	}

	/**
	 * List of all pallets that are delivered in a time interval.
	 *
	 * @param from_date start date of interval
	 * @param to_date end date of interval
	 * @return list of pallets delivered in time intervall
	 */
	public String[] getDeliveredPallets(String from_date, String to_date) {
		return null;
	}

	/**
	 * See which pallets that have been delivered to a customer_ID
	 * and what dates they were delivered.
	 *
	 * @param customer_ID customer id to search
	 * @return list of pallets
	 */
	public String[] getDeliveredPallets(String customer_ID) {
		return null;
	}


	/**
	 * Produces nbr_pallets of cookie_name and stores them in the freezer.
	 *
	 * @param cookie_name Cookie to be produced
	 * @param nbr_pallets Number of pallets to be produced of a cookie type
	 */
	public boolean producePallets(String cookie_name, int nbr_pallets) {
		/*
		SELECT ingredient_name, amount
		FROM Cookie INNER JOIN RecipeItems ON cookie_name
		WHERE Cookie.cookie_name = cookie_name AND RecipeItems.cookie_name = cookie.name

		Då fås info om ingredienser och mängd. Sätt in i query nedan

		Update the ingredient

		UPDATEamo Ingredient
           set unt = CASE
                WHEN ingredient_name = 'Butter' THEN amount - 54*(SELECT amount from RecipeItems WHERE cookie_name = 'Berliner' AND ingredient_name = 'Butter')
                WHEN ingredient_name = 'Eggs' THEN amount - 54*(SELECT amount from RecipeItems WHERE cookie_name = 'Berliner' AND ingredient_name = 'Eggs')
                ELSE amount END
         */
		 /*

         Efter uppdatering av inventariet är det dags att skapa Pallets'
         INSERT INTO Pallet (cookie_name, order_id, production_date, location, is_blocked)
    		VALUES ('cookie_name', NULL ,'2017-02-12', 'Freezer', NULL);
		 */
		for (int i = 0; i < nbr_pallets; i++) {
			String query = "INSERT INTO Pallet (cookie_name, production_date, location) " +
					"VALUES ('" + cookie_name + "', date() , 'Freezer');";
			int result;
			try{
				result = sendPutQuery(query);

			}catch(SQLException ex){
				System.err.println(ex.getMessage());
				return false;
			}
			System.out.println(result);

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
		ArrayList<String> pallets = new ArrayList<String>();
		String query = "UPDATE Pallet " +
    		"set is_blocked = CASE " + 
    		"WHEN cookie_name = '" + cookie_name + "' " + 
    		"AND location IS NOT 'Delivered' " + 
    		"AND production_date BETWEEN '" + from_date + "' AND '" + to_date + "' " + 
    		"THEN 1 " +
    		"ELSE is_blocked END";
		try{
			int nbrUpdatedLines = sendPutQuery(query);
			System.out.print("blocked");
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
		ArrayList<String> pallets = new ArrayList<String>();
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
     * Tracks a pallet and resturns a Pallet Object
	 * TODO: remove and replace with methods beneath getPalletLocation and getPalletProdDate
	 *
     */
    public Pallet trackPalletObject(String pallet_id){
        Pallet p;
        String query = "SELECT * " +
                "FROM PALLET " +
                "WHERE pallet_id = " + pallet_id;
        try{
            ResultSet rs = sendGetQuery(query);
            p = new Pallet(rs);
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
            p = null;
        }
        return p;
    }

	/**
	 *
	 * @param pallet_id
	 * @return
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
	 * @param pallet_id
	 * @return
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
	 * @param pallet_id
	 * @return
	 */
	public String getPalletProdDate(String pallet_id){
		return null;
	}

	/**
	 * Returns location of a specific pallet with pallet id pallet_id
	 * @param pallet_id
	 * @return
	 */
	public String getPalletLocation(String pallet_id){
		return null;
	}

	public String getPalletBlocked(String pallet_id){
		return null;
	}

	public String getPalletCustomer(String pallet_id){
		return null;
	}

	public String getPalletOrder(String pallet_id){
		return null;
	}

	public String getPalletDelivery(String pallet_id){
		return null;
	}


	/**
	 * Returns a list of pallets that were produced in a time interval.
	 * TODO:
	 * @param from_date start date of interval
	 * @param to_date end date of interval
	 * @return list of pallets
	 */
	public String[] getPallets(String from_date, String to_date, String cookie_name) {
		ArrayList<String> pallets = new ArrayList<String>();
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
			pallets = null;
		}
		return pallets.toArray((new String[pallets.size()]));
	}

}
