package groups;

import java.util.LinkedList;
import java.util.List;

public class ProductType {
	
	private String name;
	private List<String> suppliers = new LinkedList<>();
	
	
	public ProductType(String name) {
		super();
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public List<String> getSuppliers() {
		return suppliers;
	}
	
	
	public void addSupplier(String supplier) {
		suppliers.add(supplier);
	}
}
