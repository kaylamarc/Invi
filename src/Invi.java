
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class Invi {

	// custom color for the theme
	public static Color blueTheme = new Color(153, 204, 255);
	
	// declare the central frame components
	private static JFrame centralFrame;
	private static JPanel contentPanel; 
	private static JPanel newInvoicePanel; 
	private static JPanel savedInvoicesPanel;
	private static JPanel unpaidInvoicesPanel;
	private static JPanel notificationsPanel;
	private static JPanel inventoryPanel;
	private static JPanel editInventoryPanel;
	private static JPanel pricesPanel;
	private static JPanel creditCardPanel;
	private static JPanel customerInfoPanel;
	private static JPanel myInfoPanel;
	private static JPanel shippingInfoPanel;
	private static JPanel billingPanel;
	private static JPanel savePanel;
	
	// declares save button for everything
	private static JButton saveButton;
	
	// declare the cardlayout
	private static CardLayout cardLayout;
	
	// declare title labels for panels
	private static JLabel titleLabel;
	
	// title font for all panels
	public static Font titleFont = new Font("Serif", Font.BOLD, 30);
	private static JFormattedTextField invNumTxtFld;
	private static JFormattedTextField dateTxtFld;
	private static JLabel dateLbl;
	private static JLabel amtPaidLbl;
	private static JLabel amtDueLbl;
	private static JLabel totalLbl;
	private static JScrollPane scrollPane;
	
	// number format only takes in integers
	private static NumberFormat integerFormat = NumberFormat.getIntegerInstance();
	private static NumberFormatter integerFormatter = new NumberFormatter(integerFormat);	
	
	
	private static int count = 0;
   
	
	public static void main(String[] args) {
		new DatabaseManager();
		
		// sets up the integer format upon program run
		setIntegerFormat(integerFormatter, integerFormat);
		
		// create central frame
		centralFrame = new JFrame("Central");
		centralFrame.setBackground(blueTheme); // set background to the blue theme color
		centralFrame.getContentPane().setLayout(new BorderLayout()); // set mainFrame to null layout
		
		// set the frame to the size of the monitor
		centralFrame.setBounds(0, 0, 1440, 900);
		centralFrame.setResizable(false); // does not allow resizing of frame (to not mess up formatting)
		centralFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		// set layout of content pane to cardLayout
		cardLayout = new CardLayout();
		contentPanel = new JPanel(cardLayout);
		contentPanel.setBackground(blueTheme);
		centralFrame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		 // pulls up welcome screen
		Welcome();
		
		// Create all panels (cards), only once
		initNewInvoice();
		initToolBar();
		initSavedInvoices();
		initUnpaidInvoices();
		initNotifications();
		initInventory();
		initEditInventory();
		initPrices();
		initCreditCard();
		initCustomerInfo();
		initMyInfo();
		initShippingInfo();
		initBilling();
		initSave();
		
		// add all panels (cards) to contentPanel
		contentPanel.add(newInvoicePanel, "1");
		contentPanel.add(savedInvoicesPanel, "2");
		contentPanel.add(unpaidInvoicesPanel, "3");
		contentPanel.add(billingPanel, "4");
		contentPanel.add(notificationsPanel, "5");
		contentPanel.add(inventoryPanel, "6");
		contentPanel.add(editInventoryPanel, "7");
		contentPanel.add(pricesPanel, "8");
		contentPanel.add(creditCardPanel, "9");
		contentPanel.add(customerInfoPanel, "10");
		contentPanel.add(myInfoPanel, "11");
		contentPanel.add(shippingInfoPanel, "12");
		contentPanel.add(savePanel, "13");
		
	}
	
	// welcome screen
	public static void Welcome() {
		
		// Create Welcome Screen
		JFrame welcomeFrame = new JFrame("Welcome to Invi");
		welcomeFrame.setBackground(Color.WHITE);
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // (default action) closes when exit button pressed
		welcomeFrame.setBounds(0, 0, 1440, 900); // set frame size
		welcomeFrame.setResizable(false); // does not allow resizing of frame (to not mess up formatting)
		
		// Invi Logo label
		ImageIcon inviLogo = new ImageIcon("src/resources/InviLogo.png");
		JLabel logo = new JLabel(inviLogo); // add logo to label
		
		// fills the x-axis and forces next addition to next line
		logo.setPreferredSize(new Dimension(1440, inviLogo.getIconHeight()));
		
		// Enter button
		JButton enterButton = new JButton("Enter");
		
		// adds action: button clicked, go to central screen
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
			centralFrame.setVisible(true); // show central frame
			welcomeFrame.dispose(); // get rid of welcome screen
			DatabaseManager.getInstance().notif(); // update/display notifications
			}			
		});
		
		// create new panel
		JPanel welcomePanel = new JPanel();
		welcomePanel.setOpaque(true);
		welcomePanel.setBackground(Color.WHITE);
		
		// add welcome elements
		welcomePanel.add(logo);
		welcomePanel.add(enterButton);
		welcomeFrame.getContentPane().add(welcomePanel);
		
		// set welcome screen visible
		welcomeFrame.setVisible(true);
	}
	
	// Creates the tool bar
	public static void initToolBar() {
		
		// create tool bar
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(Color.WHITE);
		toolBar.setBorderPainted(true);
		toolBar.setBounds(0, 0, 1440, 30); 
		toolBar.setFloatable(false); // disable moving of tool bar
		toolBar.setRollover(true); // allow tool tip text when hovering over buttons
		
		// create new Invoice button
		JButton newInvoiceButton = new JButton();
		newInvoiceButton.setBorderPainted(false); // remove border from button
		newInvoiceButton.setIcon(new ImageIcon("src/resources/newIcon.png")); // add image to button
		newInvoiceButton.setToolTipText("Create New Invoice"); // set hover text
		// when button clicked go to create new invoice
		newInvoiceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "1"); // show new invoices panel
			}
		});

		// saved invoices button
		JButton savedInvoicesButton = new JButton();
		savedInvoicesButton.setBorderPainted(false);
		savedInvoicesButton.setIcon(new ImageIcon("src/resources/savedInvoicesIcon.png"));
		savedInvoicesButton.setToolTipText("View Saved Invoices");
		savedInvoicesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "2"); // show saved invoices panel
			}
		});
		
		// unpaid invoices button
		JButton unpaidInvoicesButton = new JButton();
		unpaidInvoicesButton.setBorderPainted(false);
		unpaidInvoicesButton.setIcon(new ImageIcon("src/resources/UnpaidInvIcon.png"));
		unpaidInvoicesButton.setToolTipText("View Unpaid Invoices");
		unpaidInvoicesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "3"); // show unpaid invoices panel
			}
		});
		
		// billing button
		JButton billingButton = new JButton();
		billingButton.setBorderPainted(false);
		billingButton.setIcon(new ImageIcon("src/resources/billingIcon.png"));
		billingButton.setToolTipText("Billing");
		billingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "4"); // show billing panel
			}
		});
		
		// notifications button
		JButton notificationButton = new JButton();
		notificationButton.setBorderPainted(false);
		notificationButton.setIcon(new ImageIcon("src/resources/notifIcon.png"));
		notificationButton.setToolTipText("View Notifications");
		notificationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "5"); // show notifications panel
			}
		});
		
		// inventory button
		JButton inventoryButton = new JButton();
		inventoryButton.setBorderPainted(false);
		inventoryButton.setIcon(new ImageIcon("src/resources/inventoryIcon.png"));
		inventoryButton.setToolTipText("View Inventory");
		inventoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "6"); // show inventory panel
			}
		});
		
		// edit inventory button
		JButton editInventoryButton = new JButton();
		editInventoryButton.setBorderPainted(false);
		editInventoryButton.setIcon(new ImageIcon("src/resources/editInvIcon.png"));
		editInventoryButton.setToolTipText("Edit Inventory");
		editInventoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "7"); // show edit inventory panel
			}
		});
		
		// prices button
		JButton pricesButton = new JButton();
		pricesButton.setBorderPainted(false);
		pricesButton.setIcon(new ImageIcon("src/resources/pricesIcon.png"));
		pricesButton.setToolTipText("Prices");
		pricesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "8"); // show prices panel
			}
		});
		
		// credit card button
		JButton creditCardButton = new JButton();
		creditCardButton.setBorderPainted(false);
		creditCardButton.setIcon(new ImageIcon("src/resources/creditCardIcon.png"));
		creditCardButton.setToolTipText("Credit Card Info");
		creditCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "9"); // show credit card panel
			}
		});
		
		// customer info button
		JButton customerInfoButton = new JButton();
		customerInfoButton.setBorderPainted(false);
		customerInfoButton.setIcon(new ImageIcon("src/resources/customerIcon.png"));
		customerInfoButton.setToolTipText("Customer Info");
		customerInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel,  "10"); // show customer info panel
			}
		});
		
		// my info button
		JButton myInfoButton = new JButton();
		myInfoButton.setBorderPainted(false);
		myInfoButton.setIcon(new ImageIcon("src/resources/myInfoIcon.png"));
		myInfoButton.setToolTipText("My Info");
		myInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "11"); // show my info panel
			}
		});
		
		// shipping info button
		JButton shippingInfoButton = new JButton();
		shippingInfoButton.setBorderPainted(false);
		shippingInfoButton.setIcon(new ImageIcon("src/resources/shippingInfoIcon.png"));
		shippingInfoButton.setToolTipText("Shipping Info");
		shippingInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "12"); // show shipping info panel
			}
		});
		
		// save button
		JButton saveButton = new JButton();
		saveButton.setBorderPainted(false);
		saveButton.setIcon(new ImageIcon("src/resources/saveIcon.png"));
		saveButton.setToolTipText("Save/Download");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "13"); // show save panel
			}
		});
		
		
		// add tool bar buttons with separators
		toolBar.add(newInvoiceButton);
		toolBar.addSeparator(); // add separator between buttons
		toolBar.add(savedInvoicesButton);
		toolBar.addSeparator();
		toolBar.add(unpaidInvoicesButton);
		toolBar.addSeparator();
		toolBar.add(billingButton);
		toolBar.addSeparator();
		toolBar.add(notificationButton);
		toolBar.addSeparator();
		toolBar.add(inventoryButton);
		toolBar.addSeparator();
		toolBar.add(editInventoryButton);
		toolBar.addSeparator();
		toolBar.add(pricesButton);
		toolBar.addSeparator();
		toolBar.add(creditCardButton);
		toolBar.addSeparator();
		toolBar.add(customerInfoButton);
		toolBar.addSeparator();
		toolBar.add(myInfoButton);
		toolBar.addSeparator();
		toolBar.add(shippingInfoButton);
		toolBar.addSeparator();
		toolBar.add(saveButton);
		toolBar.addSeparator();
		
		//toolBar.setVisible(true);
		centralFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
	}
	
	// build New Invoice Panel
	public static void initNewInvoice() {
		
		newInvoicePanel = new JPanel();
		newInvoicePanel.setBackground(blueTheme);
		newInvoicePanel.setLayout(null); // set layout to absolute (null layout)
	
		// Set title label
		titleLabel = new JLabel("New Invoice");
		titleLabel.setBounds(641, 5, 158, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		newInvoicePanel.add(titleLabel); // add title label to panel
		
		// invoice number text field
		invNumTxtFld = new JFormattedTextField(integerFormatter);
		invNumTxtFld.setToolTipText("Enter Invoice #");
		invNumTxtFld.setBounds(1149, 75, 130, 26); // set location of textfield
		newInvoicePanel.add(invNumTxtFld);
		invNumTxtFld.setColumns(10);
		
		// invoice number label
		JLabel invNumLabel = new JLabel("Invoice #:");
		invNumLabel.setForeground(Color.WHITE);
		invNumLabel.setBounds(1076, 80, 61, 16);
		newInvoicePanel.add(invNumLabel);
		
		// date text field
		dateTxtFld = new JFormattedTextField(getMaskFormatter("##/##/####"));
		dateTxtFld.setForeground(Color.BLACK);
		dateTxtFld.setToolTipText("Enter date (MM/DD/YYYY)");
		dateTxtFld.setColumns(10);
		dateTxtFld.setBounds(1149, 113, 130, 26);
		newInvoicePanel.add(dateTxtFld);
		
		// date label
		dateLbl = new JLabel("Date:");
		dateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		dateLbl.setForeground(Color.WHITE);
		dateLbl.setBounds(1076, 118, 61, 16);
		newInvoicePanel.add(dateLbl);
		
		// reset button to reset panel
		JButton resetButton = new JButton("RESET");
		resetButton.setBounds(202, 658, 117, 29);
		newInvoicePanel.add(resetButton);
	
		// bill to combo box (select customer to bill to)
		JComboBox billToCombo = new JComboBox();
		billToCombo.setToolTipText("select customer to bill to");
		billToCombo.setBounds(166, 114, 225, 27);
		
		// populate the bill to combobox with customers from database
		DatabaseManager.getInstance().populateCustCombo(billToCombo);	
		
		// adds blank item at first index (for checking)
		billToCombo.insertItemAt("", 0);
		
		// enable autocomplete on combobox
		AutoCompletion.enable(billToCombo);
		
		// sets the selected item to first empty string (for checking)
		billToCombo.setSelectedItem("");
		
		billToCombo.setVisible(true);
		
		// add bill to combobox to panel
		newInvoicePanel.add(billToCombo);
		
		// bill to label
		JLabel billToLbl = new JLabel("Bill To:");
		billToLbl.setForeground(Color.WHITE);
		billToLbl.setBounds(166, 92, 61, 16);
		newInvoicePanel.add(billToLbl);
		
		// column names for table
		String[] colNames = {"Product ID", "Product Name", "Quantity", "Unit Price", "Subtotal"};
		
		// table of products being purchased
		JTable newInvTbl = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // disable editing of all cells
			}
		};
		newInvTbl.getTableHeader().setReorderingAllowed(false); // disable column resizing/reordering
		
		// table model for invoice contents table, to be populated with data
		DefaultTableModel tMod = (DefaultTableModel) newInvTbl.getModel();
		tMod.setColumnIdentifiers(colNames); // sets column names of invoices table
		
		
		// scroll pane for table
		scrollPane = new JScrollPane(newInvTbl);
		scrollPane.setBounds(166, 180, 1113, 393);
		newInvoicePanel.add(scrollPane);
		
		// label for total amount
		totalLbl = new JLabel("Total:  $");
		totalLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		totalLbl.setForeground(Color.WHITE);
		totalLbl.setBounds(1058, 635, 91, 16);
		newInvoicePanel.add(totalLbl);
		
		// text field to display[ total amount
		JTextField totalFld = new JTextField();
		totalFld.setHorizontalAlignment(SwingConstants.TRAILING);
		totalFld.setToolTipText("total");
		totalFld.setColumns(10);
		totalFld.setBounds(1149, 630, 130, 26);
		totalFld.setEditable(false);
		newInvoicePanel.add(totalFld);
		
		// button to add product
		JButton addProdBtn = new JButton("Add Product");
		addProdBtn.setToolTipText("ADD PRODUCT");
		addProdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				boolean result = false;
				
				// allow addition of product only if required fields have input
				if(invNumTxtFld.getText().length() != 0 && dateValidInput(dateTxtFld) && !(billToCombo.getSelectedItem().equals(""))) {


					// textfields for input dialog
					JComboBox prodIDCombo = new JComboBox();
					DatabaseManager.getInstance().populateProdIDCombo(prodIDCombo); // populate prodID combo
					

					// adds blank item at first index (for checking)
					prodIDCombo.insertItemAt("", 0);
					
					AutoCompletion.enable(prodIDCombo); // enable autocomplete on combobox

					// sets the selected item to first empty string (for checking)
					prodIDCombo.setSelectedItem("");

					JTextField invIDTxtFld = new JTextField();
					invIDTxtFld.setText(invNumTxtFld.getText()); // sets text to invoice num from input
					invIDTxtFld.setEditable(false);

					// textfields that only accept a certain input
					JFormattedTextField qtyTxtFld = new JFormattedTextField(integerFormatter);
					JFormattedTextField unitPriceTxtFld = new JFormattedTextField(getMaskFormatter("###.##"));

					// frame for product entry form
					JFrame frame = new JFrame("Product Entry");
					frame.setBounds(600, 900, 300, 258);
					frame.setLocation(600, 1000);
					frame.setResizable(false);
					frame.getContentPane().setLayout(null);

					// components for product entry form
					JLabel enterLbl = new JLabel("Please Enter product info:");
					enterLbl.setBounds(67, 15, 175, 16);
					frame.getContentPane().add(enterLbl);

					JLabel prodIDLbl = new JLabel("ProductID:");
					prodIDLbl.setBounds(29, 65, 72, 16);
					frame.getContentPane().add(prodIDLbl);

					prodIDCombo.setBounds(119, 65, 150, 23);
					frame.getContentPane().add(prodIDCombo);

					JLabel invIDLbl = new JLabel("Invoice ID:");
					invIDLbl.setBounds(29, 93, 72, 16);
					frame.getContentPane().add(invIDLbl);

					invIDTxtFld.setBounds(119, 93, 150, 15);
					frame.getContentPane().add(invIDTxtFld);

					JLabel qtyLbl = new JLabel("Quantity:");
					qtyLbl.setBounds(29, 121, 72, 16);
					frame.getContentPane().add(qtyLbl);

					qtyTxtFld.setBounds(119, 121, 150, 15);
					frame.getContentPane().add(qtyTxtFld);

					JLabel unitPriceLbl = new JLabel("Unit Price:");
					unitPriceLbl.setBounds(29, 149, 72, 16);
					frame.getContentPane().add(unitPriceLbl);

					unitPriceTxtFld.setBounds(119, 149, 150, 15);
					frame.getContentPane().add(unitPriceTxtFld);

					// OK button for form
					JButton okButton = new JButton("OK");
					okButton.setBounds(177, 201, 117, 29);
					frame.getContentPane().add(okButton);
					okButton.setEnabled(false); // disable button at first
					okButton.setToolTipText("Press to add content. TO ENABLE: fill all required fields.");
					
					// cancel button for form
					JButton cancelButton = new JButton("Cancel");
					cancelButton.setBounds(6, 201, 117, 29);
					frame.getContentPane().add(cancelButton);
					
					// add key listeners to enable/diable okButton if no text or selection
					qtyTxtFld.addKeyListener(new KeyAdapter() {
				        public void keyReleased(KeyEvent e) {
				            super.keyReleased(e);
				            if(qtyTxtFld.getText().length() > 0 && !(unitPriceTxtFld.getText().equals("000.00")) && prodIDCombo.getSelectedItem() != "")
				                okButton.setEnabled(true);
				            else
				                okButton.setEnabled(false);
				        }
				    });
					
					// add key listeners to enable/diable okButton if no text or selection
					unitPriceTxtFld.addKeyListener(new KeyAdapter() {
				        public void keyReleased(KeyEvent e) {
				            super.keyReleased(e);
				            if(qtyTxtFld.getText().length() > 0 && !(unitPriceTxtFld.getText().equals("000.00")) && prodIDCombo.getSelectedItem() != "")
				                okButton.setEnabled(true);
				            else
				                okButton.setEnabled(false);
				        }
				    });
					
					// add listener to enable/diable okButton if no text or selection
					prodIDCombo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(qtyTxtFld.getText().length() > 0 && !(unitPriceTxtFld.getText().equals("000.00")) && prodIDCombo.getSelectedItem() != "")
				                okButton.setEnabled(true);
				            else
				                okButton.setEnabled(false);
						}
					});
					
					
					// if cancel button press dispose the frame
					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							frame.dispose();
						}
					});
					

					frame.setVisible(true);
					
					// gets the textfield info for each textfield
					 Object date = dateTxtFld.getText();
					
					 String invNum = invNumTxtFld.getText(); // gets the num from the text box

					// the value of selected customer in Select Customer combo box
					Object custSelection = String.valueOf(billToCombo.getSelectedItem());
					
					// do actions when ok button pressed
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {


							Object unitPrice = unitPriceTxtFld.getText();

							// get the input and assign to variables
							Object prodID = String.valueOf(prodIDCombo.getSelectedItem()); // the value of selected product in prodID combo boxObject unitPrice = unitPriceTxtFld.getText();

							// panel for message dialogs
							JPanel msgPnl = new JPanel();
							
							if (qtyTxtFld.getText() == "") {
								JOptionPane.showMessageDialog(msgPnl,  "Please input something for: QUANTITY", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else if (unitPriceTxtFld.getText().equals("000.00")) {
								JOptionPane.showMessageDialog(msgPnl,  "Please input something for: UNIT PRICE", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else if (prodIDCombo.getSelectedItem() == "") {
								JOptionPane.showMessageDialog(msgPnl,  "Please select a product.", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else {
							
								// if add (ok) button selected AND there's enough in inventory, add to table
								if (DatabaseManager.getInstance().checkInventory(prodID,
										Integer.parseInt(qtyTxtFld.getText()))) {

									// disable the editing of these fields after creating the invoice
									billToCombo.setEditable(false);
									invNumTxtFld.setEditable(false);
									dateTxtFld.setEditable(false);

									if (qtyTxtFld.getText() == "") {
										JOptionPane.showMessageDialog(null, "Please input something for: QUANTITY");
									}

									if (count == 0) { // add the invoice to the database only once
										DatabaseManager.getInstance().addInvoice(invNum, custSelection, date, calcTotal(newInvTbl),
												0);
										count++;
									}
									// add invoice content to database
									DatabaseManager.getInstance().addInvContent(newInvTbl, tMod, prodID, invNum,
											Integer.parseInt(qtyTxtFld.getText()), unitPrice);
									// update prod quantity and subtract the amt in invoice
									DatabaseManager.getInstance().setQuantity(prodID,
											DatabaseManager.getInstance().getAmtInStock(prodID)
													- Integer.parseInt(qtyTxtFld.getText()));
									// update total on invoice in database
									DatabaseManager.getInstance().updateTotal(invNum, calcTotal(newInvTbl));

									frame.dispose(); // close the frame
							
									// set total text field
									totalFld.setText(String.valueOf(calcTotal(newInvTbl))); // display total on new invoice frame
									totalFld.setVisible(true); // make sure total is visible

								}
							}
						}
					});
					 
				}
				else if(billToCombo.getSelectedItem().equals("")) {	// display error message if no customer is selected
					JOptionPane.showMessageDialog(null, "Please select a customer to bill to.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(invNumTxtFld.getText().length() == 0){ 	// display error message if no invoice number entered
					JOptionPane.showMessageDialog(null, "Please input an invoice #.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(!(dateValidInput(dateTxtFld))) {	// display error message if no date entered
					JOptionPane.showMessageDialog(null, "Please input a date.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		addProdBtn.setBounds(719, 631, 130, 26);
		newInvoicePanel.add(addProdBtn);
		
		// button to remove a product
		JButton removeProdBtn = new JButton("Remove Product");
		removeProdBtn.setToolTipText("REMOVE PRODUCT");
		removeProdBtn.setBounds(719, 678, 130, 26);
		newInvoicePanel.add(removeProdBtn);
		
		// remove selected product (row) from invoice when button clicked
		removeProdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseManager.getInstance().removeProductInvoice(newInvTbl, tMod, invNumTxtFld.getText());
			}

		});
		
		// reset the panel after button clicked
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				invNumTxtFld.setText("");
				invNumTxtFld.setEditable(true);
				dateTxtFld.setText("");
				dateTxtFld.setEditable(true);
				billToCombo.setSelectedItem("");
				billToCombo.setEditable(true);
				AutoCompletion.enable(billToCombo);
				totalFld.setText("");
				tMod.setRowCount(0);
			}
		});
		
		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(1162, 658, 117, 29);
		newInvoicePanel.add(refreshBtn);
		
		// refreshes the table and components
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				DatabaseManager.getInstance().populateCustCombo(billToCombo); // re-populate combo box
				
				// enable autocompletion on the combobox
				AutoCompletion.enable(billToCombo);
				
				billToCombo.setVisible(true);
				
			}
		});
			
	}
	
	// build Saved Invoice Panel
	public static void initSavedInvoices() {
		
		// saved invoices panel attributes
		savedInvoicesPanel = new JPanel();
		savedInvoicesPanel.setBackground(blueTheme);
		savedInvoicesPanel.setLayout(null); // set layout to absolute (null layout)
		
		// set title label
		titleLabel = new JLabel("Saved Invoices");
		titleLabel.setBounds(641, 5, 200, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		savedInvoicesPanel.add(titleLabel);
		
		// search for invoice label
		JLabel searchInvLabel = new JLabel("Search: ");
		searchInvLabel.setForeground(Color.WHITE);
		searchInvLabel.setBounds(166, 92, 61, 16);
		savedInvoicesPanel.add(searchInvLabel);

		// combobox to search invoice
		JComboBox selectInvCombo = new JComboBox();
		selectInvCombo.setToolTipText("select customer to bill to");
		selectInvCombo.setBounds(166, 114, 225, 27);
		DatabaseManager.getInstance().populateInvCombo(selectInvCombo); // populate combobox w/ saved invoices
		
		// adds blank item at first index (for checking)
		selectInvCombo.insertItemAt("", 0);

		// enable autocompletion on the combobox
		AutoCompletion.enable(selectInvCombo);

		// sets the selected item to first empty string (for checking)
		selectInvCombo.setSelectedItem("");

		selectInvCombo.setVisible(true);	// make sure the combobox is visible
		
		// column names for table
		String[] colNames = {"Invoice #", "Customer", "Date", "Total"};
		
		// table of all saved invoices, by customer name & date
		JTable invoicesTbl = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // disable editing of all cells
			}
		};
		invoicesTbl.getTableHeader().setReorderingAllowed(false); // disable column resizing/reordering
		
		// table model for invoices table, to be populated with data
		DefaultTableModel tMod = (DefaultTableModel) invoicesTbl.getModel();
		tMod.setColumnIdentifiers(colNames);	// sets column names of invoices table

		// display invoice table with database data
		DatabaseManager.getInstance().displayInvoices(invoicesTbl, tMod);
		
		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(1149, 113, 130, 26);
		savedInvoicesPanel.add(refreshBtn);
		
		// refreshes the table and components
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				// populate combobox w/ saved invoices
				DatabaseManager.getInstance().populateInvCombo(selectInvCombo); 
				
				// enable autocompletion on the combobox
				AutoCompletion.enable(selectInvCombo);
				
				selectInvCombo.setVisible(true);
				
				tMod.setRowCount(0); // clear the table
				
				// reset the table info
				DatabaseManager.getInstance().displayInvoices(invoicesTbl, tMod);
				
				
			}
		});
		
		
		// scroll pane containing unpaid list
		scrollPane = new JScrollPane(invoicesTbl);
		scrollPane.setBounds(166, 180, 1113, 393);
		savedInvoicesPanel.add(scrollPane);
		
		// when invoice selected, display and select on table
		selectInvCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				// the value of selected invoice in combo box
				String invSelection = String.valueOf(selectInvCombo.getSelectedItem());
				
				// gets the row num of the table w/ the invoice num
				int index = getRowAt(invoicesTbl, 0, selectInvCombo.getSelectedItem());
				
				// allows row selection
				invoicesTbl.setRowSelectionAllowed(true);
				
				// selects the row with selected invoice index
				invoicesTbl.changeSelection(index, 1, false, false); // gets rid of past selections
				
				viewInvoice(invSelection, index); // display the selected invoice 
				
			}
		});
		savedInvoicesPanel.add(selectInvCombo);
	
		
	}
	
	// build Unpaid Invoices Panel
	public static void initUnpaidInvoices() {
		
		// unpaid invoices panel attributes
		unpaidInvoicesPanel = new JPanel();
		unpaidInvoicesPanel.setBackground(blueTheme);
		unpaidInvoicesPanel.setLayout(null); // set layout to absolute (null layout)
		
		// set title label
		titleLabel = new JLabel("Unpaid Invoices");
		titleLabel.setBounds(641, 5, 240, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		unpaidInvoicesPanel.add(titleLabel);

		// combobox to search invoice
		JComboBox selectInvCombo = new JComboBox();
		selectInvCombo.setToolTipText("select customer to bill to");
		selectInvCombo.setBounds(166, 114, 225, 27);
		DatabaseManager.getInstance().populateInvCombo(selectInvCombo); // populate combobox w/ saved invoices
		
		// adds blank item at first index (for checking)
		selectInvCombo.insertItemAt("", 0);

		// enable autocompletion on the combobox
		AutoCompletion.enable(selectInvCombo);

		// sets the selected item to first empty string (for checking)
		selectInvCombo.setSelectedItem("");

		selectInvCombo.setVisible(true);	// make sure the combobox is visible
		
		// column names for table
		String[] colNames = {"Invoice #", "Customer", "Date", "Total Due", "Amount Paid"};
		
		// table of unpaid invoices
		JTable unpaidTbl = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // disable editing of all cells
			}
		};
		unpaidTbl.getTableHeader().setReorderingAllowed(false); // disable column resizing/reordering
	
		// table model for unpaid invoices table, to be populated with data
		DefaultTableModel tMod = (DefaultTableModel) unpaidTbl.getModel();
		tMod.setColumnIdentifiers(colNames); // sets column names of invoices table
		
		// display unpaid invoices from database
		DatabaseManager.getInstance().displayUnpaidInvoices(unpaidTbl, tMod);
		
		// scroll pane containing unpaid list
		scrollPane = new JScrollPane(unpaidTbl);
		scrollPane.setBounds(166, 180, 1113, 393);
		unpaidInvoicesPanel.add(scrollPane);

		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(1149, 113, 130, 26);
		unpaidInvoicesPanel.add(refreshBtn);
		
		// refreshes the table and components
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				//selectInvCombo.removeAllItems(); // reset the combo box
				
				DatabaseManager.getInstance().populateInvCombo(selectInvCombo); // populate combobox w/ saved invoices
				
				// enable autocompletion on the combobox
				AutoCompletion.enable(selectInvCombo);
				
				selectInvCombo.setVisible(true);
				
				tMod.setRowCount(0); // clear the table
				
				// reset the table info
				DatabaseManager.getInstance().displayUnpaidInvoices(unpaidTbl, tMod);
				
				
			}
		});
		
		selectInvCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				// the value of selected invoice in combo box
				String invSelection = String.valueOf(selectInvCombo.getSelectedItem());
				
				// gets the row num of the table w/ the invoice num
				int index = getRowAt(unpaidTbl, 0, selectInvCombo.getSelectedItem());
				
				// allows row selection
				unpaidTbl.setRowSelectionAllowed(true);
				
				// selects the row with selected invoice index
				unpaidTbl.changeSelection(index, 1, false, false); // gets rid of past selections
				
				viewInvoice(invSelection, index); // display the selected invoice 

			}
		});
		unpaidInvoicesPanel.add(selectInvCombo);
		
		// save button on the panel
		saveButton = new JButton("SAVE");
		saveButton.setToolTipText("SAVE CHANGES");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO save action
			}
		});
		saveButton.setBounds(1162, 658, 117, 29);
		unpaidInvoicesPanel.add(saveButton);
		
		
	}
	
	// build Notifications Panel
	public static void initNotifications() {
		
		// notifications panel attributes
		notificationsPanel = new JPanel();
		notificationsPanel.setBackground(blueTheme);
		notificationsPanel.setLayout(null); // set layout to absolute (null layout)
		
		// set title label
		titleLabel = new JLabel("Notifications");
		titleLabel.setBounds(641, 5, 240, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		notificationsPanel.add(titleLabel);
		
		// list model to be updated
		DefaultListModel<String> lMod = new DefaultListModel<String>();
		
		// List of notifications
		JList<String> notifList = new JList<String>(lMod);
		
		// add notifcations to list
		DatabaseManager.getInstance().addNotification(lMod);
		
		
		scrollPane = new JScrollPane(notifList);
		scrollPane.setBounds(166, 180, 1113, 393);
		notificationsPanel.add(scrollPane);
		
		// TODO auto populate notification list
		// TODO when click on notification show the notif message
		// TODO resolve notification (remove it)
		
		
		// TODO calculate when inventory low
		// TODO create pop up notifications when inventory low
		
		
	}
	
	// build Inventory Panel
	public static void initInventory() {
		
		// inventory panel attributes
		inventoryPanel = new JPanel();
		inventoryPanel.setBackground(blueTheme);
		inventoryPanel.setLayout(null); // set layout to absolute (null layout)
		
		// set title label
		titleLabel = new JLabel("Inventory");
		titleLabel.setBounds(641, 5, 240, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		inventoryPanel.add(titleLabel);
		
		// label prompting select invoice
		JLabel selectProdID = new JLabel("Product Search:");
		selectProdID.setForeground(Color.WHITE);
		selectProdID.setBounds(166, 92, 110, 16);
		inventoryPanel.add(selectProdID);
		
		// Combo box of invoices
		JComboBox selectProdCombo = new JComboBox();
		selectProdCombo.setToolTipText("select product #");
		selectProdCombo.setBounds(166, 114, 225, 27);
		selectProdCombo.setEditable(false);
		inventoryPanel.add(selectProdCombo);
		
		
		// populate product combo box with database data
		DatabaseManager.getInstance().populateProdIDCombo(selectProdCombo);

		// adds blank item at first index (for checking)
		selectProdCombo.insertItemAt("", 0);

		// enable autocompletion on the combobox
		AutoCompletion.enable(selectProdCombo);

		// sets the selected item to first empty string (for checking)
		selectProdCombo.setSelectedItem("");

		// populates table with prodID from selected prodID
		selectProdCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				// the value of selected product in Select product combo box
				String prodID = String.valueOf(selectProdCombo.getSelectedItem());
				Object prodIDObj = selectProdCombo.getSelectedItem();
				String prodName = String.valueOf(DatabaseManager.getInstance().getProdName(prodIDObj));
				String amtInStock = String.valueOf(DatabaseManager.getInstance().getAmtInStock(prodIDObj));
				String title = "Product " + prodID;
				
				String message = "Product ID: " + prodID + "\nName: " + prodName + "\nAmt in Stock: " + amtInStock;
				
				JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
				
				
				
			}
		});
		
		inventoryPanel.add(selectProdCombo);
		
		
		// table of entire inventory
		JTable inventoryTbl = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // disable editing of all cells
			}
		};
		inventoryTbl.getTableHeader().setReorderingAllowed(false); // disable column resizing/reordering

		// columnn names for Inventory table
		Object colNames[] = {"Product ID", "Product Name", "Amount In Stock"};
	
		// table model for inventory table, to be populated with data
		DefaultTableModel tMod = (DefaultTableModel) inventoryTbl.getModel();
		tMod.setColumnIdentifiers(colNames);	// sets column names of inventory table
		
		// set and display inventory table
		DatabaseManager.getInstance().displayInventory(inventoryTbl, tMod);
		
		// scroll pane for table
		scrollPane = new JScrollPane(inventoryTbl);
		scrollPane.setBounds(166, 180, 1113, 393);
		inventoryPanel.add(scrollPane);
		
		// button that shows edit inventory card
		JButton editInvenBtn = new JButton("EDIT");
		editInvenBtn.setBounds(1162, 658, 117, 29);
		editInvenBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "6"); // show edit inventory panel
			}
		});
		inventoryPanel.add(editInvenBtn);
		

		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(1149, 113, 130, 26);
		inventoryPanel.add(refreshBtn);
		
		// refreshes the table and components
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				DatabaseManager.getInstance().populateProdIDCombo(selectProdCombo); // re-populate combo box
				
				// enable autocompletion on the combobox
				AutoCompletion.enable(selectProdCombo);
				
				selectProdCombo.setVisible(true);
				
				tMod.setRowCount(0); // clear the table
				
				// reset the table info
				DatabaseManager.getInstance().displayInventory(inventoryTbl, tMod);
				
			}
		});
			
	}
	
	// build Edit Inventory Panel
	public static void initEditInventory() {
		
		// edit inventory panel attributes
		editInventoryPanel = new JPanel();
		editInventoryPanel.setBackground(blueTheme);
		editInventoryPanel.setLayout(null);
		
		// set title label
		titleLabel = new JLabel("Edit Inventory");
		titleLabel.setBounds(641, 5, 240, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		editInventoryPanel.add(titleLabel);	

		// inventory table to edit
		JTable editInventoryTbl = new JTable();
		editInventoryTbl.getTableHeader().setReorderingAllowed(false); // disable column resizing/reordering

		// columnn names for Inventory table
		Object colNames[] = {"Product ID", "Product Name", "Amount In Stock"};
	
		// table model for inventory table, to be populated with data
		DefaultTableModel tMod = (DefaultTableModel) editInventoryTbl.getModel();
		tMod.setColumnIdentifiers(colNames);	// sets column names of inventory table
		
		// set and display inventory table
		DatabaseManager.getInstance().displayInventory(editInventoryTbl, tMod);

		// update the database with edited cell information
		editInventoryTbl.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				
				// if cell is updated, update the info in the database
				if (e.getType() == (TableModelEvent.UPDATE)) {
					int intColumn = e.getColumn();
					int intRows = e.getFirstRow();
					DatabaseManager.getInstance().updateInventoryData(tMod, intRows, intColumn); // Update database
				}
			}
		});
				
		// scroll pane for the prices table
		scrollPane = new JScrollPane(editInventoryTbl); // adds table to scroll pane
		scrollPane.setBounds(166, 180, 1113, 393); // sets scroll pane view
		editInventoryPanel.add(scrollPane); // add scrollpane (with table) to panel
		
		// new product button
		JButton newProdBtn = new JButton("Add Product");
		newProdBtn.setBounds(166, 658, 117, 29);
		newProdBtn.setToolTipText("ADD a new product to the inventory");
		newProdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // when button clicked, add new product

				// textfields for input dialog
				JTextField idTxtFld = new JTextField();
				JTextField nameTxtFld = new JTextField();
				JTextField amtTxtFld = new JTextField();
				JTextField priceTxtFld = new JTextField();
				
				// text and textfields for input dialog
				Object[] contents = {"Product ID:", idTxtFld, "Product Name:", nameTxtFld, "Amount in Stock:", amtTxtFld, "Price:", priceTxtFld};
				
				 // displays option dialog and takes in chosen option
				 int result = JOptionPane.showConfirmDialog(null, contents, 
			               "Please Enter product info:", JOptionPane.OK_CANCEL_OPTION);
				 
				 // get the input and assign to variables
				 String id = idTxtFld.getText();
				 String name = nameTxtFld.getText();
				 String amt = amtTxtFld.getText();
				 String price = priceTxtFld.getText();
				 
				 // if add (ok) button selected, add to table
				 if (result == JOptionPane.OK_OPTION) {
					 DatabaseManager.getInstance().addProduct(tMod, id, name, amt, price);
				 }
				
			}
		});
		editInventoryPanel.add(newProdBtn);
		
		// delete product button
		JButton removeProdBtn = new JButton("Remove Product");
		removeProdBtn.setToolTipText("REMOVE a product from the inventory");
		removeProdBtn.setBounds(316, 658, 130, 29);
		removeProdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseManager.getInstance().removeProduct(editInventoryTbl, tMod); // remove selected product
			}
		});
		editInventoryPanel.add(removeProdBtn);
		
		// save button on the panel
		saveButton = new JButton("SAVE");
		saveButton.setToolTipText("SAVE CHANGES");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO save action
			}
		});
		saveButton.setBounds(1162, 658, 117, 29);
		editInventoryPanel.add(saveButton);
	
	}
	
	// build Prices Panel
	public static void initPrices() {
		
		// prices panel attributes
		pricesPanel = new JPanel();
		pricesPanel.setBackground(blueTheme);
		pricesPanel.setLayout(null);
		
		// set title label
		titleLabel = new JLabel("Prices");
		titleLabel.setBounds(641, 5, 158, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		pricesPanel.add(titleLabel);	
		
		// table with all of the prices for each product, includes row data and column names
		JTable pricesTbl = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return colIndex == 2; // only enable price column editable
			}
		};
		pricesTbl.getTableHeader().setReorderingAllowed(false); // disable column resizing/reordering
		
		// columnn names for table
		Object colNames[] = { "Product ID", "Product Name", "Price" };
		
		// table model for table, to be populated with data
		DefaultTableModel tMod = (DefaultTableModel) pricesTbl.getModel();
		tMod.setColumnIdentifiers(colNames); // sets column names of table
		
		// populate prices table
		DatabaseManager.getInstance().displayPrices(pricesTbl, tMod);
		
		// update the database with edited cell information
		pricesTbl.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				
				// if cell is updated, update the info in the database
				if (e.getType() == (TableModelEvent.UPDATE)) {
					int intColumn = e.getColumn();
					int intRows = e.getFirstRow();
					DatabaseManager.getInstance().updatePriceData(tMod, intRows, intColumn); // Update
				}
			}
		});
		
		// label prompting select invoice
		JLabel selectProdID = new JLabel("Product Search:");
		selectProdID.setForeground(Color.WHITE);
		selectProdID.setBounds(166, 92, 110, 16);
		pricesPanel.add(selectProdID);

		// Combo box of invoices
		JComboBox selectProdCombo = new JComboBox();
		selectProdCombo.setToolTipText("select product #");
		selectProdCombo.setBounds(166, 114, 225, 27);
		selectProdCombo.setEditable(false);
		
		// populate product combo box with database data
		DatabaseManager.getInstance().populateProdIDCombo(selectProdCombo);

		// adds blank item at first index (for checking)
		selectProdCombo.insertItemAt("", 0);

		// enable autocompletion on the combobox
		AutoCompletion.enable(selectProdCombo);

		// sets the selected item to first empty string (for checking)
		selectProdCombo.setSelectedItem("");

		// populates table with prodID from selected prodID
		selectProdCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				// the value of selected product in select product combo box
				String prodSelection = String.valueOf(selectProdCombo.getSelectedItem());
				Object prodID = selectProdCombo.getSelectedItem();
				double price = DatabaseManager.getInstance().getProdPrice(prodID);
				String name = DatabaseManager.getInstance().getProdName(prodID);
				
				String title = "Product " + prodSelection;
				
				String message = "Product#: " + prodSelection + "\nName: " + name + "\nPrice: " + price;
				
				JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
				
				
			}
		});
		
		pricesPanel.add(selectProdCombo);
		
		// refresh button
		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(1149, 113, 130, 26);
		pricesPanel.add(refreshBtn);
		
		// refreshes the table and components
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				
				// populate combobox w/ saved invoices
				DatabaseManager.getInstance().populateProdIDCombo(selectProdCombo); 
				
				// enable autocompletion on the combobox
				AutoCompletion.enable(selectProdCombo);
				
				selectProdCombo.setVisible(true);
				
				
				tMod.setRowCount(0); // clear the table
				
				// reset the table info
				DatabaseManager.getInstance().displayPrices(pricesTbl, tMod);
				
				
			}
		});
		
		
		// scroll pane for the prices table
		scrollPane = new JScrollPane(pricesTbl); // adds table to scroll pane
		scrollPane.setBounds(166, 180, 1113, 393); // sets scroll pane view
		pricesPanel.add(scrollPane); // add scroll pane (with table) to panel
		
		// save button on the panel
		saveButton = new JButton("SAVE");
		saveButton.setToolTipText("SAVE CHANGES");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO save action
			}
		});
		saveButton.setBounds(1162, 658, 117, 29);
		editInventoryPanel.add(saveButton);
		
		
	}
	
	// build Credit Card Panel
	public static void initCreditCard() {
		
		// credit card panel attributes
		creditCardPanel = new JPanel();
		creditCardPanel.setBackground(blueTheme);
		creditCardPanel.setLayout(null);
		
		// set title label
		titleLabel = new JLabel("Credit Card Info");
		titleLabel.setBounds(641, 5, 300, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		creditCardPanel.add(titleLabel);
		
		// label to prompt selection of customer for combo box
		JLabel selectCustLbl = new JLabel("Select Customer:");
		selectCustLbl.setBounds(166, 160, 200, 29);
		selectCustLbl.setForeground(Color.WHITE);
		creditCardPanel.add(selectCustLbl);
		
		// combo box of customers
		JComboBox selectCustCombo = new JComboBox();
		selectCustCombo.setBounds(166, 180, 200, 31);
		selectCustCombo.setToolTipText("Select customer to add credit info");
		selectCustCombo.setEditable(false); // doesn't allow user to edit
		
		// populate customer combo box with database data
		DatabaseManager.getInstance().populateCustCombo(selectCustCombo);

		// adds blank item at first index (for checking)
		selectCustCombo.insertItemAt("", 0);

		// enable autocompletion on the combobox
		AutoCompletion.enable(selectCustCombo);

		// sets the selected item to first empty string (for checking)
		selectCustCombo.setSelectedItem("");
		
		selectCustCombo.setVisible(true);

		// add the combobox to panel
		creditCardPanel.add(selectCustCombo);
		
		
		// table populated with customer info
		JTable custTbl = new JTable();
		
		// columnn names for table
		Object colNames[] = { "Customer Name", "Address", "Email", "Phone Number", "Company" };

		// table model for inventory table, populates customer table
		DefaultTableModel tMod = (DefaultTableModel) custTbl.getModel();
		tMod.setColumnIdentifiers(colNames); // sets column names of table

		tMod.setRowCount(0); // only allows one row to be visible in the table

		// populates table with customer info from selected customer
		selectCustCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				// the value of selected customer in Select Customer combo box
				String custSelection = String.valueOf(selectCustCombo.getSelectedItem());
				
				// populate and display customer table with customer selection
				DatabaseManager.getInstance().displayCreditCard(custTbl, tMod, custSelection);
			}
		});
		
		// scroll pane for customer info text field
		scrollPane = new JScrollPane(custTbl);
		scrollPane.setBounds(166, 230, 500, 300);
		creditCardPanel.add(scrollPane);
		
		// credit card types
		String[] creditTypes = {"", "American Express", "Discover Card", "Master Card", "Visa", "Capital One", "Credit One", "Other"};
		
		// label to prompt selection of credit type for combo box
		JLabel selectCredLbl = new JLabel("Select Credit Type:");
		selectCredLbl.setBounds(800, 210, 200, 29);
		selectCredLbl.setForeground(Color.WHITE);
		creditCardPanel.add(selectCredLbl);
		
		// Combo box of credit types
		JComboBox creditTypeCombo = new JComboBox(creditTypes);
		creditTypeCombo.setEditable(false); // doesn't allow user to edit
		creditTypeCombo.setToolTipText("Select credit card type");
		creditTypeCombo.setBounds(800, 230, 200, 31);
		
		creditCardPanel.add(creditTypeCombo);
		
		// Label to prompt to enter credit card #
		JLabel credNumLbl = new JLabel("Credit Card #:");
		credNumLbl.setBounds(800, 310, 200, 29);
		credNumLbl.setForeground(Color.WHITE);
		creditCardPanel.add(credNumLbl);
		
		// credit card number text field
		JTextField credNumTxtFld = new JTextField();
		credNumTxtFld.setEditable(true);
		credNumTxtFld.setToolTipText("Enter credit card number");
		credNumTxtFld.setBounds(800, 330, 200, 31);
		
		// sets selected customers credit card type to selected credit card type
		creditTypeCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				// the value of selected customer in Select Customer combo box
				String selectedCCType = String.valueOf(creditTypeCombo.getSelectedItem());
				
			}
		});
		
		creditCardPanel.add(credNumTxtFld);
		// TODO press enter & save
		
		// save button on the panel
		saveButton = new JButton("SAVE");
		saveButton.setToolTipText("SAVE CHANGES");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// the value of selected customer in Select Customer combo box
				String selectedCCType = String.valueOf(creditTypeCombo.getSelectedItem());
				
				// the value of selected customer in Select Customer combo box
				String custSelection = String.valueOf(selectCustCombo.getSelectedItem());
				
				String ccNum = String.valueOf(credNumTxtFld);
				
				// update if all fields are filled
				if(!(selectedCCType.equals("")) && !(custSelection.equals("")) && !(ccNum.equals(""))) {
					// update credit card table
					DatabaseManager.getInstance().updateCredit(selectedCCType, custSelection);
				}
				else if(custSelection.equals("")) {
					JOptionPane.showMessageDialog(null, "Please select a customer.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(selectedCCType.equals("")) {
					JOptionPane.showMessageDialog(null, "Please select a credit card type.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(ccNum.equals("")) {
					JOptionPane.showMessageDialog(null, "Please input a credit card number.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			
			}
		});
		saveButton.setBounds(1162, 658, 117, 29);
		creditCardPanel.add(saveButton);
		
		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(202, 658, 117, 29);
		creditCardPanel.add(refreshBtn);
		
		// refreshes the table and components
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				DatabaseManager.getInstance().populateCustCombo(selectCustCombo); // re-populate combo box
				
				// enable autocompletion on the combobox
				AutoCompletion.enable(selectCustCombo);
				
				selectCustCombo.setVisible(true);
				
			}
		});
		
	}
	
	// build customer info panel
	public static void initCustomerInfo() {
		
		// customer info panel attributes
		customerInfoPanel = new JPanel();
		customerInfoPanel.setBackground(blueTheme);
		customerInfoPanel.setLayout(null);
		
		// set title label
		titleLabel = new JLabel("Customer Info");
		titleLabel.setBounds(641, 5, 190, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		customerInfoPanel.add(titleLabel);

		// label prompting select customer
		JLabel selectCustLbl = new JLabel("Customer Search:");
		selectCustLbl.setForeground(Color.WHITE);
		selectCustLbl.setBounds(166, 92, 110, 16);
		customerInfoPanel.add(selectCustLbl);
		
		// Combo box of customers
		JComboBox selectCustCombo = new JComboBox();
		selectCustCombo.setToolTipText("select customer");
		selectCustCombo.setBounds(166, 114, 225, 27);
		selectCustCombo.setEditable(false);
		customerInfoPanel.add(selectCustCombo);
		
		// populate customer combo box with database data
		DatabaseManager.getInstance().populateCustCombo(selectCustCombo);
		
		// adds blank item at first index (for checking)
		selectCustCombo.insertItemAt("", 0);

		// enable autocompletion on the combobox
		AutoCompletion.enable(selectCustCombo);

		// sets the selected item to first empty string (for checking)
		selectCustCombo.setSelectedItem("");

		// populates table with customers
		selectCustCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				// the value of selected customer in Select Customer combo box
				String custSelection = String.valueOf(selectCustCombo.getSelectedItem());
				String address = DatabaseManager.getInstance().getCustAddress(custSelection);
				String email = DatabaseManager.getInstance().getCustEmail(custSelection);
				String phone = DatabaseManager.getInstance().getCustPhone(custSelection);
				String company = DatabaseManager.getInstance().getCustCompany(custSelection);
				
				
				String message = "Customer: " + custSelection + "\nCompany: " + company + "\nAddress: " + address
						+ "\nEmail: " + email + "\nPhone: " + phone;
				String title = "Customer " + custSelection;
				
				JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
				
				
				
			}
		});
		
		customerInfoPanel.add(selectCustCombo);
		
		// inventory table to edit
		JTable custInfoTbl = new JTable();
		
		custInfoTbl.getTableHeader().setReorderingAllowed(false); // disable column resizing/reordering
		

		// column names for table
		Object colNames[] = { "Customer Name", "Address", "Email", "Phone #", "Company" };

		// table model for table, to be populated with data
		DefaultTableModel tMod = (DefaultTableModel) custInfoTbl.getModel();
		tMod.setColumnIdentifiers(colNames); // sets column names of table
		
		// display customer table with database table data
		DatabaseManager.getInstance().displayCustomers(custInfoTbl, tMod);

		// update the database with edited cell information
		custInfoTbl.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {

				// if cell is updated, update the info in the database
				if (e.getType() == (TableModelEvent.UPDATE)) {
					int intColumn = e.getColumn();
					int intRows = e.getFirstRow();
					DatabaseManager.getInstance().updateCustomerData(tMod, intRows, intColumn); // Update database
				}
			}
		});

		// scroll pane for the customer table
		scrollPane = new JScrollPane(custInfoTbl); // adds table to scroll pane
		scrollPane.setBounds(166, 180, 1113, 393); // sets scroll pane view
		customerInfoPanel.add(scrollPane); // add scrollpane (with table) to panel
		
		// new customer button
		JButton newCustBtn = new JButton("New Customer");
		newCustBtn.setBounds(166, 658, 117, 29);
		newCustBtn.setToolTipText("ADD a new customer");
		newCustBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // when button clicked, add new product

				// textfields for input dialog
				JTextField nameTxtFld = new JTextField();
				JTextField addressTxtFld = new JTextField();
				JTextField emailTxtFld = new JTextField();
				JTextField phoneTxtFld = new JTextField();
				JTextField companyTxtFld = new JTextField();
				
				// text and textfields for input dialog
				Object[] contents = {"Name:", nameTxtFld, "Address:", addressTxtFld, "Email:", emailTxtFld, "Phone#:", phoneTxtFld, "Company:", companyTxtFld};
				
				 // displays option dialog and takes in chosen option
				 int result = JOptionPane.showConfirmDialog(null, contents, 
			               "Please Enter customer info:", JOptionPane.OK_CANCEL_OPTION);
				 
				 // get the input and assign to variables
				 Object name = nameTxtFld.getText();
				 Object address = addressTxtFld.getText();
				 Object email = emailTxtFld.getText();
				 Object phone = phoneTxtFld.getText();
				 Object company = companyTxtFld.getText();
				 
				 // if add (ok) button selected, add to table
				 if (result == JOptionPane.OK_OPTION) {
					 DatabaseManager.getInstance().addCustomer(tMod, name, address, email, phone, company);
				 }
			}
		});
		customerInfoPanel.add(newCustBtn);
		
		// delete customer button
		JButton removeCustBtn = new JButton("Remove Customer");
		removeCustBtn.setToolTipText("REMOVE a customer");
		removeCustBtn.setBounds(316, 658, 140, 29);
		removeCustBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// when button clicked remove selected customer
				DatabaseManager.getInstance().removeCustomer(custInfoTbl, tMod);
			}
		});
		customerInfoPanel.add(removeCustBtn);
		
		// save button on the panel
		saveButton = new JButton("SAVE");
		saveButton.setToolTipText("SAVE CHANGES");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO save action
			}
		});
		saveButton.setBounds(1162, 658, 117, 29);
		customerInfoPanel.add(saveButton);
		

		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(1149, 113, 130, 26);
		inventoryPanel.add(refreshBtn);
		
		// refreshes the table and components
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				DatabaseManager.getInstance().populateProdIDCombo(selectCustCombo); // re-populate combo box
				
				// enable autocompletion on the combobox
				AutoCompletion.enable(selectCustCombo);
				
				selectCustCombo.setVisible(true);
				
				tMod.setRowCount(0); // clear the table
				
				// reset the table info
				DatabaseManager.getInstance().displayInventory(custInfoTbl, tMod);
				
			}
		});
		
		
	}
	
	// build my info panel
	public static void initMyInfo() {
		
		// my info panel attributes
		myInfoPanel = new JPanel();
		myInfoPanel.setBackground(blueTheme);
		myInfoPanel.setLayout(null);
		
		// set title label
		titleLabel = new JLabel("My Info");
		titleLabel.setBounds(641, 5, 158, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		myInfoPanel.add(titleLabel);

		// label prompting enter first name
		JLabel firstNameLbl = new JLabel("First Name:");
		firstNameLbl.setBounds(286, 160, 200, 29);
		firstNameLbl.setForeground(Color.WHITE);
		myInfoPanel.add(firstNameLbl);
		
		// first name text field
		JTextField firstNameTxtFld = new JTextField();
		firstNameTxtFld.setToolTipText("Enter your first name");
		firstNameTxtFld.setBounds(286, 180, 200, 31);
		firstNameTxtFld.setEditable(true);
		myInfoPanel.add(firstNameTxtFld);
		
		// label prompting enter last name
		JLabel lastNameLbl = new JLabel("Last Name:");
		lastNameLbl.setBounds(866, 160, 200, 29);
		lastNameLbl.setForeground(Color.WHITE);
		myInfoPanel.add(lastNameLbl);
		
		// first name text field
		JTextField lastNameTxtFld = new JTextField();
		lastNameTxtFld.setToolTipText("Enter your last name");
		lastNameTxtFld.setBounds(866, 180, 200, 31);
		lastNameTxtFld.setEditable(true);
		myInfoPanel.add(lastNameTxtFld);
		
		// label prompting enter business name
		JLabel businNameLbl = new JLabel("Business Name:");
		businNameLbl.setBounds(286, 220, 200, 29);
		businNameLbl.setForeground(Color.WHITE);
		myInfoPanel.add(businNameLbl);

		// business name text field
		JTextField businNameTxtFld = new JTextField();
		businNameTxtFld.setToolTipText("Enter your business name");
		businNameTxtFld.setBounds(286, 240, 200, 31);
		businNameTxtFld.setEditable(true);
		myInfoPanel.add(businNameTxtFld);
		
		// label prompting enter address
		JLabel addressLbl = new JLabel("Address:");
		addressLbl.setBounds(866, 220, 200, 29);
		addressLbl.setForeground(Color.WHITE);
		myInfoPanel.add(addressLbl);

		// address text field
		JTextField addressTxtFld = new JTextField();
		addressTxtFld.setToolTipText("Enter your business address");
		addressTxtFld.setBounds(866, 240, 200, 31);
		addressTxtFld.setEditable(true);
		myInfoPanel.add(addressTxtFld);
		
		// label prompting enter phone number
		JLabel phoneNumLbl = new JLabel("Phone Number:");
		phoneNumLbl.setBounds(286, 280, 200, 29);
		phoneNumLbl.setForeground(Color.WHITE);
		myInfoPanel.add(phoneNumLbl);

		// phone number text field
		JFormattedTextField phoneNumTxtFld = new JFormattedTextField(getMaskFormatter("(###)-###-####"));
		phoneNumTxtFld.setToolTipText("Enter your business phone number");
		phoneNumTxtFld.setBounds(286, 300, 200, 31);
		phoneNumTxtFld.setEditable(true);
		myInfoPanel.add(phoneNumTxtFld);
		
		// label prompting enter email
		JLabel emailLbl = new JLabel("Email:");
		emailLbl.setBounds(866, 280, 200, 29);
		emailLbl.setForeground(Color.WHITE);
		myInfoPanel.add(emailLbl);

		// email text field
		JTextField emailTxtFld = new JTextField();
		emailTxtFld.setToolTipText("Enter your business email");
		emailTxtFld.setBounds(866, 300, 200, 31);
		emailTxtFld.setEditable(true);
		myInfoPanel.add(emailTxtFld);
	
		// save button on the panel
		saveButton = new JButton("SAVE");
		saveButton.setToolTipText("SAVE CHANGES");
		
		// save the info and store when button pressed
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// store all of the data from text fields
				DatabaseManager.getInstance().storeMyInfo(firstNameTxtFld.getText(),
						lastNameTxtFld.getText(), businNameTxtFld.getText(), phoneNumTxtFld.getText(),
						addressTxtFld.getText(), emailTxtFld.getText());
				
			}
		});
		saveButton.setBounds(1162, 658, 117, 29);
		myInfoPanel.add(saveButton);
		
	}
	
	// build shipping info panel
	public static void initShippingInfo() {
		
		// shipping info panel attributes
		shippingInfoPanel = new JPanel();
		shippingInfoPanel.setBackground(blueTheme);
		shippingInfoPanel.setLayout(null);
		
		// set title label
		titleLabel = new JLabel("Shipping Info");
		titleLabel.setBounds(641, 5, 300, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		shippingInfoPanel.add(titleLabel);
		
		// label to prompt selection of customer for combo box
		JLabel selectCustLbl = new JLabel("Select Customer:");
		selectCustLbl.setBounds(166, 160, 200, 29);
		selectCustLbl.setForeground(Color.WHITE);
		shippingInfoPanel.add(selectCustLbl);


		// combo box of customers
		JComboBox selectCustCombo = new JComboBox();
		selectCustCombo.setBounds(166, 180, 200, 31);
		selectCustCombo.setToolTipText("Select customer to add shipping info");
		selectCustCombo.setEditable(false); // doesn't allow user to edit
		
		// populate customer combo box with database data
		DatabaseManager.getInstance().populateCustCombo(selectCustCombo);
		
		// adds blank item at first index (for checking)
		selectCustCombo.insertItemAt("", 0);

		// enable autocompletion on the combobox
		AutoCompletion.enable(selectCustCombo);

		// sets the selected item to first empty string (for checking)
		selectCustCombo.setSelectedItem("");

		selectCustCombo.setVisible(true);

		shippingInfoPanel.add(selectCustCombo);
		
		// table populated with customer info
		JTable custTbl = new JTable(){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // disable editing of all cells
			}
		};
		custTbl.getTableHeader().setReorderingAllowed(false); // disable column resizing/reordering;
		
		// columnn names for table
		Object colNames[] = { "Customer Name", "Address", "Company" };

		// table model for inventory table, populates customer table
		DefaultTableModel tMod = (DefaultTableModel) custTbl.getModel();
		tMod.setColumnIdentifiers(colNames); // sets column names of table

		tMod.setRowCount(0); // only allows one row to be visible in the table

		// populates table with customer info from selected customer
		selectCustCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				// the value of selected customer in Select Customer combo box
				String custSelection = String.valueOf(selectCustCombo.getSelectedItem());
				
				// populate and display customer table with customer selection
				DatabaseManager.getInstance().displayShipping(custTbl, tMod, custSelection);
			}
		});
		

		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(1149, 113, 130, 26);
		shippingInfoPanel.add(refreshBtn);
		
		// refreshes the table and components
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				DatabaseManager.getInstance().populateCustCombo(selectCustCombo); // re-populate combo box
				
				// enable autocompletion on the combobox
				AutoCompletion.enable(selectCustCombo);
				
				selectCustCombo.setVisible(true);
				
			}
		});
		
		
		
		// scroll pane for customer info text field
		scrollPane = new JScrollPane(custTbl);
		scrollPane.setBounds(166, 230, 900, 300);
		shippingInfoPanel.add(scrollPane);
		
		
	}
	
	// build billing frame
	public static void initBilling() {
		
		// billing panel attributes
		billingPanel = new JPanel();
		billingPanel.setBackground(blueTheme);
		billingPanel.setLayout(null);
		
		// set title label
		titleLabel = new JLabel("Billing");
		titleLabel.setBounds(641, 5, 158, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		billingPanel.add(titleLabel);
		
		// label prompting select invoice
		JLabel selectInvNum = new JLabel("Select Invoice #:");
		selectInvNum.setForeground(Color.WHITE);
		selectInvNum.setBounds(166, 92, 110, 16);
		billingPanel.add(selectInvNum);

		// Combo box of invoices
		JComboBox selectInvCombo = new JComboBox();
		selectInvCombo.setToolTipText("select invoice #");
		selectInvCombo.setBounds(166, 114, 225, 27);
		selectInvCombo.setEditable(true);
		
	
		// populate invoice combo
		DatabaseManager.getInstance().populateInvCombo((selectInvCombo));

		// adds blank item at first index (for checking)
		selectInvCombo.insertItemAt("", 0);

		// enable autocompletion on the combobox
		AutoCompletion.enable(selectInvCombo);

		// sets the selected item to first empty string (for checking)
		selectInvCombo.setSelectedItem("");
		
		selectInvCombo.setVisible(true);	// make sure the combobox is visible
		
		// label for amount due
		amtDueLbl = new JLabel("Amount Due:  $");
		amtDueLbl.setForeground(Color.WHITE);
		amtDueLbl.setBounds(166, 150, 105, 16);
		billingPanel.add(amtDueLbl);

		// text field for amount due
		JTextField amtDueFld = new JTextField();
		amtDueFld.setHorizontalAlignment(SwingConstants.TRAILING);
		amtDueFld.setToolTipText("amount due");
		amtDueFld.setColumns(10);
		amtDueFld.setBounds(267, 150, 130, 26);
		amtDueFld.setEditable(false);
		billingPanel.add(amtDueFld);
		
		// populates table with invoiceID from selected invoiceID
		selectInvCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				// the value of selected invoice in Select invoice combo box
				String invSelection = String.valueOf(selectInvCombo.getSelectedItem());
				String amtDueString = Double.toString(DatabaseManager.getInstance().getAmtDue(invSelection));
				amtDueFld.setText(amtDueString);

			}
		});
		billingPanel.add(selectInvCombo);
		
		// label of check number
		JLabel checkNumLbl = new JLabel("Check #:");
		checkNumLbl.setForeground(Color.WHITE);
		checkNumLbl.setBounds(866, 150, 90, 16);
		billingPanel.add(checkNumLbl);

		// text field for check number
		JFormattedTextField checkNumTxtFld = new JFormattedTextField(integerFormatter);
		checkNumTxtFld.setHorizontalAlignment(SwingConstants.TRAILING);
		checkNumTxtFld.setToolTipText("enter check number");
		checkNumTxtFld.setBounds(975, 150, 130, 26);
		billingPanel.add(checkNumTxtFld);
		
		// label for check amount
		JLabel checkAmtLbl = new JLabel("Check Amount: $");
		checkAmtLbl.setForeground(Color.WHITE);
		checkAmtLbl.setBounds(866, 170, 140, 16);
		billingPanel.add(checkAmtLbl);
		
		// text field for check number
		JFormattedTextField checkAmtTxtFld = new JFormattedTextField(getMaskFormatter("######.##"));
		checkAmtTxtFld.setHorizontalAlignment(SwingConstants.TRAILING);
		checkAmtTxtFld.setToolTipText("enter check amount");
		checkAmtTxtFld.setColumns(10);
		checkAmtTxtFld.setBounds(975, 170, 130, 26);
		billingPanel.add(checkAmtTxtFld);
		
		// save button on the panel
		saveButton = new JButton("SAVE");
		saveButton.setToolTipText("SAVE CHANGES");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Object invNum = selectInvCombo.getSelectedItem();
				String checkAmt = checkAmtTxtFld.getText();
				String checkNum = checkNumTxtFld.getText();
				
				// only continue check input if valid input and fields filled in completely/correctly
				if(checkNum.length() != 0 && !(checkAmt.equals("000000.00")) && !(invNum.equals(""))) {
					if(Double.parseDouble(checkAmt) <= DatabaseManager.getInstance().getAmtDue(invNum)) {
						DatabaseManager.getInstance().addCheck(checkNum, checkAmt, invNum);
						DatabaseManager.getInstance().setAmtPaid(invNum, Double.parseDouble(checkAmt));
					}
					else {
						String errorMsg = "Action cannot be performed.\nCheck amount is greater than amount due";
						JOptionPane.showMessageDialog(null, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else if(invNum.equals("")) {
					JOptionPane.showMessageDialog(null, "Please select an invoice.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(checkNum.length() == 0) {
					JOptionPane.showMessageDialog(null, "Please input a check #.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if((checkAmt.equals("000000.00"))) {
					JOptionPane.showMessageDialog(null, "Please input a check amount.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		saveButton.setBounds(1162, 658, 117, 29);
		billingPanel.add(saveButton);
		
		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(202, 658, 117, 29);
		billingPanel.add(refreshBtn);
		
		// refreshes the table and components
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				DatabaseManager.getInstance().populateInvCombo(selectInvCombo); // re-populate combo box
				
				// enable autocompletion on the combobox
				AutoCompletion.enable(selectInvCombo);
				
				selectInvCombo.setVisible(true);
				
			}
		});
		
		
	}
	
	// build save panel
	public static void initSave() {
		
		// print panel attributes
		savePanel = new JPanel();
		savePanel.setBackground(blueTheme);
		savePanel.setLayout(null);
		
		// set title label
		titleLabel = new JLabel("Save");
		titleLabel.setBounds(641, 5, 158, 31);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titleFont);
		savePanel.add(titleLabel);
		
		
		// label prompting select invoice
		JLabel selectInvNumLbl = new JLabel("Select Invoice #:");
		selectInvNumLbl.setForeground(Color.WHITE);
		selectInvNumLbl.setBounds(166, 92, 123, 16);
		savePanel.add(selectInvNumLbl);

		// Combo box of invoices
		JComboBox selectInvCombo = new JComboBox();
		selectInvCombo.setToolTipText("select invoice #");
		selectInvCombo.setBounds(166, 114, 225, 27);
		selectInvCombo.setEditable(false);
		
		// populate combobox with invoice numbers
		DatabaseManager.getInstance().populateInvCombo(selectInvCombo);

		// adds blank item at first index (for checking)
		selectInvCombo.insertItemAt("", 0);

		// enable autocompletion on the combobox
		AutoCompletion.enable(selectInvCombo);

		// sets the selected item to first empty string (for checking)
		selectInvCombo.setSelectedItem("");
		
		selectInvCombo.setVisible(true);	// make sure the combobox is visible
		
		savePanel.add(selectInvCombo);
		
		// column names for table
		String[] colNames = {"Product #", "Product Name", "Quantity", "Unit Price", "Subtotal"};
	
		// invoice table to be printed (populates with invoice table)
		JTable saveInvTbl = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // disable editing of all cells
			}
		};
		saveInvTbl.getTableHeader().setReorderingAllowed(false); // disable column resizing/reordering
		
		// table model for invoice contents table, to be populated with data
		DefaultTableModel tMod = (DefaultTableModel) saveInvTbl.getModel();
		tMod.setColumnIdentifiers(colNames); // sets column names of invoices table
		
		// scroll pane for table
		scrollPane = new JScrollPane(saveInvTbl);
		scrollPane.setBounds(166, 145, 1113, 393);
		savePanel.add(scrollPane);
			
		// label for amount paid
		amtPaidLbl = new JLabel("Amount Paid:  $");
		amtPaidLbl.setForeground(Color.WHITE);
		amtPaidLbl.setBounds(166, 635, 105, 16);
		savePanel.add(amtPaidLbl);

		// text field for amount paid (updates with invoice selection)
		JTextField amtPaidTxtFld = new JTextField();
		amtPaidTxtFld.setHorizontalAlignment(SwingConstants.TRAILING);
		amtPaidTxtFld.setToolTipText("amount paid");
		amtPaidTxtFld.setColumns(10);
		amtPaidTxtFld.setBounds(267, 630, 130, 26);
		amtPaidTxtFld.setEditable(false);
		savePanel.add(amtPaidTxtFld);

		// label for amount due
		amtDueLbl = new JLabel("Amount Due:  $");
		amtDueLbl.setForeground(Color.WHITE);
		amtDueLbl.setBounds(166, 663, 105, 16);
		savePanel.add(amtDueLbl);

		// text field for amount due
		// TODO calculate and auto populate amount due
		JTextField amtDueTxtFld = new JTextField();
		amtDueTxtFld.setHorizontalAlignment(SwingConstants.TRAILING);
		amtDueTxtFld.setToolTipText("amount due");
		amtDueTxtFld.setColumns(10);
		amtDueTxtFld.setBounds(267, 658, 130, 26);
		amtDueTxtFld.setEditable(false);
		savePanel.add(amtDueTxtFld);

		// label for total amount
		totalLbl = new JLabel("Total:  $");
		totalLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		totalLbl.setForeground(Color.WHITE);
		totalLbl.setBounds(1058, 635, 91, 16);
		savePanel.add(totalLbl);


		// text field to display[ total amount
		JTextField totalFld = new JTextField();
		totalFld.setHorizontalAlignment(SwingConstants.TRAILING);
		totalFld.setToolTipText("total");
		totalFld.setColumns(10);
		totalFld.setBounds(1149, 630, 130, 26);
		totalFld.setEditable(false);
		savePanel.add(totalFld);

		// refresh button
		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(1149, 113, 130, 26);
		savePanel.add(refreshBtn);

		// refreshes the table and components
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				// populate combobox w/ saved invoices
				DatabaseManager.getInstance().populateInvCombo(selectInvCombo);

				// enable autocompletion on the combobox
				AutoCompletion.enable(selectInvCombo);

				selectInvCombo.setVisible(true);

			}
		});
		
		
		// save button on the panel
		JButton saveButton = new JButton("SAVE");
		saveButton.setToolTipText("press to download selected invoice");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Object invNum = selectInvCombo.getSelectedItem();
				
				WriteFile.writeTextFile(saveInvTbl, invNum);
				
			}
		});
		saveButton.setBounds(1162, 658, 117, 29);
		savePanel.add(saveButton);
		
		// update table view when certain invoice number selected
		selectInvCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tMod.setRowCount(0); // reset the table before adding new row
				
				// the value of selected customer in Select Customer combo box
				String invSelection = String.valueOf(selectInvCombo.getSelectedItem());
				
				// populate and display table with invoice selection
				DatabaseManager.getInstance().displayInvoiceTable(tMod, saveInvTbl, invSelection);
				
				// set total text field
				totalFld.setText(String.valueOf(calcTotal(saveInvTbl))); // display total on new invoice frame
				totalFld.setVisible(true); // make sure total is visible
				
				// set amtDue text field
				double amtDue = DatabaseManager.getInstance().getAmtDue(selectInvCombo.getSelectedItem());    
				String amtDueString = String.valueOf(amtDue);
				amtDueTxtFld.setText(amtDueString);
				
				// set amtDue text field
				double amtPaid = DatabaseManager.getInstance().getAmtPaid(selectInvCombo.getSelectedItem());    
				String amtPaidString = String.valueOf(amtPaid);
				amtPaidTxtFld.setText(amtPaidString);
				

			}
		});
		
	}
	
	// set up integer format
	public static void setIntegerFormat(NumberFormatter  integerFormatter, NumberFormat integerFormat) {
		
		integerFormat.setGroupingUsed(false); // doesn't allow commas in input, allows backspace
		integerFormatter.setValueClass(Integer.class); // sets valid input to integers
		integerFormatter.setMinimum(1); // min value accepted
		integerFormatter.setMaximum(Integer.MAX_VALUE); // max value accepted
		integerFormatter.setAllowsInvalid(false); // doesn't allow invalid input
		integerFormatter.setCommitsOnValidEdit(true);
		integerFormatter.setOverwriteMode(true);
	}

	// gets maskformatter for double values
	public static MaskFormatter getMaskFormatter(String formatThis) {
	    MaskFormatter mask = null;
	    try {
	        mask = new MaskFormatter(formatThis);
	        mask.setPlaceholderCharacter('0');
	    }catch (ParseException ex) {
	        ex.printStackTrace();
	    }
	    return mask;
	}
	
	
	// calculate total of the invoice
	public static double calcTotal(JTable table) {
		
		double getSub;
		double total = 0;
		
		// calculate total of the invoice
		for (int i = 0; i < table.getRowCount(); i++) {

			// gets the subtotal from the table
			getSub = Double.parseDouble(table.getValueAt(i, 4).toString());
			
			// adds the subtotals
			total += getSub;

		}
		return total;
	}
	
	// returns true if date textfield had valid input
	public static boolean dateValidInput(JFormattedTextField field) {
		
		boolean isValid = false;
	
		String date = field.getText();
		
		// return false if the date field isn't completely filled out
		if(date.equals("00/00/0000")) {
			isValid = false;
		}
		else if(date.startsWith("00/")) {
			isValid = false;
		}
		else if(date.contains("/00/")) {
			isValid = false;
		}
		else if(date.endsWith("/0000")) {
			isValid = false;
		}
		else if(date.contains("/00")) {
			isValid = false;
		}
		else {
			isValid = true;
		}
		
		return isValid;
	}
	
	// displays a popup frame of the selected invoice
	public static void viewInvoice(String invNum, int index) {
		
		JTextField invNumFld = new JTextField();
		invNumFld.setEditable(false);
		invNumFld.setText(invNum); // set text to selected invoice number
		invNumFld.setBounds(46, 40, 130, 26);
		
		String[] columnNames = {"Product #", "Product Name", "Quantity", "Unit Price", "Subtotal"};
		
		JTable invTbl = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // disable editing of all cells
			}
		};
		invTbl.getTableHeader().setReorderingAllowed(false); // disable column resizing/reordering
		
		// table model for invoices table, to be populated with data
		DefaultTableModel tMod2 = (DefaultTableModel) invTbl.getModel();
		tMod2.setColumnIdentifiers(columnNames);	// sets column names of invoice table
		
		// populate and display table with invoice selection
		DatabaseManager.getInstance().displayInvoiceTable(tMod2, invTbl, invNum);
		
		JScrollPane scrollPane2 = new JScrollPane(invTbl);
		scrollPane2.setBounds(46, 78, 576, 171); // sets scroll pane view
		
		String frameTitle = "Invoice " + invNum;
		
		JFrame viewInvFrm = new JFrame(frameTitle);
		viewInvFrm.setSize(664, 406);
		viewInvFrm.getContentPane().setLayout(null); // absolute layout
		viewInvFrm.setResizable(false); // not resizable
		
		viewInvFrm.add(invNumFld);
		viewInvFrm.add(scrollPane2);
		
		JLabel invLbl = new JLabel("INVOICE:");
		invLbl.setBounds(49, 26, 61, 16);
		viewInvFrm.add(invLbl);

		JTextField totalFld = new JTextField();
		totalFld.setBounds(499, 276, 130, 26);
		totalFld.setEditable(false);
		totalFld.setText(String.valueOf(calcTotal(invTbl)));
		viewInvFrm.add(totalFld);
		
		JTextField amtDueFld = new JTextField();
		amtDueFld.setBounds(129, 276, 130, 26);
		amtDueFld.setEditable(false);
		amtDueFld.setText(String.valueOf(DatabaseManager.getInstance().getAmtDue(invNum)));
		viewInvFrm.add(amtDueFld);
		
		JLabel totalLbl = new JLabel("Total:  $");
		totalLbl.setBounds(439, 281, 61, 16);
		viewInvFrm.add(totalLbl);
		
		JButton okBtn = new JButton("OK");
		okBtn.setBounds(509, 314, 117, 29);
		viewInvFrm.add(okBtn);
		
		// dispose frame when ok button clicked
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewInvFrm.dispose();
			}
		});

		JLabel amtDueLbl = new JLabel("Amt Due:   $");
		amtDueLbl.setBounds(49, 281, 83, 16);
		viewInvFrm.add(amtDueLbl);	
		
		JTextField dateFld = new JTextField();
		dateFld.setBounds(498, 40, 124, 26);
		dateFld.setEditable(false);
		dateFld.setText(DatabaseManager.getInstance().getInvDate(invNum));
		viewInvFrm.add(dateFld);

		JLabel dateLbl = new JLabel("Date:");
		dateLbl.setBounds(455, 45, 35, 16);
		viewInvFrm.add(dateLbl);
		
		JLabel customerLbl = new JLabel("Customer:");
		customerLbl.setBounds(49, 319, 65, 16);
		viewInvFrm.add(customerLbl);
		
		JTextField customerFld = new JTextField();
		customerFld.setBounds(129, 314, 166, 26);
		customerFld.setEditable(false);
		customerFld.setText((DatabaseManager.getInstance().getInvCustomer(invNum)));
		viewInvFrm.add(customerFld);
		
		
		viewInvFrm.setVisible(true);
		
	}
	
	// returns the rows with the value wanted
	public static int getRowAt(JTable table, int colNum, Object value) {
		
		int rowNum = 0;
		
		// iterate table check if the value in cell is the one that is wanted
		for(int i = 0; i < table.getRowCount(); i++) {
			if(table.getValueAt(i, colNum).equals(value)) {
				rowNum = i;
				break;
			}
		}
		
		return rowNum;
		
	}
	
}