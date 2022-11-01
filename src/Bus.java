
public class Bus extends PublicTransport implements Payable {
	private Station station; // variable for Station (Composition - "has a" relationship)

	// four-argument constructor
	public Bus(String model, double baseFare, double farePerStation, int nStations) {
		super(model, baseFare);
		/* When calculating the number of station-5 later, 
		 * if a value less than 5 is found, it is calculated as 0. */
		if (nStations < 5)
			nStations = 5;

		setStation(farePerStation, nStations); // Create a station object using the two-argument
		calculatePayment(); // Calculate totalFare
	} // end Bus constructor

	// getter and setter
	public Station getStation() {
		return station;
	}

	public void setStation(double farePerStation, int nStations) {
		this.station = new Station(farePerStation, nStations);
	}

	@Override
	public void calculatePayment() {
		/* For calculating bus fare: 
		 * 	If the number of stations is less than 5: total fare = (base fare)
		 * 	If the number of stations is more than 5:
		 * 		total fare = (base fare) + ((number of station) - 5) * (fare per station)
		 * 
		 * Since the number of station was set to 5 when the number of station is less than 5, 
		 * the code below is the same operation as above. */
		super.setTotalFare(super.getBaseFare() + (station.getnStations() - 5) * station.getFarePerStation());
	} // end calculatePayment method
} // end Bus class
