package warehouse;

import java.util.List;
import java.util.LinkedList;


import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;


public class Product {

	private String code; 
	private String description;
	private int quantity;
	private List<Supplier> suppliers = new LinkedList<>();
	private List<Order> orders = new LinkedList<>();  // ordini per questo prodotto
	
	public Product(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void decreaseQuantity() {
		this.quantity--;
	}

	public int getQuantity(){
		return quantity;
	}

	
	public void addSupplier(Supplier supplier) {
		suppliers.add(supplier);
	}
	
	
	public List<Supplier> suppliers(){
		return suppliers.stream()
				.sorted(comparing(Supplier::getNome))
				.collect(toList());
	}

	public List<Order> pendingOrders() {
		return orders.stream()
				.filter(o -> o.delivered() == false)
				.sorted(comparing(Order::getQuantity, reverseOrder()))
				.collect(toList());
	}

	public void addOrder(Order order) {
		this.orders.add(order);
	}

}
