package flightBookings;
import java.util.*;

//import static java.util.stream.Collectors.*;

public class FBookingMgr {

	//R1
	public void addFareTypes(String... names) throws FBMException {
		
	}
	
	public List<String> addFlightsForDestination(String destName, String... flightIds) {
		return new ArrayList<String>();
	}
	
	//R2
	public void addOffer(String offerId, String flightId, int nSeats, String fareTypeName, int price) 
				throws FBMException {
			
	}
	
	public void addRequest(String requestId, String destName, String fareTypeName, 
			int maxPrice, int nSeats) throws FBMException {
	
	}
	
	public int getNRequests(String destination) {
		return -1;
	}

	public int getNOffers(String destination) {
		return -1;
	}
	
	//R3
	public void addBooking(String bookingId, String requestId, String offerId) 
			throws FBMException {
		
	}

	public int getTotalPrice(String bookingId) throws FBMException {
		return -1;
	}
		
	//R4
	public SortedMap<Integer, List<String>> destinationsPerNSeats() {
		return new TreeMap<Integer, List<String>>();
	}

	public SortedMap<String, Integer> revenuesPerFareType() {
		return new TreeMap<String, Integer>();
	}
}
