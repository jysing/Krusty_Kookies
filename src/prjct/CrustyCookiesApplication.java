package prjct;

import app.Database;

/**
 * CrustyCookiesApplication is the main class for the Crusty Cookies
 * application. It creates a database object and the GUI to
 * interface to the database.
 */
public class CrustyCookiesApplication {

	/**
	 * Execute using:
	 *
	 * java -cp ".;sqlite-jdbc.jar" tblseat/TableSeatingHelper
	 */
	public static void main(String[] args) {
		Database db = new Database();
	}
}