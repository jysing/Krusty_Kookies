package src.prjct;

import src.app.Database;

/**
 * CrustyCookiesApplication is the main class for the Crusty Cookies
 * application. It creates a database object and the GUI to
 * interface to the database.
 */
public class CrustyCookiesApplication {

	/**
	 * Execute using:
	 * javac serc/ * /*.java
	 * java -cp ".;sqlite-jdbc.jar" src/prjct/CrustyCookiesApplication
	 */
	public static void main(String[] args) {
		Database db = new Database();
		db.openConnection("src/Krusty.db");
		if (db.isConnected()) new CrustyGUI(db);
	 }
}
