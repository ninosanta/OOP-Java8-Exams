package flightBookings;

public class Booking {
	
	private String bookingId;
	private String requestId;
	private String offerId;
	
	
	
	public Booking(String bookingId, String requestId, String offerId) {
		super();
		this.bookingId = bookingId;
		this.requestId = requestId;
		this.offerId = offerId;
	}



	public String getBookingId() {
		return bookingId;
	}



	public String getRequestId() {
		return requestId;
	}



	public String getOfferId() {
		return offerId;
	}
	
}
