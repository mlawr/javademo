//Name: 	Mercy Lawrence 
//Final Project : Customer Maintenance Application 
// Purpose: This application is for displaying, adding, deleting and editing a customer from a CustomerDataBase
//Date: 12/16/2013

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;


public class CustomerMaintance extends JPanel
{
	private JPanel cutomerPanel;
	private JButton addButton;
	private JButton editButton; 
	private JButton deleteButton; 
	private JPanel  buttonPanel; 
	private JList cutomerList; 
	private JPanel selectedcCustomer;
	private final int WINDOW_WIDTH = 400;
	private final int WINDOW_HEIGHT = 200;
	
	
	//constructor
	public CustomerMaintance()
	{
		
		setLayout(new BorderLayout());
		
	  //build button panel and selected customer panel 
		buildButtonPanel(); 
		
	  //add panels to the content pane
		add(buttonPanel,BorderLayout.SOUTH); 
		
	 //pack and Display the window 
		
		
		setVisible(true);
		
		
	}
	
	private void buildButtonPanel()
	{
		//create a panel to hold buttons
		buttonPanel = new JPanel();
		
		// create   buttons
		addButton = new JButton("Add");
		editButton = new JButton ("Edit");
		deleteButton = new JButton("Delete");
		
		// add buttons to the panel
		
		buttonPanel.add(addButton);
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton); 
		
		
		
	}
	
	
}
	

	








