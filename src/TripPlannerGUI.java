import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/* Program GUI */
public class TripPlannerGUI extends JFrame implements ActionListener {
	
	/* Main Panel */
	private JPanel contentPane;

	/* North Panel and labels to be placed on north */
	private JPanel northPanel;
	private JLabel plannerLogo;
	private JLabel tripPlannerText;

	/* West Panel and buttons to be placed on west */
	private JPanel westPanel;
	private JButton trainButton;
	private JButton busButton;
	private JButton taxiButton;

	/* Text window to be placed on center */
	private JTextArea textWindow;

	/* East Panel and buttons to be placed on east */
	private JPanel eastPanel;
	private JButton yesButton;
	private JButton noButton;

	/* South Panel and buttons to be placed on south */
	private JPanel southPanel;
	private JButton[] numberButtons; // 0 ~ 9 buttons array
	private JButton cancelButton;
	private JButton clearButton;
	private JButton enterButton;

	
	// PublicTransport array where the transports the user will board are stored as PublicTransport objects.
	private PublicTransport[] transports;
	// The transport the user will board. It is newly added to the transports array.
	private PublicTransport newTransport;

	/* Variable for current stage.
	 * Step 1: Asking which transport to board
	 * Step 2: Ask for base fare
	 * Step 3: Ask for fare per station or fare per km
	 * Step 4: Asking for number of station or distance in km
	 * Step 5: Ask if you want to take additional transport
	 * Step 6: Displaying the price and total trip fare of each transport in the text window */
	private int step;
	
	// Values required when creating a PublicTransport object
	private String model;
	private double baseFare;
	private double firstVariable; // case Taxi : farePerKm, case Bus or Train : farePerStation
	private double secondVariable; /* case Taxi : distance, case Bus or Train : nStations
									* (In Bus or Train case, a forced type conversion is required.) */

	private String displayText; // String to display in text window
	
	private int pin; // Variable for number entered by the user
	private boolean inputNum = false; // Variable for determines whether the user entered a number
	/* For readability, in the code below, pin and inputNum are directly assigned without using getters and setters. */
	
	// getter and setter
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public double getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(double baseFare) {
		this.baseFare = baseFare;
	}

	public double getFirstVariable() {
		return firstVariable;
	}

	public void setFirstVariable(double firstVariable) {
		this.firstVariable = firstVariable;
	}

	public double getSecondVariable() {
		return secondVariable;
	}

	public void setSecondVariable(double secondVariable) {
		this.secondVariable = secondVariable;
	}

