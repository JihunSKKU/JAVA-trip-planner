
public abstract class PublicTransport implements Payable {
	private String model; // variable for what is kind of transport
	private double baseFare; // variable for base fare
	private double totalFare; // variable for total fare

	// two-argument constructor
	public PublicTransport(String model, double baseFare) {
		setModel(model);
		setBaseFare(baseFare);
	}

	// getter and setter
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(double baseFare) {
		this.baseFare = baseFare;
	}

	public double getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(double totalFare) {
		this.totalFare = totalFare;
	}

	@Override
	public void calculatePayment() {
		/* Empty method, because PublicTransport class is in abstract class. */
	} // end calculatePayment method
} // end PublicTransport class
