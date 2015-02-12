
//Final Project : Customer Maintenance Application 
// Purpose: This application is for displaying, adding, deleting and editing a customer from a CustomerDataBase


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class genderPanel extends JPanel 
{
	private JRadioButton male;
	private JRadioButton female; 
	private ButtonGroup btngroup;
	
	
	public genderPanel ()
	{
		//set Gridlayout with two rows and one column
		setLayout(new GridLayout(2,1));
		setMaximumSize(new Dimension(150, 150));		
		//create radiobuttons 
		
		male = new JRadioButton("Male");
		female = new JRadioButton("Female");
		
		//group the radiobuttons
		btngroup = new ButtonGroup();
		btngroup.add(male);
		btngroup.add(female);

		// add border around the panel
		setBorder(BorderFactory.createTitledBorder("Gender"));
		
		
		//add radiobuttons to the panel
		add(male);
		add(female); 
		
	}
	public String getSelectedGender()
	{
		if(male.isSelected())
		{
			return "M";
		}
		else
		{
			return "F";
		}
	}
	public void setSelectedGender(String s)
	{
		if(s == "M")
		{
			male.setSelected(true);
			female.setSelected(false);
		}
		else
		{
			male.setSelected(false);
			female.setSelected(true);
		}
	}

}
