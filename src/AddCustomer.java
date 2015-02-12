
//Final Project : Customer Maintenance Application 
// Purpose: This application is for displaying, adding, deleting and editing a customer from a CustomerDataBase



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class AddCustomer extends JFrame
{
	
	private JPanel mstatuspanel; 
	private JLabel maritalstatuslbl; 
	private JComboBox maritalstatus; 
	private JPanel namepanel; 
	private JLabel firstnamelbl;
	private JPanel firstnmepanel;
	private JTextField firstnametxt;
	private JLabel middlenamelabl;
	private JTextField middlenametxt;
	private JPanel middlenmepanel;
	private JLabel lastnamelbl;
	private JTextField lastnametxt;
	private JPanel lastnamepanel; 
	private genderPanel gender;
	private PaymentmethodsPanel payment; 
	private JButton savebutton;
	private JPanel buttonpanel; 
	private String [] mstatus = { "Married", "Unmarried"};
	private final int WINDOW_WIDTH = 400;
	private final int WINDOW_HEIGHT = 200;
	private String custnum;
	private CustomerList ownerFrame;
	private int customereditID;

	private JPanel addresspanel;
	private JLabel addresslbl;
	private JTextField addresstxt;
	private JLabel citylbl;
	private JTextField citytxt;
	private JLabel statelbl;
	private JTextField statetxt;
	private JLabel ziplbl;
	private JTextField ziptxt;
	
	private JPanel topPanel; 
	private JPanel leftPanel; 
	private JPanel centerPanel; 
	private JPanel rightPanel; 
	private JPanel bottomPanel; 
	
	public AddCustomer()
	{
		setTitle("Add Customers");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//create Borderlayout Manager 
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		
		setLayout(new BorderLayout());
		topPanel = new JPanel();
		leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		rightPanel = new JPanel();
		bottomPanel = new JPanel();

		
		
		buildPanel();
		buildstatusPanel();
		buildAddressPanel();
		
		//create custom panels .
		gender = new genderPanel();
		payment = new PaymentmethodsPanel(); 

		centerPanel.add(gender);
		centerPanel.add(mstatuspanel);
		
		rightPanel.add(payment);
		leftPanel.add(addresspanel);
		
		bottomPanel.add(buttonpanel);
		
		add(topPanel, BorderLayout.NORTH);
		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
		add(bottomPanel, BorderLayout.SOUTH);
		pack();
		setVisible(true); 
		setLocationRelativeTo(null);

		
	}

	
	public AddCustomer(int customrID)
	{
		this();
		customereditID = customrID; 
		
		SetDataForCustomer();
	}
	
	public void setOwnerFrame(CustomerList owner)
	{
		ownerFrame = owner;
	}
	private void buildAddressPanel()
	{
		addresspanel = new JPanel(new GridLayout(4, 2,10,15));
		
		addresslbl = new JLabel("Address Line");
		addresstxt = new JTextField();
		
		citylbl = new JLabel("City");
		citytxt = new JTextField();
		
		statelbl = new JLabel("State");
		statetxt = new JTextField();
		
		ziplbl = new JLabel("Zip Code");
		ziptxt = new JTextField();
		
		addresspanel.add(addresslbl);
		addresspanel.add(addresstxt);
		
		addresspanel.add(citylbl);
		addresspanel.add(citytxt);
		
		addresspanel.add(statelbl);
		addresspanel.add(statetxt);
		addresspanel.add(ziplbl);
		addresspanel.add(ziptxt);
		
		addresspanel.setBorder(BorderFactory.createTitledBorder("Address"));
		addresspanel.setPreferredSize(new Dimension(250,150));
	}
	
	private void buildPanel()
	{
		
		firstnamelbl = new JLabel("First Name");
		firstnametxt = new JTextField(15);
		
		middlenamelabl = new JLabel("MIddleName");
		middlenametxt = new JTextField(15);
		
		lastnamelbl = new JLabel("Last Name");
		lastnametxt = new JTextField(15);
		
		savebutton= new JButton("Save");
		
		buttonpanel = new JPanel(); 
	
		savebutton.addActionListener(new SaveButtonListener());

		firstnmepanel = new JPanel();
		middlenmepanel = new JPanel();
		lastnamepanel = new JPanel();
	
		firstnmepanel.add(firstnamelbl);
		firstnmepanel.add(firstnametxt);
		middlenmepanel.add(middlenamelabl);
		middlenmepanel.add(middlenametxt);
		lastnamepanel.add(lastnamelbl);
		lastnamepanel.add(lastnametxt);
		
		topPanel.setLayout(new GridLayout(1, 3));
		topPanel.add(firstnmepanel);
		topPanel.add(middlenmepanel);
		topPanel.add(lastnamepanel);
		
		buttonpanel.add(savebutton);
		
	}
	private void buildstatusPanel()
	{
		mstatuspanel = new JPanel(); 
		maritalstatus = new JComboBox(mstatus);
		maritalstatuslbl = new JLabel("Marital Status");
		
		mstatuspanel.add(maritalstatuslbl);
		mstatuspanel.add(maritalstatus);
		
	}
	
	private void SetDataForCustomer()
	{
		
		if(customereditID > 0)
		{
			
			Customer editCustomer = new Customer(customereditID);
		
			
			firstnametxt.setText(editCustomer.getFirstName());
			middlenametxt.setText(editCustomer.getMiddleName());
			lastnametxt.setText(editCustomer.getLastName());
			addresstxt.setText(editCustomer.getCustAddress());
			citytxt.setText(editCustomer.getCustCity());
			statetxt.setText(editCustomer.getState());
			ziptxt.setText(editCustomer.getZipCode());
			
			if(editCustomer.getMaritalStatus().equals("M"))
			{
				maritalstatus.setSelectedIndex(0);
			}
			else
			{
				maritalstatus.setSelectedIndex(1);
			}
			gender.setSelectedGender(editCustomer.getGender());
			payment.setSelectedPaymentmethods(editCustomer.getpaymentMethods());
		}
	}
	private String getSelectedMaritalStatus()
	{
		if(maritalstatus.getSelectedIndex() == 0)
		{
			return "M";
		}
		else
		{
			return "N";
		}
			
	}
	private class SaveButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			// Creating  Customer object
			Customer customer = new Customer();
			customer.setFirstName(firstnametxt.getText());
			customer.setMiddleName(middlenametxt.getText());
			customer.setLastName(lastnametxt.getText());
			customer.setAddress(addresstxt.getText());
			customer.setCustCity(citytxt.getText());
			customer.setState(statetxt.getText());
			customer.setZipcode(ziptxt.getText());
			customer.setMaritalStatus(getSelectedMaritalStatus());
			customer.setGender(gender.getSelectedGender());
			customer.setPaymentMethods(payment.getSelectedPaymentmethods());
		
			try 
			{
				customer.setCustomerId(customereditID);
				customer.SaveCustomerToDB();
				if(ownerFrame != null)
				{
					ownerFrame.RefreshData();
				}
				dispose();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
			}
			
			
			
		}
		
	}
	



}
