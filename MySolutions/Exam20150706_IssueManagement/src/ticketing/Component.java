package ticketing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;


public class Component {
	
	private String name;
	private HashSet<Component> children = new HashSet<>();
	private Component prev;
	
	
	public Component(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public Component getPrev() {
		return prev;
	}


	public void setPrev(Component prev) {
		this.prev = prev;
	}

	
	public void addChild(Component child) {
		this.children.add(child);
	}


	public Set<String> getSubComponents() {
		return children.stream()  // Stream<Component>
				       .map(Component::getName)
				       .collect(toSet());
	}
	
}
