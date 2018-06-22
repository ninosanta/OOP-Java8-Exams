package managingProperties;

public class Apartment {

	private String idOwner;
	private String number;
	private String building;
	
	public Apartment(String idOwner, String number, String building) {
		this.setIdOwner(idOwner);
		this.setNumber(number);
		this.setBuilding(building);
	}

	public String getIdOwner() {
		return idOwner;
	}

	public void setIdOwner(String idOwner) {
		this.idOwner = idOwner;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
}
