
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.*;

// manages all database actions
public class DatabaseManager {
	
	private static DatabaseManager instance;

	private Connection conn;

	// connects to the database
	public DatabaseManager() {
		instance = this;

		try {
			// db parameters
			String url = "jdbc:sqlite:InviBase.db";
			// create a connection to the database
			conn = DriverManager.getConnection(url);

			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// keeps connection open
	public static DatabaseManager getInstance() {
		return instance;
	}

	// displays inventory table
	public void displayInventory(JTable inventoryTbl, DefaultTableModel tMod) {
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects ProductId, ProductName, AmtInStock from Inventory table in database
			String selectStmt = "SELECT * FROM Inventory;";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);
			ResultSetMetaData rsmd = res.getMetaData();
			int colNum = rsmd.getColumnCount();
			while (res.next()) {
				Object[] objects = new Object[colNum];
				for (int i = 0; i < colNum; i++) {
					objects[i] = res.getObject(i + 1);
				}
				tMod.addRow(objects);
			}
			inventoryTbl.setModel(tMod);
			;
		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
		}
	}

	// displays prices table
	public void displayPrices(JTable pricesTbl, DefaultTableModel tMod) {

		// populate prices table
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects ProductId, ProductName, Price from Inventory table in database
			String selectStmt = "SELECT ProductID, ProductName, Price FROM Inventory;";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);
			ResultSetMetaData rsmd = res.getMetaData();
			int colNum = rsmd.getColumnCount();
			while (res.next()) {
				Object[] objects = new Object[colNum];
				for (int i = 0; i < colNum; i++) {
					objects[i] = res.getObject(i + 1);
				}
				tMod.addRow(objects);

			}
			pricesTbl.setModel(tMod);
		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}

	}

	public void displayShipping(JTable custTbl, DefaultTableModel tMod, String custSelection) {

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects customer info from database with the selected name from combo box
			String selectStmt = "SELECT CustomerName, Address, Company FROM Customers WHERE CustomerName = '"
					+ custSelection + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);
			ResultSetMetaData rsmd = res.getMetaData();
			int colNum = rsmd.getColumnCount();
			tMod.setRowCount(0); // make table model with only one row
			while (res.next()) {
				Object[] objects = new Object[colNum];
				for (int i = 0; i < colNum; i++) {
					objects[i] = res.getObject(i + 1);
				}
				tMod.addRow(objects);
			}
			custTbl.setModel(tMod);

		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}

	}

	// displays credit card table
	public void displayCreditCard(JTable custTbl, DefaultTableModel tMod, String custSelection) {

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects customer info from database with the selected name from combo box
			String selectStmt = "SELECT * FROM Customers WHERE CustomerName = '" + custSelection + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);
			ResultSetMetaData rsmd = res.getMetaData();
			int colNum = rsmd.getColumnCount();
			tMod.setRowCount(0); // make table model with only one row
			while (res.next()) {
				Object[] objects = new Object[colNum];
				for (int i = 0; i < colNum; i++) {
					objects[i] = res.getObject(i + 1);
				}
				tMod.addRow(objects);
			}
			custTbl.setModel(tMod);

		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}

	}

	// displays customer table with database data
	public void displayCustomers(JTable custInfoTbl, DefaultTableModel tMod) {

		// populate customer info table
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects customers from table in database
			String selectStmt = "SELECT * FROM Customers;";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);
			ResultSetMetaData rsmd = res.getMetaData();
			int colNum = rsmd.getColumnCount();
			while (res.next()) {
				Object[] objects = new Object[colNum];
				for (int i = 0; i < colNum; i++) {
					objects[i] = res.getObject(i + 1);
				}
				tMod.addRow(objects);
			}
			custInfoTbl.setModel(tMod);
			;
		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}
	}

	// displays saved invoices table with database data
	public void displayInvoices(JTable invoicesTbl, DefaultTableModel tMod) {

		// populate invoice table
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects invoices from table in database
			String selectStmt = "SELECT * FROM Invoice;";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);
			ResultSetMetaData rsmd = res.getMetaData();
			int colNum = rsmd.getColumnCount();
			while (res.next()) {
				Object[] objects = new Object[colNum];
				for (int i = 0; i < colNum; i++) {
					objects[i] = res.getObject(i + 1);
				}
				tMod.addRow(objects);
			}
			invoicesTbl.setModel(tMod);
			;
		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}
	}

	// populates customer selection combo box with customers from database
	public void populateCustCombo(JComboBox selectCustCombo) {
		// populate customer combo box with customers from customers table
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select CustomerName FROM Customers;"); // select statement
			while (rs.next()) {
				String name = rs.getString("CustomerName");
				
				// does not allow duplicate combobox items
				// when refreshing adds only the new items
				if (name.equals("")) {
					selectCustCombo.addItem("");
					selectCustCombo.setVisible(false);
				} else {
					String id = rs.getString("CustomerName");
					boolean alreadyThere = false;
					for(int i = 0; i < selectCustCombo.getItemCount(); i++) {
						if(id.equals((String)selectCustCombo.getItemAt(i))) {
							alreadyThere = true;
							break;
						}
					}
					if(!alreadyThere) {
						selectCustCombo.addItem(rs.getString("CustomerName"));
					}
					selectCustCombo.setVisible(true);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
	}

	// populate invoice # combobox
	public void populateInvCombo(JComboBox selectInvCombo) {
		// populate invoice# combo box with invoice #s from invoice table
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select InvoiceID FROM Invoice;"); // select statement
			while (rs.next()) {
				String name = rs.getString("InvoiceID");
				
				// does not allow duplicate combobox items
				// when refreshing adds only the new items
				if (name.equals("")) {
					selectInvCombo.addItem("");
					selectInvCombo.setVisible(false);
				} else {
					String id = rs.getString("InvoiceID");
					boolean alreadyThere = false;
					for(int i = 0; i < selectInvCombo.getItemCount(); i++) {
						if(id.equals((String)selectInvCombo.getItemAt(i))) {
							alreadyThere = true;
							break;
						}
					}
					if(!alreadyThere) {
						selectInvCombo.addItem(rs.getString("InvoiceID"));
					}
					selectInvCombo.setVisible(true);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
	}
	

	// populates customer selection combo box with customers from database
	public void populateProdIDCombo(JComboBox prodIDCombo) {
		// populate customer combo box with customers from customers table
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select ProductID FROM Inventory;"); // select statement
			while (rs.next()) {
				String name = rs.getString("ProductID");
				
				// does not allow duplicate combobox items
				// when refreshing adds only the new items
				if (name.equals("")) {
					prodIDCombo.addItem("");
					prodIDCombo.setVisible(false);
				} else {
					String id = rs.getString("ProductID");
					boolean alreadyThere = false;
					for(int i = 0; i < prodIDCombo.getItemCount(); i++) {
						if(id.equals((String)prodIDCombo.getItemAt(i))) {
							alreadyThere = true;
							break;
						}
					}
					if(!alreadyThere) {
						prodIDCombo.addItem(rs.getString("ProductID"));
					}
					prodIDCombo.setVisible(true);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
	}

	// add a customer to database and table
	public void addCustomer(DefaultTableModel tMod, Object name, Object address, Object email, Object phone,
			Object company) {

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// remove statement
			String addStmt = "INSERT INTO Customers (CustomerName, Address, Email, PhoneNum, Company) VALUES ('" + name
					+ "', '" + address + "', '" + email + "', '" + phone + "', '" + company + "');";

			// executes the add statement
			stmt.execute(addStmt);

			// row data to be added to the table model
			Object[] row = { name, address, email, phone, company };

			// adds the row to table model
			tMod.addRow(row);

		} catch (SQLException ex) {
			System.out.println(ex); // print the error
		}
	}

	// remove a customer from database and table
	public void removeCustomer(JTable table, DefaultTableModel tMod) {

		// if row is selected, delete, if not notify to select row
		if (table.getSelectedRow() != -1) {
			int row = table.getSelectedRow();
			int col = 0; // productID is first column
			Object id = table.getValueAt(row, col); // gets the ID from selected row

			try {
				// prepares to execute query on table
				Statement stmt = conn.createStatement();

				// remove statement
				String removeStmt = "DELETE FROM Customers WHERE CustomerName = '" + id + "';";

				// executes the remove statement
				stmt.execute(removeStmt);

				// removes the row from the table model
				tMod.removeRow(row);

			} catch (SQLException ex) {
				System.out.println(ex); // print the error
				ex.printStackTrace();
			}

		} else {
			// pops up a message dialog to prompt a row selection
			JFrame messageFrame = new JFrame("Error");
			JOptionPane.showMessageDialog(messageFrame, "There is no product row selected.\nPlease select a row.",
					"Error", JOptionPane.WARNING_MESSAGE);
		}

	}

	// add product to inventory able
	public void addProduct(DefaultTableModel tMod, Object id, Object name, Object amt, Object price) {

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// remove statement
			String addStmt = "INSERT INTO Inventory (ProductID, ProductName, AmtInStock, Price) VALUES ('" + id + "', '"
					+ name + "', '" + amt + "', '" + price + "');";

			// executes the add statement
			stmt.execute(addStmt);

			// row data to be added to the table model
			Object[] row = { id, name, amt, price };

			// adds the row to table model
			tMod.addRow(row);

		} catch (SQLException ex) {
			System.out.println(ex); // print the error
		}

	}

	// remove product from inventory table
	public void removeProduct(JTable table, DefaultTableModel tMod) {

		// if row is selected, delete, if not notify to select row
		if (table.getSelectedRow() != -1) {
			int row = table.getSelectedRow();
			int col = 0; // productID is first column
			Object id = table.getValueAt(row, col); // gets the ID from selected row

			try {
				// prepares to execute query on table
				Statement stmt = conn.createStatement();

				// remove statement
				String removeStmt = "DELETE FROM Inventory WHERE ProductID = '" + id + "';";

				// executes the remove statement
				stmt.execute(removeStmt);

				// removes the row from the table model
				tMod.removeRow(row);

			} catch (SQLException ex) {
				System.out.println(ex); // print the error
			}

		} else {
			// pops up a message dialog to prompt a row selection
			JFrame messageFrame = new JFrame("Error");
			JOptionPane.showMessageDialog(messageFrame, "There is no product row selected.\nPlease select a row.",
					"Error", JOptionPane.WARNING_MESSAGE);
		}

	}

	// remove product from InvoiceContents table
		public void removeProductInvoice(JTable table, DefaultTableModel tMod, Object invID) {

			// if row is selected, delete, if not notify to select row
			if (table.getSelectedRow() != -1) {
				int row = table.getSelectedRow();
				int col = 0; // productID is first column
				Object prodID = table.getValueAt(row, col); // gets the ID from selected row
				

				try {
					// prepares to execute query on table
					Statement stmt = conn.createStatement();

					// remove statement
					String removeStmt = "DELETE FROM InvoiceContents WHERE (ProductID = '" + prodID + "')"
							+ " AND (InvoiceID = ' " + invID + "');";

					// executes the remove statement
					stmt.execute(removeStmt);

					// removes the row from the table model
					tMod.removeRow(row);

				} catch (SQLException ex) {
					System.out.println(ex); // print the error
				}
				
			} else {
				// pops up a message dialog to prompt a row selection
				JFrame messageFrame = new JFrame("Error");
				JOptionPane.showMessageDialog(messageFrame, "There is no product row selected.\nPlease select a row.",
						"Error", JOptionPane.WARNING_MESSAGE);
			}

		}

	
	
	// update the database with edited cell
	public void updateInventoryData(DefaultTableModel tMod, int intRows, int intColumn)

	{
		// gets the updated info
		Object prodID = tMod.getValueAt(intRows, 0);
		Object prodName = tMod.getValueAt(intRows, 1);
		Object amtInStock = tMod.getValueAt(intRows, 2);

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// update statement
			String updateStmt = "UPDATE Inventory " + "SET ProductID = '" + prodID + "' " + ", ProductName = '"
					+ prodName + "' " + ", AmtInStock = '" + amtInStock + "' " + " WHERE ProductID = '" + prodID + "';";

			// executes the update statement
			stmt.execute(updateStmt);

			checkInventoryLevel(); // check inventory level after update

		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

	}

	// update price data when table is edited
	public void updatePriceData(DefaultTableModel tMod, int intRows, int intColumn) {

		// gets the updated info
		Object prodID = tMod.getValueAt(intRows, 0);
		Object prodName = tMod.getValueAt(intRows, 1);
		Object price = tMod.getValueAt(intRows, 2);

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// update statement
			String updateStmt = "UPDATE Inventory " + "SET ProductID = '" + prodID + "' " + ", ProductName = '"
					+ prodName + "' " + ", Price = '" + price + "' " + " WHERE ProductID = '" + prodID + "';";

			// executes the update statement
			stmt.execute(updateStmt);
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

	}
	
	// update customer data when table is edited
	public void updateCustomerData(DefaultTableModel tMod, int intRows, int intColumn) {
		// gets the updated info
		Object custName = tMod.getValueAt(intRows, 0);
		Object address = tMod.getValueAt(intRows, 1);
		Object email = tMod.getValueAt(intRows, 2);
		Object phone = tMod.getValueAt(intRows, 3);
		Object company = tMod.getValueAt(intRows, 4);

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// update statement
			String updateStmt = "UPDATE Customers " + "SET CustomerName = '" + custName + "' " + ", Address = '"
					+ address + "' " + ", Email = '" + email + "', " + "PhoneNum = '" + phone +"', Company = '" +
					company + "' WHERE CustomerName = '" + custName + "';";

			// executes the update statement
			stmt.execute(updateStmt);
			
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}
	}
	
	// calculates the subtotal
	public double calculateSubTotal(Object invID, Object prodID) {
		
		double subtotal = 0;
		double unitPrice = 0;
		double qty = 0;
		
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT UnitPrice, Quantity FROM InvoiceContents WHERE (ProductID = '" + prodID + "')"
					+ " AND (InvoiceID = '" + invID + "');";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				unitPrice = res.getDouble("UnitPrice");
				qty = res.getInt("Quantity");
			}
			
			subtotal = unitPrice * qty;
			
			
			
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}
		
		return subtotal;
	}
	

	// determines if invoice is paid
	public boolean isPaid(Object invNum) {

		// returns true if the invoice is paid
		if (getAmtDue(invNum) == 0) {
			return true;
		} else {
			return false;
		}

	}
	
	// gets amt paid from invoice
	public double getAmtPaid(Object invNum) {

		double amtPaid = 0;
		
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT AmtPaid FROM Invoice WHERE InvoiceID = '" + invNum + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				amtPaid = res.getDouble("AmtPaid");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}
		
		return amtPaid;
	}
	

	// gets remaining amount due for invoice
	public double getAmtDue(Object invNum) {
		// set variables to be assigned with data
		double total = 0;
		double amtPaid = 0;
		double amtDue = 0;

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT * FROM Invoice WHERE InvoiceID = '" + invNum + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				total = res.getDouble("Total");
				amtPaid = res.getDouble("AmtPaid");
				amtDue = total - amtPaid; // calculate amount due
			}

		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}
		return amtDue;
	}
	
	// displays unpaid invoices
	public void displayUnpaidInvoices(JTable unpaidTbl, DefaultTableModel tMod) {

		Object invNum;

		// populate unpaid invoice table
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects invoices from table in database
			String selectStmt = "SELECT * FROM Invoice;";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);
			ResultSetMetaData rsmd = res.getMetaData();
			int colNum = rsmd.getColumnCount();
			while (res.next()) {
				Object[] objects = new Object[colNum];
				invNum = res.getObject("InvoiceID");
				for (int i = 0; i < colNum; i++) {
					objects[i] = res.getObject(i + 1);
				}
				if (isPaid(invNum) == false) {
					tMod.addRow(objects);
				}
			}
			unpaidTbl.setModel(tMod);
			;
		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}

	}

	// updates credit card info
	public void updateCredit(String selectedCCType, String custSelection) {
		try {
			// open database connection again
			Connection conn = null;
			try {
				// db parameters
				String url = "jdbc:sqlite:InviBase.db";
				// create a connection to the database
				conn = DriverManager.getConnection(url);

				System.out.println("Connection to SQLite has been established.");

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// sets credit card type of certain customer
			String insertStmt = "UPDATE CreditCard SET CCType = '" + selectedCCType + "' WHERE CustomerName = '"
					+ custSelection + "';";

			// executes the insert statement
			stmt.execute(insertStmt);

		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}
	}


	// add contents into the InvoiceContent table
	public void addInvContent(JTable newInvTbl, DefaultTableModel tMod, Object prodID, Object invNum, Object qty,
			Object unitPrice) {
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// remove statement
			String addStmt = "INSERT INTO InvoiceContents (ProductID, InvoiceID, Quantity, UnitPrice) VALUES ('"
					+ prodID + "', '" + invNum + "', '" + qty + "', '" + unitPrice + "');";

			// executes the add statement
			stmt.execute(addStmt);

			Object prodName;

			String slct = "SELECT ProductName FROM Inventory WHERE ProductID = " + prodID;
			// executes the add statement
			ResultSet res = stmt.executeQuery(slct);
			while (res.next()) {
				prodName = res.getObject("ProductName");
				// row data to be added to the table model
				Object[] row = { prodID, prodName, qty, unitPrice, calculateSubTotal(invNum, prodID)};

				// adds the row to table model
				tMod.addRow(row);

			}

			newInvTbl.setModel(tMod);

		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

	}

	// removes an invoice content (data) row from InvoiceContent table
	public void removeInvContent(JTable newInvTbl, DefaultTableModel tMod) {
		// if row is selected, delete, if not notify to select row
		if (newInvTbl.getSelectedRow() != -1) {
			int row = newInvTbl.getSelectedRow();
			int col = 0; // productID is first column
			Object id = newInvTbl.getValueAt(row, col); // gets the ID from selected row

			try {
				// prepares to execute query on table
				Statement stmt = conn.createStatement();

				// remove statement
				String removeStmt = "DELETE FROM InvoiceContents WHERE ProductID = '" + id + "';";

				// executes the remove statement
				stmt.execute(removeStmt);

				// removes the row from the table model
				tMod.removeRow(row);

			} catch (SQLException ex) {
				System.out.println(ex); // print the error
			}

		} else {
			// pops up a message dialog to prompt a row selection
			JFrame messageFrame = new JFrame("Error");
			JOptionPane.showMessageDialog(messageFrame, "There is no product row selected.\nPlease select a row.",
					"Error", JOptionPane.WARNING_MESSAGE);
		}

	}

	// adds invoice to Invoice table in database
	public void addInvoice(Object invNum, Object custName, Object date, Object total, Object amtPaid) {

		// insert
		String insrt = "INSERT INTO Invoice (InvoiceID, CustomerName, Date, Total, AmtPaid) VALUES " + " ('" + invNum
				+ "', '" + custName + "', '" + date + "', '" + total + "', '" + amtPaid + "');";
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// executes the insert statement
			stmt.execute(insrt);

		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}

	}

	// displays notifications
	public void notif() {
		
		// create string message for empty products
		String emptyNotification = getEmptyProducts();
		
		// create string message for low inventory products
		String lowInvNotification = checkInventoryLevel();
		
		// create string of both empty and low inventory products
		String notification = getEmptyProducts() + "\n" + checkInventoryLevel();
		
		JPanel msgPnl = new JPanel(); // panel for option dialog components
		
		// display notification if both or one or other has low/empty inventory products 
		if(!(emptyNotification.equals("")) && !(lowInvNotification.equals(""))) {
			JOptionPane.showMessageDialog(msgPnl, notification, "Inventory Notification", JOptionPane.WARNING_MESSAGE);
		}
		else if(!(emptyNotification.equals("")) && lowInvNotification.equals("")) {
			JOptionPane.showMessageDialog(msgPnl, emptyNotification, "Inventory Notification", JOptionPane.WARNING_MESSAGE);
		}
		else if(!(lowInvNotification.equals("")) && emptyNotification.equals("")) {
			JOptionPane.showMessageDialog(msgPnl, lowInvNotification, "Inventory Notification", JOptionPane.WARNING_MESSAGE);
		}
		else {
			// don't display if there is no empty or low inventory
		}
		
	}
	
	
	
	// determines which products are low on inventory
	public String checkInventoryLevel() {

		// list of low stock products
		ArrayList<Object> prods = new ArrayList<Object>();
		
		String msg = "";

		// get low stock products
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects products with low inventory from table in database
			String selectStmt = "SELECT ProductID FROM Inventory WHERE AmtInStock <= 14;";
			
			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);
			ResultSetMetaData rsmd = res.getMetaData();
			int colNum = rsmd.getColumnCount();
			int i = 0;
			while (res.next()) {
				prods.add(i, res.getObject("ProductID"));
				i++;

			}

			String message = "Low inventory on these items: " + prods;
			
		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}
		
		// return a complete message if the list is not empty
		if (!(prods.isEmpty())) {
			msg = "Empty inventory on these items: " + prods;
		} else {
			msg = "";
		}
		
		// returns empty string if there are no products with low inventory
		return msg;
	}
	
	// gets the products with empty inventory
	public String getEmptyProducts() {
		
		// list of low stock products
		ArrayList<Object> prods = new ArrayList<Object>();
		
		String msg = "";

		// get low stock products
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects products with empty inventory from table in database
			String selectStmt = "SELECT ProductID FROM Inventory WHERE AmtInStock = 0;";
			
			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);
			ResultSetMetaData rsmd = res.getMetaData();
			int colNum = rsmd.getColumnCount();
			int i = 0;
			while (res.next()) {
				prods.add(i, res.getObject("ProductID"));
				i++;

			}

			
		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}
		
		// return a complete message if the list is not empty
		if(!(prods.isEmpty())) {
			msg =  "Empty inventory on these items: " + prods;
		}
		else {
			msg = "";
		}
		
		// returns empty string if there are no products with low inventory
		return msg;
		
	}
	
	// displays the invpoce table with contents
	public void displayInvoiceTable(DefaultTableModel tMod, JTable table, Object invNum) {
		// populate table of invoice
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			Object prodID;
			int qty;
			double unitPrice;
			double subtotal = 0;
			Object prodName;

			String select = "SELECT * FROM InvoiceContents WHERE InvoiceID = '" + invNum + "';";

			ResultSet res1 = stmt.executeQuery(select);
			while (res1.next()) {
				prodID = res1.getObject("ProductID");
				qty = res1.getInt("Quantity");
				unitPrice = res1.getDouble("UnitPrice");

				// gets the subtotal
				subtotal = calculateSubTotal(invNum, prodID);

				prodName = getProdName(prodID); // gets the product name

				Object[] row = { prodID, prodName, qty, unitPrice, subtotal };
				tMod.addRow(row);
			}
			table.setModel(tMod);

		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}
	}
	
	
	
	// adds notification to notification table
	public void addNotification(DefaultListModel<String> lMod) {

		lMod.addElement(getEmptyProducts()); // add products with empty inventory
		lMod.addElement(checkInventoryLevel());	// adds products with low inventory

	}


	// check if enough in inventory to sell
	public boolean checkInventory(Object prodNum, int quantity) {
		boolean enough = false; // represents if there is enough in inventory
		JFrame messageFrame = new JFrame(); // frame for warning message
		// get amount in stock
				try {
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery("SELECT AmtInStock FROM Inventory WHERE ProductID = '" + prodNum + "';"); // select statement
					while (rs.next()) {
						int amtInStock = rs.getInt("AmtInStock");
						if(amtInStock < quantity) {
							enough = false;
							JOptionPane.showMessageDialog(messageFrame, "There is not enough in stock to buy this amount of"
									+ " items.\nAmount in stock:" + amtInStock,
									"Error", JOptionPane.WARNING_MESSAGE);
						}
						else if(amtInStock == quantity) {
							// there is enough in stock but inventory will be empty
							JOptionPane.showMessageDialog(messageFrame, "emptied inventory on product: " + prodNum
									+ "\nPlease restock.",
									"Error", JOptionPane.WARNING_MESSAGE);
							enough = true;
						}
						else {
							// there is enough in stock
							enough = true;
						}
					}
				} catch (Exception ex) {
					System.out.println(ex);
					ex.printStackTrace();
				}
				return enough;
	}
	
	// stores the users info into the database
	public void storeMyInfo(Object fName, Object lName, Object businName, Object phone, Object address, Object email) {
		
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			String insrt = "INSERT INTO CompanyInfo (CompanyName, PhoneNum, FName, LName, Address, Email) VALUES " + " ('" + businName
					+ "', '" + phone + "', '" + fName + "', '" + lName + "', '" + address + "', '" + email + "');";
			
			stmt.execute(insrt);
			
			
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}
		
	}
	
	// gets and returns price of product
	public double getProdPrice(Object prodID) {
		
		double price = 0;

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT Price FROM Inventory WHERE ProductID = '" + prodID + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				price = res.getDouble("Price");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

		return price;
	}
	
	// gets and returns name of product
		public String getProdName(Object prodID) {
			
			String name = "";

			try {
				// prepares to execute query on table
				Statement stmt = conn.createStatement();

				// selects data from table in database
				String selectStmt = "SELECT ProductName FROM Inventory WHERE ProductID = '" + prodID + "';";

				// populates table with result set data from select query
				ResultSet res = stmt.executeQuery(selectStmt);

				// assigns variables to selected database data
				while (res.next()) {
					name = res.getString("ProductName");
				}
			} catch (SQLException ex) {
				System.out.println(ex); // print the error
				ex.printStackTrace();
			}

			return name;
		}
	
	
	// returns the users company name
	public String getMyCompanyName() {
		
		String companyName = "";
		
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT CompanyName FROM CompanyInfo;";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				companyName = res.getString("CompanyName");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}
		
		return companyName;
		
	}
	
	// returns the users company address
	public String getMyAddress() {

		String address = "";
		
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT Address FROM CompanyInfo;";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				address = res.getString("Address");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}
		
		return address;
	}
	
	// returns the users company phone number
	public String getMyPhone() {

		String phone = "";

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT PhoneNum FROM CompanyInfo;";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				phone = res.getString("PhoneNum");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

		return phone;
	}
	
	// returns the invoices date
	public String getInvDate(Object invNum) {
		
		String date = "";

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT Date FROM Invoice WHERE InvoiceID = '" + invNum + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				date = res.getString("Date");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

		return date;
		
	}
	
	// returns the customers name from the invoice
	public String getInvCustomer(Object invNum) {
		
		String customer = "";

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT CustomerName FROM Invoice WHERE InvoiceID = '" + invNum + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				customer = res.getString("CustomerName");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

		return customer;
	}
	
	// returns the customers company (based off invoice number)
	public String getCustCompany(Object invNum) {
		
		String company = "";
		String customerName = "";

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt1 = "SELECT Company FROM Customers WHERE CustomerName = '" + getInvCustomer(invNum) + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt1);

			// assigns variables to selected database data
			while (res.next()) {
				company = res.getString("Company");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

		return company;
		
	}

	// returns the customers company (based off name)
	public String getCustCompany(String custName) {

		String company = "";
		String customerName = "";

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt1 = "SELECT Company FROM Customers WHERE CustomerName = '" + custName + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt1);

			// assigns variables to selected database data
			while (res.next()) {
				company = res.getString("Company");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

		return company;

	}
	
	// returns the customers phone number from invoice
	public String getCustPhone(Object invNum) {
		
		String phone = "";

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT PhoneNum FROM Customers WHERE CustomerName = '" + getInvCustomer(invNum) + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				phone = res.getString("PhoneNum");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

		return phone;
	}
	
	// returns the customers phone number from invoice (input name)
	public String getCustPhone(String custName) {

		String phone = "";

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT PhoneNum FROM Customers WHERE CustomerName = '" + custName + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				phone = res.getString("PhoneNum");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

		return phone;
	}
	
	// returns the customers address based off invoice number
	public String getCustAddress(Object invNum) {

		String address = "";

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT Address FROM Customers WHERE CustomerName = '" + getInvCustomer(invNum) + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				address = res.getString("Address");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

		return address;
	}
	
	// returns the customers address based off customer name
	public String getCustAddress(String custName) {

		String address = "";

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT Address FROM Customers WHERE CustomerName = '" + custName + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				address = res.getString("Address");
			}
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}

		return address;
	}
	
	// returns the customers address based off customer name
		public String getCustEmail(String custName) {

			String email = "";

			try {
				// prepares to execute query on table
				Statement stmt = conn.createStatement();

				// selects data from table in database
				String selectStmt = "SELECT Email FROM Customers WHERE CustomerName = '" + custName + "';";

				// populates table with result set data from select query
				ResultSet res = stmt.executeQuery(selectStmt);

				// assigns variables to selected database data
				while (res.next()) {
					email = res.getString("Email");
				}
			} catch (SQLException ex) {
				System.out.println(ex); // print the error
				ex.printStackTrace();
			}

			return email;
		}
		
	
	// returns the amount in stock of the product
	public int getAmtInStock(Object prodNum) {
		
		int quantity = 0;
		
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String selectStmt = "SELECT AmtInStock FROM Inventory WHERE ProductID = '" + prodNum + "';";

			// populates table with result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				quantity = res.getInt("AmtInStock");
			}

		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}
		
		return quantity;
	}
	
	// updates the quantity in the database
	public void setQuantity(Object prodNum, int amt) {
	
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String updateStmt = "UPDATE Inventory SET AmtInStock = '" + amt + "'  WHERE ProductID = '" + prodNum + "';";

			// executes update
			stmt.execute(updateStmt);

		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}
		
		
	}
	
	// update the total in invoice
	public void updateTotal(Object invNum, double total) {
		
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			// selects data from table in database
			String updateStmt = "UPDATE Invoice SET Total = '" + total + "'  WHERE InvoiceID = '" + invNum + "';";

			// executes update
			stmt.execute(updateStmt);

		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}
		
	}
	
	// sets the amount paid in the invoice
	public void setAmtPaid(Object invNum, double amt) {
		
		double amtPaidBefore = 0;
		double amtPaid = 0;
		
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			String selectStmt = "SELECT AmtPaid FROM Invoice WHERE InvoiceID = '" + invNum + "';";
			
			// result set data from select query
			ResultSet res = stmt.executeQuery(selectStmt);

			// assigns variables to selected database data
			while (res.next()) {
				amtPaidBefore = res.getDouble("AmtPaid");
			}
			
			amtPaid = amtPaidBefore + amt;
			
			// selects data from table in database
			String updateStmt = "UPDATE Invoice SET AmtPaid = '" + amtPaid + "'  WHERE InvoiceID = '" + invNum + "';";

			// executes update
			stmt.execute(updateStmt);

		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}
		
	}
	
	// adds check to CheckInfo table in database
	public void addCheck(Object checkNum, Object checkAmt, Object invNum) {

		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			String insrt = "INSERT INTO CheckInfo (CheckNum, CheckAmt, InvoiceID) VALUES " + " ('" + checkNum
					+ "', '" + checkAmt + "', '" + invNum + "');";
			
			// executes insert statement
			stmt.execute(insrt);
			
			
		} catch (SQLException ex) {
			System.out.println(ex); // print the error
			ex.printStackTrace();
		}
	}
	
	// create 2D array of invoice contents
	public Object[][] getInvoiceArray(Object invNum) {

		Object[][] invCont = {};
		
		// populate table of invoice
		try {
			// prepares to execute query on table
			Statement stmt = conn.createStatement();

			Object prodID;
			int qty;
			double unitPrice;
			double subtotal = 0;
			Object prodName;
			
			int count = 0;

			String select = "SELECT * FROM InvoiceContents WHERE InvoiceID = '" + invNum + "';";
			
			ResultSet res1 = stmt.executeQuery(select);
			while (res1.next()) {
				prodID = res1.getObject("ProductID");
				qty = res1.getInt("Quantity");
				unitPrice = res1.getDouble("UnitPrice");

				// gets the subtotal
				subtotal = calculateSubTotal(invNum, prodID);

				prodName = getProdName(prodID); // gets the product name
				
				// insert data into respected columns
				invCont[count][0] = prodID;
				invCont[count][1] = prodName;
				invCont[count][2] = qty;
				invCont[count][3] = unitPrice;
				invCont[count][4] = subtotal;
				
				count++;
			}
			
		} catch (SQLException ex) {
			System.out.println(ex); // prints SQL exception
			ex.printStackTrace();
		}
		return invCont;
	}
	
	
}
