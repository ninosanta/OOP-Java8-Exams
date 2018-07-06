package flightBookings;

import java.util.Set;
import java.util.TreeSet;

public class DestFlights {
	
	private String destName;
	private Set<String> flightIds = new TreeSet<>();
	
	
	public DestFlights(String destName) {
		this.destName = destName;
	}
	
	
	public void addId(String id) {
		flightIds.add(id);
	}


	public String getDestName() {
		return destName;
	}


	public Set<String> getFlightIds() {
		return flightIds;
	}
	
}
