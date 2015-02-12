
//Final Project : Customer Maintenance Application 
// Purpose: This application is for displaying, adding, deleting and editing a customer from a CustomerDataBase


import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;


public class CreateCustomerDB {

	public static void main(String[] args) {
		//create named constant for the URL 
		final String DB_URL = "jdbc:derby:CustomerDB";
		
		try 
		{
			//Create connection to the DB
			Connection connect = DriverManager.getConnection(DB_URL);
			
		
			Statement statement = connect.createStatement();
						
			
			connect.close();

		}
		catch (Exception ex )
		{
			System.out.println(ex.getMessage());
		}
				

	}

}
