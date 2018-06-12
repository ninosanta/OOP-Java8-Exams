package it.polito.oop.milliways;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.*;

public class Race {
	
	private String name;
	Set<String> requirements = new HashSet<>();
	
	
	public Race(String name) {
		this.name = name;
	}
	
	public void addRequirement(String requirement) throws MilliwaysException {
		if(requirements.contains(requirement))
			throw new MilliwaysException();
		else
			requirements.add(requirement);
	}
	
	public List<String> getRequirements() {
        return requirements.stream().collect(toList());
	}
	
	public String getName() {
        return name;
	}
	
}
