
public class Taxi extends PublicTransport implements Payable {

	private double farePerKm; // variable for fare per km
	private double distance; // variable for distance

	// four-argument constructor
	public Taxi(String model, double baseFare, double farePerKm, double distance) {
		super(model, baseFare);
		setFarePerKm(farePerKm);
		setDistance(distance);
		calculatePayment(); // Calculate totalFare
	} // end Taxi constructor

	// getter and setter
	public double getFarePerKm() {
		return farePerKm;
	}

	public void setFarePerKm(double farePerKm) {
		this.farePerKm = farePerKm;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public void calculatePayment() {
		/* For calculating taxi fare: total fare = (base fare) + distance * (fare per km)
		 *
		 * The code below is the same operation as above. */
		super.setTotalFare(super.getBaseFare() + distance * farePerKm);
	} // end calculatePayment method
} // end Taxi class
