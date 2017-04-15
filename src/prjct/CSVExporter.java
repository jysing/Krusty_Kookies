package src.prjct;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * A class for generating a csv-file containing loading orders (i.e. orders
 * that have been loaded to a truck and delivered to a customer). The loading
 * order (csv-file) has for columns; customer, address, cookie type and
 * number of pallets.
 */
class CSVExporter {

	/**
	 * A list containing loading orders.
	 */
	private LinkedList<String[]> loadingOrders;
	
	/**
	 * The number of columns in the csv-file.
	 */
	private static final int NBR_COLUMNS = 4;
	
	/**
	 * 
	 * @param loadingOrders
	 * @param saveLocation
	 */
	CSVExporter(LinkedList<String[]> loadingOrders, String saveLocation) {
		this.loadingOrders = loadingOrders;
		generateCsvFile(saveLocation);
	}
	
	/**
	 * Generates a csv-file.
	 */
	private void generateCsvFile(String sFileName) {
		String sep = ";";
		
		try {
			FileWriter writer = new FileWriter(sFileName);

			writer.append("Customer Name");
			writer.append(sep);
			writer.append("Address");
			writer.append(sep);
			writer.append("Product");
			writer.append(sep);
			writer.append("No of pallets");
			writer.append("\n");
			for (String[] s: loadingOrders) {
				for(int j = 0; j < NBR_COLUMNS; j++) {
					String item = s[j];
					writer.append(item);
					writer.append(sep);
				}
				writer.append("\n");
			}
			writer.flush();
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		} 
	}
}
