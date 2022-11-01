/* Main class of Java Midterm Assignment
 * 2019313611 JiHun-Kim */

import java.awt.EventQueue;

public class Main {
	/* Main method that runs the program */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TripPlannerGUI frame = new TripPlannerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
} // end Main class

/* Step 1: Asking which transport to board
 * Step 2: Ask for base fare
 * Step 3: Ask for fare per station or fare per km
 * Step 4: Asking for number of station or distance in km
 * Step 5: Ask if you want to take additional transport
 * Step 6: Displaying the price and total trip fare of each transport in the text window */