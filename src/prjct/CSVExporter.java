package src.prjct;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class CSVExporter {

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
	 * @param loadingOrders, saveLocation
	 */
	public CSVExporter(LinkedList<String[]> loadingOrders, String saveLocation) {
		this.loadingOrders = loadingOrders;
		generateCsvFile(saveLocation);
	}
	
	/**
	 * Generates a csv-file.
	 */
	public void generateCsvFile(String sFileName) {
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

			for(int i = 0; i < loadingOrders.size(); i++) {
				String[] row = loadingOrders.get(i);

				for(int j = 0; j < NBR_COLUMNS; j++) {
					String item = row[j];
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
