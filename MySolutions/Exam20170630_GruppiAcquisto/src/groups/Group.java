package groups;

import java.util.LinkedList;
import java.util.List;

public class Group {
	
	private String groupName;
	private String productName;
//	private ProductType product;
	private List<String> customers = new LinkedList<>();
	private List<String> suppliers = new LinkedList<>();
	
	
	public Group(String groupName, String productName) {
		super();
		this.groupName = groupName;
		this.productName = productName;
//		this.product = product;
	}


	public String getGroupName() {
		return groupName;
	}

	
	public String getProductName() {
		return productName;
	}

	
//	public ProductType getProduct() {
//		return product;
//	}


	public List<String> getCustomers() {
		return customers;
	}
	

	public void addCustomer(String customer) {
		customers.add(customer);
	}


	public void addSupplier(String supplier) {
		suppliers.add(supplier);		
	}


	public List<String> getSuppliers() {
		return suppliers;
	}
	
	
}
