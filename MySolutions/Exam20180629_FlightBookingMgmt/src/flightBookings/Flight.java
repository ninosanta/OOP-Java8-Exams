package flightBookings;


public class Flight {
	private String destName;
	private String flightId;
	
	
	public String getDestName() {
		return destName;
	}


	public String getFlightId() {
		return flightId;
	}


	public Flight(String id, String dest) {
		this.destName = dest;
		this.flightId = id;
	}
}
