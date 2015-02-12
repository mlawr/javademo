
//Final Project : Customer Maintenance Application 
// Purpose: This application is for displaying, adding, deleting and editing a customer from a CustomerDataBase


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.derby.iapi.sql.dictionary.TableDescriptor;

public class CustomerList extends JFrame {

	private final int WIDTH = 900;
	private final int HEIGHT = 400;
	private JTable table;
	private JPanel panel;
	private JPanel headerpanel;
	private JScrollPane scrollpane;
	private JButton addbutton;
	private JButton editbutton;
	private JButton deletebutton;
	private JLabel headerlabel;
	private String[] columNames;
	private String[][] tbleData;
	private JFrame thisFrame;

	// private Customer[] customers;

	public CustomerList() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		thisFrame = this;
		getCustomers();

		table = new JTable(tbleData, columNames);

		// put the table in a scroll pane
		scrollpane = new JScrollPane(table);

		scrollpane.setBorder(new EmptyBorder(20, 20, 20, 20));

		setLayout(new BorderLayout());
		setTitle("Customer Maintenance");

		add(scrollpane, BorderLayout.CENTER);
		setHeaderPanel();
		setbuttonPanel();

		setLocation(100, 200);
		// set the size
		setSize(WIDTH, HEIGHT);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //forums.oracle.com
		
		// pack();
		setVisible(true);
	}

	public void RefreshData() {
		getCustomers();
		table.setModel(new DefaultTableModel(tbleData, columNames)); // Reference
																		// StackOverFlow.com
	}

	public void setbuttonPanel() {
		addbutton = new JButton("Add");
		editbutton = new JButton("Edit");
		deletebutton = new JButton("Delete");
		panel = new JPanel();
		panel.add(addbutton);
		panel.add(editbutton);
		panel.add(deletebutton);
		add(panel, BorderLayout.SOUTH);

		// register action listeners with the buttons
		addbutton.addActionListener(new AddButtonListener());
		editbutton.addActionListener(new EditButtonListener());
		deletebutton.addActionListener(new DeleteButtonListener());

	}

	public void setHeaderPanel() {
		headerlabel = new JLabel("Customer Maintenance");
		headerpanel = new JPanel();
		headerpanel.setSize(WIDTH, HEIGHT);
		headerpanel.setAlignmentY(CENTER_ALIGNMENT);
		headerpanel.add(headerlabel);
		add(headerlabel, BorderLayout.NORTH);

	}

	private class AddButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Calling the AddCustomer frame
			AddCustomer addcustomer = new AddCustomer();
			addcustomer.setOwnerFrame((CustomerList) thisFrame);
		}

	}

	private class EditButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// Getting selected row and finding the customerID
			int selectRowIndex = table.getSelectedRow();
			if (selectRowIndex < 0) {
				JOptionPane.showMessageDialog(null,
						"Please select a row for editing");
			} else {

				// Calling the AddCustomer with the selected CustomerID

				String selectedcustid;
				int custmID;
				selectedcustid = (String) table.getModel().getValueAt(
						selectRowIndex, 0);// reference StackOVerflow.com
				custmID = Integer.parseInt(selectedcustid);

				AddCustomer editcustomer = new AddCustomer(custmID);
				editcustomer.setOwnerFrame((CustomerList) thisFrame);// reference
																		// stackOverflow.com
			}

		}

	}

	private class DeleteButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Getting selected row and finding the customerID
			int selectRowIndex = table.getSelectedRow();
			if (selectRowIndex < 0) {
				JOptionPane.showMessageDialog(null,
						"Please select a row for editing");
			} else {

				// Creating the Customer object with the customerID
				String selectedcustid;
				int custmID;
				selectedcustid = (String) table.getModel().getValueAt(
						selectRowIndex, 0);// reference StackOVerflow.com
				custmID = Integer.parseInt(selectedcustid);
				Customer cust = new Customer(custmID);

				// Deleting the customer from Database
				cust.DeleteCustomer();
				RefreshData();
			}

		}
	}

	public void getCustomers() {
		final String DB_URL = "jdbc:derby:CustomerDB";

		try {
			// create connection to the database
			Connection conn = DriverManager.getConnection(DB_URL);

			// create a statement object
			Statement statement = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			// execute the query

			ResultSet resultset = statement
					.executeQuery("SELECT CustomerId, FirstName,MiddleName,LastName,AddressLine1,City,State,ZipCode,Gender,MaritalStatus FROM Customer");

			// Get the number of rows
			resultset.last();
			int numRows = resultset.getRow();
			resultset.first();

			// get the result set metadata.
			ResultSetMetaData meta = resultset.getMetaData();

			columNames = new String[] { "Customer ID", "First Name",
					"Middle Name", "Last Name", "Address", "City", "State",
					"Zip Code", "Gender", "Marital Status" };

			// create a two dimensional array for the table data
			tbleData = new String[numRows][meta.getColumnCount()];

			// store col in the table array

			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < meta.getColumnCount(); col++) {
					String colValue = resultset.getString(col + 1);

					// Converting Gender M and F to Male and Female
					if (col == 8) {
						if (colValue.equals("M")) {
							colValue = "Male";
						} else {
							colValue = "Female";
						}
					}
					// Converting Marital Status Married and UnMarried

					if (col == 9) {
						if (colValue.equals("M")) {
							colValue = "Married";
						} else {
							colValue = "Unmarried";
						}
					}
					tbleData[row][col] = colValue;
				}

				// go the next row in the Resultset
				resultset.next();

			}

			// close the statenment and connection
			statement.close();

			conn.close();

		} catch (Exception ex) {
			System.out.println("ERROR : " + ex.getMessage());
		}

	}

}
