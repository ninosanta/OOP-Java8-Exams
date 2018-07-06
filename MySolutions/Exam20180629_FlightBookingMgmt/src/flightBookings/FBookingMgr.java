package flightBookings;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

/**
 * Main class
 * 
 * @author ninosanta
  */
public class FBookingMgr {
	
	private boolean recall = false;
	private List<String> fares = new ArrayList<String>();
	private Set<String> fIds = new TreeSet<String>();
	private Set<String> destinations = new TreeSet<String>();
	private List<DestFlights> destFlights = new ArrayList<DestFlights>();
	private Map<String, Flight> flights = new HashMap<>();
	private Map<String, Offer> offers = new HashMap<>();
	private Map<String, Request> requests = new HashMap<String, Request>();
	private Map<String, Booking> bookings = new HashMap<>();
	
	

	//R1
	public void addFareTypes(String... names) throws FBMException {
		if (recall == true)
			throw new FBMException();
		else
			recall = true;
		
		fares.addAll(Stream.of(names).collect(toList()));
	}
	
	public List<String> addFlightsForDestination(String destName, String... flightIds) {
		Set<String> curr = new TreeSet<>();
		DestFlights fl = new DestFlights(destName);
		destinations.add(destName);
		Stream.of(flightIds).filter(fId -> !fIds.contains(fId)).forEach(id -> {
			Flight flight = new Flight(id, destName);
			flights.put(id, flight);
			fIds.add(id);
			fl.addId(id);  // li colleziono in un Set che ignora gli id duplicati
			curr.add(id);
		});
		
		destFlights.add(fl);
		return curr.stream().collect(toList());  // e' un treeSet gia' ordinato
	}
	
	//R2
	public void addOffer(String offerId, String flightId, int nSeats, String fareTypeName, int price) 
				throws FBMException {
		if (!fIds.contains(flightId) || !fares.contains(fareTypeName))
			throw new FBMException();
		
		Offer off = new Offer(offerId, flightId, nSeats, fareTypeName, price);
		offers.put(offerId, off);
	}
	
	public void addRequest(String requestId, String destName, String fareTypeName, 
			int maxPrice, int nSeats) throws FBMException {
		if (!destinations.contains(destName) || !fares.contains(fareTypeName))
			throw new FBMException();
		
		Request req = new Request(requestId, destName, fareTypeName, maxPrice, nSeats);
		requests.put(requestId, req);
	}
	
	public int getNRequests(String destination) {
		return requests.values().stream()
				.filter(req -> req.getDestName().equals(destination))
				.collect(counting()).intValue();
	}

	public int getNOffers(String destination) {
		List<String> fI = destFlights.stream().filter(f -> f.getDestName().equals(destination))
			.map(DestFlights::getFlightIds)
			.flatMap(Set::stream)
			.distinct()
			.collect(toList());
			
		return offers.values().stream()
		.map(Offer::getFlightId)
		.filter(s -> fI.contains(s))
		.collect(counting()).intValue();

	}
	
	//R3
	public void addBooking(String bookingId, String requestId, String offerId) 
			throws FBMException {
		if (!offers.containsKey(offerId) ||
			!requests.containsKey(requestId) ||
			!requests.get(requestId).getFareTypeName().equals(offers.get(offerId).getFareTypeName()) || 
			requests.get(requestId).getnSeats() > offers.get(offerId).getnSeats() ||
			requests.get(requestId).getMaxPrice() <  offers.get(offerId).getPrice() ||
			!requests.get(requestId).getDestName()
			 		 .equals(flights.get(offers.get(offerId).getFlightId()).getDestName()))
			throw new FBMException();
		
		
		Booking book = new Booking(bookingId, requestId, offerId);
		int nSeats = (offers.get(offerId).getnSeats() - requests.get(requestId).getnSeats());
		offers.get(offerId).setnSeats(nSeats);
		bookings.put(bookingId, book);
		
	}

	public int getTotalPrice(String bookingId) throws FBMException {
		if (!bookings.containsKey(bookingId))
			throw new FBMException();
		
		int price = offers.get(bookings.get(bookingId).getOfferId()).getPrice();
		int nSeats = requests.get(bookings.get(bookingId).getRequestId() ).getnSeats();
		
		return price * nSeats;
	
	}
		
	//R4
	public SortedMap<Integer, List<String>> destinationsPerNSeats() {
		return requests.values()
					   .stream()
					   .collect(groupingBy(Request::getDestName,
							    summingInt(Request::getnSeats)))
					   .entrySet().stream()
					   .collect(groupingBy(Map.Entry<String, Integer>::getValue,
							    		   () -> new TreeMap<Integer, List<String>>(reverseOrder()), 
							    		   mapping(e -> e.getKey(), 
							    				   toList()))
							   );
	}

	public SortedMap<String, Integer> revenuesPerFareType() {
		return  bookings.values()
				 	    .stream()
				 	    .collect(groupingBy(book -> requests.get(book.getRequestId()).getFareTypeName(), 
				 	    					() -> new TreeMap<String, Integer>(), 
				 	    					summingInt(book -> offers.get(book.getOfferId()).getPrice() * 
		 	    									           requests.get(book.getRequestId() ).getnSeats()))
//							 	    		mapping(book -> offers.get(book.getOfferId()).getPrice() * 
//							 	    				        requests.get(book.getRequestId() ).getnSeats(), 
//							 	    				summingInt(l -> l)))
				 	    		);	
	}
}