	/* Create the frame. */
	// zero-argument constructor
	public TripPlannerGUI() {
		setDisplayText("Choose transport (from left menu): ");
		setStep(1); // Change the step 1 - Asking which transport to board

		setTitle("Trip Planner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 680);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null); // Set the Main panel to absolute. (To free the position of each panel)

		
		/* North Panel and labels to be placed on north */
		northPanel = new JPanel(); // This panel contains the planner logo and the label "TRIP PLANNER".
		northPanel.setBounds(0, 0, 585, 150);
		contentPane.add(northPanel);
		northPanel.setLayout(null); // Set the northPanel to absolute. (To free the position of each panel)

		plannerLogo = new JLabel();
		plannerLogo.setBounds(0, 50, 585, 74);
		plannerLogo.setHorizontalAlignment(SwingConstants.CENTER); // Center the Image.
		plannerLogo.setIcon(new ImageIcon(TripPlannerGUI.class.getResource("/assets/planner_logo.png")));
		northPanel.add(plannerLogo);

		tripPlannerText = new JLabel("TRIP PLANNER");
		tripPlannerText.setBounds(170, 130, 200, 20);
		tripPlannerText.setHorizontalAlignment(SwingConstants.CENTER);
		northPanel.add(tripPlannerText);

		
		/* West Panel and buttons to be placed on west */
		westPanel = new JPanel(); // This panel contains the buttons "TRAIN", "BUS" and "TAXI".
		westPanel.setBounds(45, 155, 90, 100);
		contentPane.add(westPanel);
		westPanel.setLayout(new GridLayout(3, 1, 0, 5)); // Arrange the three buttons in 3 rows (5 pixel spacing).

		trainButton = new JButton("TRAIN");
		trainButton.setIcon(new ImageIcon(TripPlannerGUI.class.getResource("/assets/train.png")));
		westPanel.add(trainButton);

		busButton = new JButton("BUS");
		busButton.setIcon(new ImageIcon(TripPlannerGUI.class.getResource("/assets/bus.png")));
		westPanel.add(busButton);

		taxiButton = new JButton("TAXI");
		taxiButton.setIcon(new ImageIcon(TripPlannerGUI.class.getResource("/assets/taxi.png")));
		westPanel.add(taxiButton);


		/* Text window to be placed on center */
		textWindow = new JTextArea(displayText);
		textWindow.setFont(UIManager.getFont("Gulim 12"));
		textWindow.setBounds(140, 150, 270, 105);
		contentPane.add(textWindow); // Add it directly to the main panel.

		
		/* East Panel and buttons to be placed on east */
		eastPanel = new JPanel(); // This panel contains the buttons "Yes" and "No".
		eastPanel.setBounds(415, 155, 140, 70);
		contentPane.add(eastPanel);
		eastPanel.setLayout(new GridLayout(2, 1, 0, 0)); // Arrange the three buttons in 2 rows.

		yesButton = new JButton("Yes");
		yesButton.setIcon(new ImageIcon(TripPlannerGUI.class.getResource("/assets/enterSmall.png")));
		eastPanel.add(yesButton);

		noButton = new JButton("No");
		noButton.setIcon(new ImageIcon(TripPlannerGUI.class.getResource("/assets/cancelSmall.png")));
		eastPanel.add(noButton);

		
		/* South Panel and buttons to be placed on south */
		southPanel = new JPanel(); // This panel contains the buttons 0 ~ 9, "CANCEL", "CLEAR" and "ENTER".
		southPanel.setBounds(140, 260, 415, 260);
		contentPane.add(southPanel);
		southPanel.setLayout(null); // Set the southPanel to absolute. (To free the position of each panel)

		numberButtons = new JButton[10]; // 0 ~ 9 buttons array
		int coordinateX; // variable for the x-coordinate of each button
		int coordinateY; // variable for the y-coordinate of each button
		for (int number = 0; number < numberButtons.length; number++) {
			numberButtons[number] = new JButton("");
			// Set the number picture for each number as an Icon.
			numberButtons[number].setIcon(new ImageIcon(TripPlannerGUI.class.getResource(String.format("/assets/%d.png", number))));
			if (number == 0) {
				/* Case of the number is 0. */
				coordinateX = 90;
				coordinateY = 195;
			} 
			else {
				/* Case of the number is 1 to 9 */
				// Calculator type arrangement
				coordinateX = (number - 1) % 3 * 90;
				coordinateY = (number - 1) / 3 * 65;
			}
			// Set the button position with the coordinates calculated above.
			numberButtons[number].setBounds(coordinateX, coordinateY, 90, 65);
			southPanel.add(numberButtons[number]);
		} // end for (number)

		cancelButton = new JButton("CANCEL");
		cancelButton.setBounds(275, 0, 140, 65);
		cancelButton.setIcon(new ImageIcon(TripPlannerGUI.class.getResource("/assets/cancel.png")));
		southPanel.add(cancelButton);

		clearButton = new JButton("CLEAR");
		clearButton.setBounds(275, 65, 140, 65);
		clearButton.setIcon(new ImageIcon(TripPlannerGUI.class.getResource("/assets/clear.png")));
		southPanel.add(clearButton);

		enterButton = new JButton("ENTER");
		enterButton.setBounds(275, 130, 140, 65);
		enterButton.setIcon(new ImageIcon(TripPlannerGUI.class.getResource("/assets/enter.png")));
		southPanel.add(enterButton);

		/* The codes below are the codes needed for action and operation. */
		trainButton.addActionListener(this);
		busButton.addActionListener(this);
		taxiButton.addActionListener(this);
		yesButton.addActionListener(this);
		noButton.addActionListener(this);
		for (JButton numberButton : numberButtons) 
			numberButton.addActionListener(this); // All buttons from 0 to 9
		cancelButton.addActionListener(this);
		clearButton.addActionListener(this);
		enterButton.addActionListener(this);
	} // end TripPlanner constructor

	/* Method to return an array that adds new transport to the existing transports array */
	private PublicTransport[] addTransport(PublicTransport[] originTransports, PublicTransport addTransport) {
		int arrayLength; // variable for length of existing ingredients

		// Set arrayLength to the length of existing transports array
		if (originTransports == null)
			/* Case of transports array empty */
			arrayLength = 0;
		else
			arrayLength = originTransports.length;

		// Create an array with size (size of original array + 1).
		PublicTransport[] newTransports = new PublicTransport[arrayLength + 1];

		// Sequentially assign values to a new array.
		for (int index = 0; index < arrayLength; index++)
			newTransports[index] = originTransports[index];

		// Assign the value you want to append to the last position of the new array.
		newTransports[arrayLength] = addTransport;

		// Return a new array.
		return newTransports;
	} // end addTransport method

	@Override
	public void actionPerformed(ActionEvent e) {
		/* Case of a number button is pressed (Search with for statement) */
		for (int number = 0; number < numberButtons.length; number++) {
			if (e.getSource() == numberButtons[number] && getStep() >= 2 && getStep() <= 4) {
				 /* Step 2: Ask for base fare
				  * Step 3: Ask for fare per station or fare per km
				  * Step 4: Asking for number of station or distance in km
				  * For other steps, the number buttons do not work. */
				inputNum = true; // Save that the user entered a number.
				setDisplayText(getDisplayText() + number); // Adds the entered number to displayText.
				textWindow.setText(getDisplayText()); // Update textWindow.
			} // end if
		} // end for (number)

		/* Case of train button is pressed */
		if (e.getSource() == trainButton) {
			// If it is not step 1, the train button does not work.
			if (getStep() == 1) {
				/* Step 1: Asking which transport to board
				 * Step 2: Ask for base fare
				 * Change the step from 1 to 2. */
				setStep(2);
				setModel("KORAIL");
				setDisplayText("You choose TRAIN\n" + "Enter base fare: ");
				textWindow.setText(getDisplayText()); // Update textWindow.
			} // end if
		} // end if

		/* Case of bus button is pressed */
		else if (e.getSource() == busButton) {
			// If it is not step 1, the bus button does not work.
			if (getStep() == 1) {
				/* Step 1: Asking which transport to board
				 * Step 2: Ask for base fare
				 * Change the step from 1 to 2. */
				setStep(2);
				setModel("KORBUS");
				setDisplayText("You choose BUS\nEnter base fare: ");
				textWindow.setText(getDisplayText()); // Update textWindow.
			} // end if
		} // end else if

		/* Case of taxi button is pressed */
		else if (e.getSource() == taxiButton) {
			// If it is not step 1, the bus button does not work.
			if (getStep() == 1) {
				/* Step 1: Asking which transport to board
				 * Step 2: Ask for base fare
				 * Change the step from 1 to 2. */
				setStep(2);
				setModel("KAKAO TAXI");
				setDisplayText("You choose TAXI\n" + "Enter base fare: ");
				textWindow.setText(getDisplayText()); // Update textWindow.
			} // end if
		} // end else if

		/* Case of yes button is pressed */
		else if (e.getSource() == yesButton) {
			// If it is not step 5, the yes button does not work.
			if (getStep() == 5) {
				/* Step 5: Ask if you want to take additional transport
				 * Step 1: Asking which transport to board
				 * Change the step from 5 to 1. */
				setStep(1);
				setDisplayText("Choose transport (from left menu): ");
				textWindow.setText(getDisplayText()); // Update textWindow.
			} // end if
		} // end else if

		/* Case of no button is pressed */
		else if (e.getSource() == noButton) {
			// If it is not step 5, the no button does not work.
			if (getStep() == 5) {
				/* Step 5: Ask if you want to take additional transport
				 * Step 6: Displaying the price and total trip fare of each transport in the text window
				 * Change the step from 5 to 6. */
				setStep(6);
				// Increase the size of the textWindow a little more, and lower the southPanel down accordingly.
				textWindow.setBounds(140, 150, 270, 200);
				southPanel.setBounds(140, 350, 415, 260);

				// String variable for the price and total trip fare of each transport will be printed in the text window.
				String info = "";
				double totalTripFare = 0;
				for (int th = 0; th < transports.length; th++) {
					// Extracts the information of each object stored in the transports array.
					info += String.format("Trainport %d: %s\n", th + 1, transports[th].getModel());
					info += String.format("Fare: %.2f\n", transports[th].getTotalFare());
					// Add the fare of the current transport to the total trip fare.
					totalTripFare += transports[th].getTotalFare();
				} // end for
				info += "==============================\n";
				info += String.format("Total Trip Fare: %.2f", totalTripFare);
				
				setDisplayText(info);
				textWindow.setText(getDisplayText()); // Update textWindow.
			} // end if
		} // end else if

		/* Case of cancel button is pressed */
		else if (e.getSource() == cancelButton) {
			// If it is step 6, the cancel button does not work. (Because step 6 is the final step)
			if (getStep() < 6) {
				/* Step 1: Asking which transport to board
				 * Change the step to 1. */
				setStep(1);
				transports = null; // Delete all transports added so far.
				setDisplayText("Cancelled!\n" + "New plan:\n" + "Choose transport (from left menu): ");
				textWindow.setText(getDisplayText()); // Update textWindow.
			} // end if
		} // end else if

		/* Case of clear button is pressed */
		else if (e.getSource() == clearButton) {
			/* It is executed when any number is entered. */
			if (inputNum) {
				// For getting only digits from the textWindow.
				pin = Integer.parseInt(getDisplayText().replaceAll("[^0-9]", ""));
				setDisplayText(getDisplayText().replace(pin + "", "")); // Remove digits in textWindow.
				textWindow.setText(getDisplayText()); // Update textWindow.
				inputNum = false; // No number entered now
			} // end if
		} // end else if

		/* Case of enter button is pressed */
		else if (e.getSource() == enterButton) {
			/* It is executed when any number is entered. */
			if (inputNum) {
				// For getting only digits from the textWindow.
				pin = Integer.parseInt(getDisplayText().replaceAll("[^0-9]", ""));

				/* Case of step 2 */
				if (getStep() == 2) {
					/* Step 2: Ask for base fare
					 * Step 3: Ask for fare per station or fare per km
					 * Change the step from 2 to 3. */
					setStep(3);
					setBaseFare(pin);
					if (getModel() == "KAKAO TAXI")
						setDisplayText("Enter fare per km: ");
					else
						setDisplayText("Enter fare per station (for extra stations): ");
					textWindow.setText(getDisplayText()); // Update textWindow.
				} // end if (getStep() == 2)

				/* Case of step 3 */
				else if (getStep() == 3) {
					/* Step 3: Ask for fare per station or fare per km
					 * Step 4: Asking for number of station or distance in km
					 * Change the step from 3 to 4. */
					setStep(4);
					setFirstVariable(pin);
					if (getModel() == "KAKAO TAXI")
						setDisplayText("Enter distance (in km): ");
					else
						setDisplayText("Enter number of stations: ");
					textWindow.setText(getDisplayText()); // Update textWindow.
				} // end else if (getStep() == 3)

				/* Case of step 4 */
				else if (getStep() == 4) {
					/* Step 4: Asking for number of station or distance in km
					 * Step 5: Ask if you want to take additional transport
					 * Change the step from 4 to 5. */
					setStep(5);
					setSecondVariable(pin);
					setDisplayText("Add more transport(from right menu)?");
					textWindow.setText(getDisplayText()); // Update textWindow.

					// Create a PublicTransport object for each case.
					if (getModel() == "KORAIL")
						newTransport = new Train(model, baseFare, firstVariable, (int) secondVariable);
					else if (getModel() == "KORBUS")
						newTransport = new Bus(model, baseFare, firstVariable, (int) secondVariable);
					else if (getModel() == "KAKAO TAXI")
						newTransport = new Taxi(model, baseFare, firstVariable, secondVariable);
					// Add the newly created object to the transports array.
					transports = addTransport(transports, newTransport);
				} // end else if (getStep() == 4)

				inputNum = false; // No number entered now
			} // end if (inputNum)
		} // end else if (e.getSource() == enterButton)

	} // end actionPerformed method
} // end TripPlannerGUI class
