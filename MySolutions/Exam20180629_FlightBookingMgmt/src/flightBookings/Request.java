package flightBookings;

public class Request {
	
	private String requestId;
	private String destName;
	private String fareTypeName;
	private	int maxPrice;
	private int nSeats;
	
	
	public Request(String requestId, String destName, String fareTypeName, int maxPrice, int nSeats) {
		super();
		this.requestId = requestId;
		this.destName = destName;
		this.fareTypeName = fareTypeName;
		this.maxPrice = maxPrice;
		this.nSeats = nSeats;
	}


	public String getRequestId() {
		return requestId;
	}


	public String getDestName() {
		return destName;
	}


	public String getFareTypeName() {
		return fareTypeName;
	}


	public int getMaxPrice() {
		return maxPrice;
	}


	public int getnSeats() {
		return nSeats;
	}

	
	
	
}
