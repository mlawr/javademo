//Name: 	Mercy Lawrence 
//Final Project : Customer Maintenance Application 
// Purpose: This application is for displaying, adding, deleting and editing a customer from a CustomerDataBase
//Date: 12/16/2013

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;


public class PaymentmethodsPanel extends JPanel
{
	private JCheckBox ach;
	private JCheckBox check;
	private JCheckBox wiretransfer;
	private JCheckBox creditCard;
	private JCheckBox debitCard;
	
	
	
	public PaymentmethodsPanel ()
	{
		//create a GridLayout manager with five rows and one column
		setLayout(new GridLayout(5,1));
		setPreferredSize(new Dimension(150, 150));

		//create the check boxes
		ach = new JCheckBox("ACH");
		check = new JCheckBox("Check");
		wiretransfer = new JCheckBox("Wire");
		creditCard = new JCheckBox("Credit Card");
		debitCard = new JCheckBox("Debit Card");
		
		
		//add border around the panel
		setBorder(BorderFactory.createTitledBorder("Payment Methods"));
		
		//add checkboxes to the panel 
		add(ach);
		add(check);
		add(wiretransfer);
		add(creditCard);
		add(debitCard);
		
	}
	public String[] getSelectedPaymentmethods()
	{
		ArrayList<String> myList = new ArrayList<String>();
		
		//Adding the values selected to the arraylist
		
		if (ach.isSelected())
		{
			myList.add("A");
		}
		if(check.isSelected())
		{
			myList.add("C");
		}
		if(wiretransfer.isSelected())
		{
			myList.add("W");
		}
		if(creditCard.isSelected())
		{
			myList.add("R");
			
		}
		if(debitCard.isSelected())

		{
			myList.add("D");
		}
		//Convert the arraylist to an array
		String[] selections = new String[myList.size()];
		
		//Adding the values from ArrayList Array
		for(int i = 0; i< selections.length; i++)
		{
			selections[i] = myList.get(i);
		}
		
		return selections;
	}

	public void setSelectedPaymentmethods(String[] selections)
	{
		//Looping through the array to mark the selected checkboxes
		if(selections != null)
		{
			for(int i = 0; i<selections.length;i++)
			{
				if(selections[i] .equals("A"))
				{
					ach.setSelected(true);
				}
				if(selections[i].equals("C"))
				{
					check.setSelected(true);
				}
				if(selections[i].equals("W"))
				{
					wiretransfer.setSelected(true);
				}
				if(selections[i].equals("R"))
				{
					creditCard.setSelected(true);
				}
				if(selections[i].equals("D"))
				{
					debitCard.setSelected(true);
				}
			}
		}		
	}

}
