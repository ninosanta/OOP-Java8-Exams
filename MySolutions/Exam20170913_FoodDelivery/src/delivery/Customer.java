package delivery;

public class Customer {
	private String name;
	private String address;
	private String phone;
	private String email;
	private int id;
	
	
	public Customer(String name, String address, String phone, String email, int id) {
		super();
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.id = id;
	}
	
	
	@Override
	public String toString() {
		return name + ", " + address + ", " + phone + ", " + email;
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}



	public String getAddress() {
		return address;
	}



	public String getPhone() {
		return phone;
	}



	public String getEmail() {
		return email;
	}
		
	
}
