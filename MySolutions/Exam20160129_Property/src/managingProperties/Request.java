package managingProperties;

import managingProperties.PropertyManager.State;

public class Request {
	
	private String professional;
	private String building;
	private String profession;
	private String owner;
	private String apartment;
	private State state = State.Pending;
	private int amount;

	public Request(String profession, String owner, String apartment) {
		this.setOwner(owner);
		this.setApartment(apartment);
		this.setProfession(profession);
		setBuilding(apartment.split(":")[0]);
	}
	
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}


	public String getProfession() {
		return profession;
	}


	public void setProfession(String profession) {
		this.profession = profession;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getApartment() {
		return apartment;
	}


	public void setApartment(String apartment) {
		this.apartment = apartment;
	}


	public String getBuilding() {
		return building;
	}


	public void setBuilding(String building) {
		this.building = building;
	}


	public void setAmount(int amount) {
		this.amount = amount;		
	}
	
	public int getAmount() {
		return amount;		
	}
	
}
