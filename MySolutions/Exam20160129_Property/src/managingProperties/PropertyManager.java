package managingProperties;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.*;

import javafx.collections.transformation.SortedList;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

import java.util.HashMap;


public class PropertyManager {
	
	public enum State {
		Pending, Assigned, Completed
	}
	
	private static final int MAX = 100;
	private static final int MIN = 1;
	private static final int B_ID = 0;
	private static final int A_NUM = 1;
	
	
	Map<String, Building> buildings = new HashMap<>();
	Map<String, Owner> owners = new HashMap<>();
	Map<String, Apartment> apartments = new HashMap<>();
	Map<String, String> professionals = new HashMap<>();
	private int reqID = 1;
	Map<Integer, Request> requests = new HashMap<>();
	
	/**
	 * Add a new building 
	 */
	public void addBuilding(String building, int n) throws PropertyException {
		if (buildings.containsKey(building)  || n < MIN || n > MAX)
			throw new PropertyException();
		else 
			buildings.put(building, new Building(building, n));
	}

	public void addOwner(String owner, String... apartmentsList) throws PropertyException {
		if (owners.containsKey(owner))
			throw new PropertyException();
		
		Owner o = new Owner(owner);
		owners.put(owner, o);
		
		for (String apartment : apartmentsList) {
			String[] info = apartment.split(":");
			
			if (!buildings.containsKey(info[B_ID]) ||
				(apartments.containsKey(apartment) && 
				 apartments.get(apartment).getIdOwner() != null
				 ) ||
				/* dal testo non si capiva affatto bene che era questa la verifica richiesta */
				!(Integer.parseInt(info[A_NUM]) >= 1 && 
				  Integer.parseInt(info[A_NUM]) <= buildings.get(info[B_ID]).getAptNumber()
				 ))
				throw new PropertyException();
			
			Apartment a = new Apartment(owner, info[A_NUM], info[B_ID]);
			apartments.put(apartment, a);
			o.putApartment(a);
			buildings.get(info[B_ID]).putApartment(a);
			
		}
			
		
	}

	/**
	 * Returns a map ( number of apartments => list of buildings ) 
	 * 
	 */
	public SortedMap<Integer, List<String>> getBuildings() {
		 return buildings.values().stream()  // Stream<Building>
				.sorted(comparing(Building::getId))
				.collect(groupingBy(Building::getAptNumber,  // chiave della TreeMap
									TreeMap::new, // TreeMap<Integer,List<String>>
									// valore della Mappa: 
									mapping(Building::getId, toList())  /* trasforma gli elementi di tipo 
																		 * <Building> prima di collezionarli
																		 * nel collettore toList() */
									)
						// NB: senza mapping ma solo toList() avrei ottenuto TreeMap<Integer,List<Building>> 
						);
	}

	public void addProfessionals(String profession, String... professionals) throws PropertyException {
		// prima d'ora questa professione e' gia' stata assegnata ad un gruppo di professionisti?
		if (this.professionals.values().stream().anyMatch(p -> p.equals(profession)))
			throw new PropertyException();
		
		for (String p : professionals) {  // per ogni professionista
			if (this.professionals.containsKey(p))
				throw new PropertyException();
			// assegno la medesima professione
			this.professionals.put(p, profession);
		}
		
	}

	/**
	 * Returns a map ( profession => number of workers )
	 *
	 */
	public SortedMap<String, Integer> getProfessions() {
		return professionals.values().stream()
				.collect(groupingBy(s -> s, 
						TreeMap::new,
//						summingInt(s -> 1)  // tip
						collectingAndThen(counting(), sum -> sum.intValue())));
		
	}

	public int addRequest(String owner, String apartment, String profession) throws PropertyException {
		if (!owners.containsKey(owner) ||
			!apartments.containsKey(apartment) ||
			!apartments.get(apartment).getIdOwner().equals(owner) ||
			professionals.values().stream().noneMatch(p -> p.equals(profession)))
			throw new PropertyException();
		
		requests.put(reqID, new Request(profession, owner, apartment));
		
		return reqID++;
	}

	public void assign(int requestN, String professional) throws PropertyException {
		if (!requests.containsKey(requestN)  ||
			!professionals.get(professional).equals(requests.get(requestN).getProfession()) ||
			requests.get(requestN).getState() != State.Pending)
			throw new PropertyException();

		requests.get(requestN).setState(State.Assigned);
		requests.get(requestN).setProfessional(professional);
	}

	public List<Integer> getAssignedRequests() {
		return requests.entrySet().stream()
				.filter(entry -> entry.getValue().getState() == State.Assigned)
				.map(Map.Entry::getKey)
				.sorted()
				.collect(toList());
	}

	
	public void charge(int requestN, int amount) throws PropertyException {
		if (amount < 0 || amount > 1000 ||
			!requests.containsKey(requestN) ||
			requests.get(requestN).getState() != State.Assigned)
			throw new PropertyException();
		
		requests.get(requestN).setState(State.Completed);
		requests.get(requestN).setAmount(amount);
		String owner = requests.get(requestN).getOwner();
		owners.get(owner).addCharge(amount);
		String building = (requests.get(requestN).getApartment().split(":"))[0];
		buildings.get(building).addCharge(amount);
	}

	/**
	 * Returns the list of request ids
	 * 
	 */
	public List<Integer> getCompletedRequests() {
		return requests.entrySet().stream()
				.filter(entry -> entry.getValue().getState() == State.Completed)
				.map(Map.Entry::getKey)
				.sorted()
				.collect(toList());
	}
	
	/**
	 * Returns a map ( owner => total expenses )
	 * 
	 */
	public SortedMap<String, Integer> getCharges() {
		return owners.values().stream()
				.filter(o -> o.getCharge() != 0)
//				.sorted(comparing(Owner::getId))
				.collect(groupingBy(Owner::getId, 
						TreeMap::new,
						mapping(Owner::getCharge, summingInt(i -> i))
						));
		
	}

	/**
	 * Returns the map ( building => ( professional => total expenses) ).
	 * Both buildings and professions are sorted alphabetically
	 * 
	 */
	public SortedMap<String, Map<String, Integer>> getChargesOfBuildings() {
		return requests.values().stream()
		.filter(r -> r.getAmount() != 0)
		.collect(groupingBy(Request::getBuilding, 
							TreeMap::new,
							// Collector:
				 			groupingBy(Request::getProfessional,  
				 					   TreeMap::new,
				 					   /* Per ogni professionista estratto dalle richieste
				 					    * calcola il costo come somma dei costi richiesti
				 					    * nelle varie richieste
				 					    */
				 					   summingInt(Request::getAmount))));
	}

}
