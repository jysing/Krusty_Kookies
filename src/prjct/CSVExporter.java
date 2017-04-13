package src.prjct;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class CSVExporter {

	private LinkedList<String[]> loadingOrders;
	private static final int NBR_COLUMNS = 4;
	
	/*
	 * Loading Order: Exportera lista över pallar och vart de ska till föraren i form av:
	 * 
	 * Customer name
	 * adress
	 * nbr of pallets of each product 
	 */

	public CSVExporter(LinkedList<String[]> loadingOrders, String saveLocation) {
		this.loadingOrders = loadingOrders;
		generateCsvFile(saveLocation);
	}
	
	//\Users\Kewin\Desktop\Bord_C.csv
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
