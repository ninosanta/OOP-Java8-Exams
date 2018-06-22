package warehouse;

import java.util.List;

import static java.util.stream.Collectors.*;

import java.util.LinkedList;

import static java.util.Comparator.*;

public class Supplier {
	
	private String code; 
	private String name;
	private List<Product> products = new LinkedList<>();
	
	public Supplier(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCodice(){
		return code;
	}

	public String getNome(){
		return name;
	}
	
	public void newSupply(Product product) {
		products.add(product);
		product.addSupplier(this);
	}
	
	public List<Product> supplies() {
		return products.stream()
				.sorted(comparing(Product::getDescription))
				.collect(toList());
	}
	
}
