package flightBookings;

public class Offer {
	private String offerId;
	public void setnSeats(int nSeats) {
		this.nSeats = nSeats;
	}


	private String flightId;
	private int nSeats;
	private String fareTypeName;
	private int price;
	
	
	public Offer(String offerId, String flightId, int nSeats, String fareTypeName, int price) {
		super();
		this.offerId = offerId;
		this.flightId = flightId;
		this.nSeats = nSeats;
		this.fareTypeName = fareTypeName;
		this.price = price;
	}


	public String getOfferId() {
		return offerId;
	}


	public String getFlightId() {
		return flightId;
	}


	public int getnSeats() {
		return nSeats;
	}


	public String getFareTypeName() {
		return fareTypeName;
	}


	public int getPrice() {
		return price;
	}
}
