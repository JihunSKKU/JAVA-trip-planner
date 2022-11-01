
public class Station {
	private double farePerStation; // variable for fare per station
	private int nStations; // variable for number of station

	// two-argument constructor
	public Station(double farePerStation, int nStations) {
		setFarePerStation(farePerStation);
		setnStations(nStations);
	} // end Station constructor

	// getter and setter
	public double getFarePerStation() {
		return farePerStation;
	}

	public void setFarePerStation(double farePerStation) {
		this.farePerStation = farePerStation;
	}

	public int getnStations() {
		return nStations;
	}

	public void setnStations(int nStations) {
		this.nStations = nStations;
	}
} // end Station class
