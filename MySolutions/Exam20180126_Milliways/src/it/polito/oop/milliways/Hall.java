package it.polito.oop.milliways;

import java.util.LinkedList;
import java.util.List;

public class Hall {
	
	private int ID;
	List<String> facilities = new LinkedList<>();
	List<Party> seatedParties = new LinkedList<>();
	
	public Hall(int ID) {
		this.ID = ID;
	}
	
	public int getId() {
		return ID;
	}

	public void addFacility(String facility) throws MilliwaysException {
		if (facilities.contains(facility))
			throw new MilliwaysException();
		facilities.add(facility);
	}

	public List<String> getFacilities() {
        return facilities;
	}
	
	public boolean containFacility(String facility) {
		return facilities.contains(facility);
	}
	
	int getNumFacilities() {
        return facilities.size();
	}

	public boolean isSuitable(Party party) {
		return party.getRequirements().stream()  // Stream<String>
				.allMatch(s -> facilities.contains(s));
	}
	
	public void addSeatedParty(Party party) {
		seatedParties.add(party);
	}
	
	public List<Party> getSeatedParties() {
		return seatedParties;
	}

}
