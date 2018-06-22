package managingProperties;

import java.util.LinkedList;
import java.util.List;

public class Owner {

	private String id;
	private List<Apartment> apartments = new LinkedList<>();
	private int charge;
	
	public Owner(String id) {
		this.setId(id);
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


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
}
