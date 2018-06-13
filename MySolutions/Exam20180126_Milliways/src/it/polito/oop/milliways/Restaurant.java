package it.polito.oop.milliways;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Restaurant {
	
	Map<String, Race> races = new HashMap<>();
//	Map<String, Party> parties = new HashMap<>();
	Map<Integer, Hall> halls = new HashMap<>();
	List<Hall> hallsList = new LinkedList<>();
	
    public Restaurant() {
	}
	
	public Race defineRace(String name) throws MilliwaysException {
		if (races.containsKey(name)) {
			throw new MilliwaysException();
		}
		
		races.put(name, new Race(name));
		return races.get(name);
	}
	
	public Party createParty() {
		return new Party();
	}
	
	public Hall defineHall(int id) throws MilliwaysException {
	    if (halls.containsKey(id))
	    	throw new MilliwaysException();
	    	
		halls.put(id, new Hall(id));
		hallsList.add(halls.get(id));
	    return halls.get(id);
	}

	public List<Hall> getHallList() {
		return hallsList;
	}

	public Hall seat(Party party, Hall hall) throws MilliwaysException {
		if (hall.isSuitable(party) == false)
			throw new MilliwaysException();
		
        return hall;
	}

	public Hall seat(Party party) throws MilliwaysException {
        Optional<Hall> hall = halls.values().stream()
        		.filter(h -> h.isSuitable(party))
        		.findFirst();
        if (hall.isPresent())
        	return hall.get();
        else 
        	throw new MilliwaysException();
	}

	public Map<Race, Integer> statComposition() {
        return null;
	}

	public List<String> statFacility() {
        return null;
	}
	
	public Map<Integer,List<Integer>> statHalls() {
        return null;
	}

}
