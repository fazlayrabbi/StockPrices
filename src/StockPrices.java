/*******************************************************************************
Title: StockPrices.java
Author: Fazlay Rabbi 
Created on: 01/23/2015
Description:This program takes input from goog.csv and asks user to input date 
to display Google's stock market prices for available dates. If the date is not
available, fields are shown as n/a and if wrong date format is entered, date 
text field displays "Wrong date format entered".
Dependencies: One file "goog.csv" should be in the same directory. 
Modifications: Comments were added on 01/25/2015
*******************************************************************************/
import java.io.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.border.TitledBorder;
import java.text.*;

public class StockPrices extends JFrame {
	private JTextField entryDate = new JTextField();// used to enter date by user
	private JTextField open = new JTextField();// displays open value
	private JTextField high = new JTextField();// displays high value
	private JTextField low = new JTextField();// displays low value
	private JTextField close = new JTextField();// displays close value
	private JTextField volume = new JTextField();// displays value value
	private JTextField adj = new JTextField();// displays adj value
	private JTextField availability = new JTextField();// displays whether date is available in file
	private JButton submit= new JButton("Submit");// takes only the date input
	/**Map that holds date as key and numbers as its values*/
	private static Map<Date, List<Number>> stocks= new TreeMap<Date, List<Number>>();
	public StockPrices() throws FileNotFoundException{
		getInfo();// gets all the input
		JPanel p1 = new JPanel(new GridLayout(8,2));
		p1.add(new JLabel("Date"));
		p1.add(entryDate);
		p1.add(new JLabel("Date Availability"));
		p1.add(availability);
		p1.add(new JLabel("Open"));
		p1.add(open);
		p1.add(new JLabel("High"));
		p1.add(high);
		p1.add(new JLabel("Low"));
		p1.add(low);
		p1.add(new JLabel("Close"));
		p1.add(close);
		p1.add(new JLabel("Volume"));
		p1.add(volume);
		p1.add(new JLabel("Adj"));
		p1.add(adj);
		p1.setBorder(new TitledBorder("Enter a date in (MM/DD/YYYY) format to find"
				+ " Google's stock market prices for available dates"));
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p2.add(submit);
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.EAST);
		submit.addActionListener(new ButtonListener());
	}
	public static void main(String[] args) throws FileNotFoundException {
		JFrame frame = new StockPrices();
		frame.setTitle("Stock Prices");
		frame.setLocationRelativeTo(null); // Center the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550, 255);
		frame.setVisible(true);
	}
	/**Gets input from the file "goog.csv*/
	private void getInfo()throws FileNotFoundException {
		Scanner input= new Scanner(new File("goog.csv"));
		int i=0;// used to keep track of the first line which contains invalid input
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");// date format used in the file
		List<Number> tempList;
		while(input.hasNext()){
			String info= new String(input.nextLine());
			if(i==0){// first line
				i++;
				continue;
			}
			String [] temp= info.split("[,]");// all values are separated by ","
			try
		    {
		      Date date = formatter.parse(temp[0]);
		      tempList= Arrays.asList(Double.parseDouble(temp[1]),
						Double.parseDouble(temp[2]),Double.parseDouble(temp[3]),
						Double.parseDouble(temp[4]),Double.parseDouble(temp[5]),
						Double.parseDouble(temp[6]));
			  stocks.put(date, tempList);
		    }
			catch (ParseException e)// wrong date format
		    {
				continue;
		    }
		}
		input.close();
	}
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");// date format used by the user
			try
		    {
		      Date date = formatter.parse(entryDate.getText());// user entered date
		      if(stocks.containsKey(date)){// date is available
		    	  List<Number> tempList= stocks.get(date);
		    	  entryDate.setText(formatter.format(date));
		    	  ListIterator<Number> listIterator = tempList.listIterator();//iterator to set list of stock variables
		    	  availability.setText("Available");
		    	  open.setText(listIterator.next().toString());
		    	  high.setText(listIterator.next().toString());
		    	  low.setText(listIterator.next().toString());
		    	  close.setText(listIterator.next().toString());
		    	  volume.setText(listIterator.next().toString());
		    	  adj.setText(listIterator.next().toString());
		      }
		      else{// date does not exists in the file
		    	  entryDate.setText(formatter.format(date));
		    	  availability.setText("n/a");
		    	  open.setText("n/a");
		    	  high.setText("n/a");
		    	  low.setText("n/a");
		    	  close.setText("n/a");
		    	  volume.setText("n/a");
		    	  adj.setText("n/a");
		      }
		    }
			catch (ParseException e1)// wrong date entered
		    {
				entryDate.setText("Wrong date format entered");
				availability.setText("n/a");
				open.setText("n/a");
				high.setText("n/a");
				low.setText("n/a");
				close.setText("n/a");
				volume.setText("n/a");
				adj.setText("n/a");
		    }
			
		}
	}	
}
