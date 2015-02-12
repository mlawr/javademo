
//Final Project : Customer Maintenance Application 
// Purpose: This application is for displaying, adding, deleting and editing a customer from a CustomerDataBase


import java.sql.*;

import javax.swing.DefaultButtonModel;

public class Customer {
	final String DB_URL = "jdbc:derby:CustomerDB";
	private int customerid;
	private String lastName;
	private String middleName;
	private String firstName;
	private String gender;
	private String custaddress;
	private String city;
	private String state;
	private String zipcode;
	private String maritalstatus;
	private String[] paymentMethods;

	public Customer() {

	}

	public Customer(int custID) {
		customerid = custID;
		
		SetCustomerFromDB();
	}

	public Customer(String lname, String mname, String firstnme, String g,
			String ad, String custcity, String custstate, String z, String stat) {
		lastName = lname;
		middleName = mname;
		firstName = firstnme;
		gender = g;
		custaddress = ad;
		city = custcity;
		state = custstate;
		zipcode = z;
		maritalstatus = stat;

	}

	public void setCustomerId(int custID) {
		customerid = custID;
	}

	public int getCustomerId() {
		return customerid;
	}

	public void setLastName(String l) {
		lastName = l;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String f) {
		firstName = f;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setMiddleName(String mName) {
		middleName = mName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setAddress(String address) {
		custaddress = address;

	}

	public String getCustAddress() {
		return custaddress;
	}

	public void setCustCity(String custc) {
		city = custc;

	}

	public String getCustCity() {
		return city;

	}

	public void setState(String st) {
		state = st;
	}

	public String getState() {
		return state;

	}

	public void setZipcode(String zip) {
		zipcode = zip;
	}

	public String getZipCode() {
		return zipcode;
	}

	public void setMaritalStatus(String s) {
		maritalstatus = s;
	}

	public String getMaritalStatus() {
		return maritalstatus;
	}

	public void setGender(String s) {
		gender = s;
	}

	public String getGender() {
		return gender;
	}

	public void setPaymentMethods(String[] pms) {
		paymentMethods = pms;
	}

	public String[] getpaymentMethods() {
		return paymentMethods;
	}

	public void SetCustomerFromDB() {

		

		if (customerid > 0)

		{
			try {

				// Getting the Data from DB
				Connection connect = DriverManager.getConnection(DB_URL);
				
				Statement stment = connect.createStatement();

				String sqlstring = "SELECT CustomerId, FirstName,MiddleName,LastName,"
						+ "AddressLine1,City,State,ZipCode,Gender,MaritalStatus FROM Customer "
						+ "WHERE CustomerID =" + customerid;

				ResultSet result;
				result = stment.executeQuery(sqlstring);

				if (result.next()) {
					lastName = result.getString("LastName");
					middleName = result.getString("MiddleName");
					firstName = result.getString("FirstName");
					gender = result.getString("Gender");
					custaddress = result.getString("AddressLine1");
					city = result.getString("City");
					state = result.getString("State");
					zipcode = result.getString("ZipCode");
					maritalstatus = result.getString("MaritalStatus");
				}

				result.close();
				Statement statement = connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				String pmSQL = "SELECT CustomerId, PaymentMethod FROM CustomerPaymentMethod WHERE CustomerId = "
						+ customerid;
				ResultSet pmresult;
				pmresult = statement.executeQuery(pmSQL);

				
				pmresult.last();
				int numRows = pmresult.getRow();
				pmresult.first();

				// Set the paymentMethods array from the resultset

				paymentMethods = new String[numRows];

				// loop the resultset row
				for (int i = 0; i < paymentMethods.length; i++) {
					paymentMethods[i] = pmresult.getString(2);
					pmresult.next();
				}

				pmresult.close();
				stment.close();
				statement.close();
				connect.close();

			} catch (Exception ex) {
				System.out.println("ERROR: " + ex.getMessage());
			}

		}

	

	}

	public void SaveCustomerToDB() throws Exception {
		ValidateData();
		if (customerid > 0) {
			UpdateCustomerTable();
		} else {
			InsertCustomertoDB();
		}
	}
	private void ValidateData() throws Exception
	{
		if(firstName.length() == 0)
		{
			throw new Exception("First Name is Required.");
		}
		if(lastName.length() == 0)
		{
			throw new Exception("Last Name is required.");
		}
		if(gender.length() == 0)
		{
			throw new Exception("Gender is required.");
		}
		if(maritalstatus.length() == 0)
		{
			throw new Exception("Marital Status is required.");
		}
		if(custaddress.length() == 0)
		{
			throw new Exception("Address is required.");
		}
		if(city.length() == 0)
		{
			throw new Exception("City is required.");
		}
		if(state.length() <= 1)
		{
			throw new Exception("State is required and should be at least 2 characters.");
		}
		if(zipcode.length() != 5)
		{
			throw new Exception("Zipcode is required and should be 5 characters.");
		}
		if(paymentMethods.length < 1)
		{
			throw new Exception("Atleast 1 payment method is required.");
		}

	}
	public void InsertCustomertoDB() throws Exception {

		// Connect to CustomerDB
		try {
			Connection connect = DriverManager.getConnection(DB_URL);

			// Create Statement from connection
			Statement stmt = connect.createStatement();

			// Create the INSERT statement
			String sqlStr = "INSERT INTO Customer (FirstName,MiddleName,LastName,Gender,MaritalStatus,AddressLine1,City,State,ZipCode) VALUES"
					+ "('"
					+ firstName
					+ "','"
					+ middleName
					+ "','"
					+ lastName
					+ "','"
					+ gender
					+ "','"
					+ maritalstatus
					+ "','"
					+ custaddress
					+ "','"
					+ city
					+ "','"
					+ state
					+ "','"
					+ zipcode + "')";

			
			stmt.executeUpdate(sqlStr, Statement.RETURN_GENERATED_KEYS);

			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int newCustId = rs.getInt(1);

				
				if (paymentMethods != null && paymentMethods.length > 0) {
					for (int i = 0; i < paymentMethods.length; i++) {
						
						String pmSQL = "INSERT INTO CustomerPaymentMethod VALUES"
								+ "("
								+ Integer.toString(newCustId)
								+ ",'"
								+ paymentMethods[i] + "')";

					

						stmt.execute(pmSQL);
					}
				}
			}
			rs.close();
			
			stmt.close();
			connect.close();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
			throw ex;
		}

	}

	public void UpdateCustomerTable() throws Exception {
		try {
			Connection conn = DriverManager.getConnection(DB_URL);

			
			Statement st = conn.createStatement();

		
			String sqlStatemnt;
			sqlStatemnt = "UPDATE Customer SET FirstName = '" + firstName + "'"
					+ ", MiddleName = '" + middleName + "'" + ", LastName = '"
					+ lastName + "'" + ",Gender = '" + gender + "'"
					+ ",MaritalStatus ='" + maritalstatus + "'"
					+ ",AddressLine1 ='" + custaddress + "'" + ",City ='"
					+ city + "'" + ",State ='" + state + "'" + ",ZipCode='"
					+ zipcode + "'" + " WHERE CustomerID =" + customerid;
		
			st.executeUpdate(sqlStatemnt);

			
			String pmDSQL = "DELETE FROM CustomerPaymentMethod  WHERE CustomerID ="
					+ customerid;
			st.executeUpdate(pmDSQL);

			// If any payment methods selected, loop and insert the
			// customerpaymentmethod records
			if (paymentMethods != null && paymentMethods.length > 0) {
				for (int i = 0; i < paymentMethods.length; i++) {
					
					String pmSQL = "INSERT INTO CustomerPaymentMethod VALUES"
							+ "(" + Integer.toString(customerid) + ",'"
							+ paymentMethods[i] + "')";

					

					st.executeUpdate(pmSQL);
				}
			}

			
			st.close();
			conn.close();

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
			throw ex;

		}

	}

	public void DeleteCustomer() {
		try {
			Connection connectdb = DriverManager.getConnection(DB_URL);

			Statement stment = connectdb.createStatement();

			
			String sqlstmt = "DELETE from CustomerPaymentMethod "
					+ "WHERE CustomerID = " + customerid;
			int rows;
			rows = stment.executeUpdate(sqlstmt);

			
			String sqlstmt1 = "DELETE from Customer " + "WHERE CustomerID = "
					+ customerid;

			rows = stment.executeUpdate(sqlstmt1);

			

			stment.close();
			connectdb.close();

			System.out.println(rows + "row (s) deleted.");

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
	}

}
