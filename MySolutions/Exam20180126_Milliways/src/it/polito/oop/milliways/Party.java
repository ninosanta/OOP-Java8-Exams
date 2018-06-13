package it.polito.oop.milliways;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class Party {
	
	Map<Race, Integer> companions = new HashMap<>(); 
	Map<String, Integer> description = new HashMap<>();
	int num;
	
	
    public void addCompanions(Race race, int num) {
    	this.num += num;
    	if (companions.containsKey(race))
    		num += companions.get(race);
    	companions.put(race, num);
    	description.put(race.getName(), num);
	}
    
    public Map<Race, Integer> getCompanions() {
    	return companions;
    }

	public int getNum() {
        return num;
	}

	public int getNum(Race race) {
	    return companions.get(race);
	}

	public List<String> getRequirements() {
		return companions.keySet().stream()
				.map(Race::getRequirements)  // Stream<List<String>>
				.flatMap(List::stream)  // Stream<String>
				.sorted((s1,s2) -> s1.compareTo(s2))
				.distinct()
				.collect(toList());
	}	

    public Map<String,Integer> getDescription() {
        return description;        			 
    }

}
