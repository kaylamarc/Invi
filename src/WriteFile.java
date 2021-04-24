import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import java.io.IOException;

// deals with the writing and saving of text file
public class WriteFile {

	private String filePath;

	// assign values for document input
	private static String companyName = DatabaseManager.getInstance().getMyCompanyName();
	private static String companyAddress = DatabaseManager.getInstance().getMyAddress();
	private static String companyPhoneNum = DatabaseManager.getInstance().getMyPhone();
	
	
	public static void writeTextFile(JTable table, Object invNum) {
		
		// components for invoice
		 String date = DatabaseManager.getInstance().getInvDate(invNum);
		 String customerCompany = DatabaseManager.getInstance().getCustCompany(invNum);
		 String customerName = DatabaseManager.getInstance().getInvCustomer(invNum);
		 String customerPhone = DatabaseManager.getInstance().getCustPhone(invNum);
		 String customerAddress = DatabaseManager.getInstance().getCustAddress(invNum);
		 double amtDue = DatabaseManager.getInstance().getAmtDue(invNum);
		 double total = Invi.calcTotal(table);
		 

		// gets the home of users computer regardless of OS
		String home = System.getProperty("user.home");

		String invNumStr = String.valueOf(invNum);
		String fileName = "Invoice" + invNumStr;

		try {
			
			// directory
			File folderFile = new File(home + "/Desktop/Invoices");

			// file path
			File txtFile = new File(home + "/Desktop/Invoices/" + fileName + ".txt");
			
			boolean success = folderFile.mkdirs();
			
			// if the file not exist create one
			if (!txtFile.exists()) {
				txtFile.createNewFile();
			}

			FileWriter fw = new FileWriter(txtFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
		
			// invoice header
			bw.write(companyName + "\t\t\tINVOICE\n" + companyAddress + "\t\t\tInvoice#: " + invNum + "\n"
					+ companyPhoneNum + "\t\t\tDate: " + date + "\n\n");
			
			// bill to info
			bw.write("Bill To: \n" + customerCompany + "\n" + customerAddress + "\n" + customerPhone + "\n\n");
			
			// invoice column names
			bw.write("|\tProdID\t|\tProdName\t|\tQty\t|\tUnit$\t|\tSubt\t|");
			bw.write("\n-----------------------------------------------------------------------------------"
					+ "-------\n");
			
			// loop for jtable rows
			for (int i = 0; i < table.getRowCount(); i++) {
				// loop for jtable column
				bw.write("|\t");
				for (int j = 0; j < table.getColumnCount(); j++) {
					bw.write(table.getModel().getValueAt(i, j) + "\t|\t");
				}
				// break line at the begin
				// break line at the end
				bw.write("\n-----------------------------------------------------------------------------------"
						+ "-------\n");
			}
			
			bw.write("Amount Due: $" + amtDue + "\t\t\t\t\t\t\tTotal: $" + total);
			
			// close BufferedWriter
			bw.close();
			// close FileWriter
			fw.close();
			JOptionPane.showMessageDialog(null, "Data Exported");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
}
