package managingProperties;

import java.util.LinkedList;
import java.util.List;

public class Building {

	private String id;
	private int aptNumber;
	private List<Apartment> apartments = new LinkedList<>();
	private int charge;
	
	public Building(String id, int aptNumber) {
		this.setAptNumber(aptNumber);
		this.setId(id);
	}


	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public int getAptNumber() {
		return aptNumber;
	}



	public void setAptNumber(int aptNumber) {
		this.aptNumber = aptNumber;
	}



	public void putApartment(Apartment apartment) {
		apartments.add(apartment);
	}



	public int getCharge() {
		return charge;
	}



	public void addCharge(int charge) {
		this.charge += charge;
	}
	
}
