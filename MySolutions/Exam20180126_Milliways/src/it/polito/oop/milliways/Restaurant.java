package it.polito.oop.milliways;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;


public class Restaurant {
	
	Map<String, Race> races = new HashMap<>();
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
		
		hall.addSeatedParty(party);
		
        return hall;
	}

	public Hall seat(Party party) throws MilliwaysException {
        Optional<Hall> hall = halls.values().stream()
        		.filter(h -> h.isSuitable(party))
        		.findFirst();
        if (hall.isPresent()) {
        	hall.get().addSeatedParty(party);
        	return hall.get();
        } else { 
        	throw new MilliwaysException();
        }
	}

	public Map<Race,Integer> statComposition() {
			
		return halls.values().stream().map(Hall::getSeatedParties)  // Stream<List<Party>>
					.flatMap(List::stream)  // Stream<Party>
					.map(Party::getCompanions)  // Stream<Map<Race,Integer>>
					.flatMap(e -> e.entrySet().stream())  // Stream<Race,Integer>
					.collect(groupingBy(Map.Entry<Race, Integer>::getKey,  // key
							 summingInt(Map.Entry::getValue) // collettore associato alla chiave
							));
					   
					  
//		Map<Race, Integer> rm = new HashMap<>();
//		halls.values().stream().map(Hall::getParties)  // Stream<List<Party>>
//				  .flatMap(List::stream)  // Stream<Party>
//				  .map(Party::getCompanions)  // Stream<Map<Race,Integer>>
//				  .forEach( m -> {
//						  for (Race r : m.keySet()) {
//							  if (rm.containsKey(r))
//								  rm.put(r, rm.get(r)+m.get(r));
//							  else
//								  rm.put(r, m.get(r));
//						}
//					  });		
//		return rm;
	}

	public List<String> statFacility() {
		return hallsList.stream()  // Stream<Hall>
				 .map(h -> h.getFacilities())  // Stream<List<String>>
				 .flatMap(List::stream)  // Stream<String>
				 .collect(groupingBy( s -> s, 
						 			 counting()))  // Map<String,Long> 
				 .entrySet().stream()  // Stream<Map<String,Long>>
				 		    .sorted(comparing(Map.Entry<String,Long>::getValue, 
				 		    				  reverseOrder()).thenComparing(Map.Entry::getKey))
				 		    .map(Map.Entry<String,Long>::getKey)
				 		    .collect(toList());
				 
	}
	
	public Map<Integer,List<Integer>> statHalls() {
        return null;
	}

}
