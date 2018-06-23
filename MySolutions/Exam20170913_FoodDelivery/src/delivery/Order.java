package delivery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delivery.Delivery.OrderStatus;

import static java.util.stream.Collectors.*;

public class Order {
	
	private int customerId;
	private Map<MenuItem, Integer> items = new HashMap<>();
	private double total;
	private OrderStatus status = OrderStatus.NEW;
	private int shippingTime;
	private int preparationTimeItems;

	public Order(int customerID) {
		this.customerId = customerID;
	}


	public int getCustomerId() {
		return customerId;
	}

	
	public void putItem(MenuItem menuItem, int qty) {
		if (items.containsKey(menuItem))
			qty += items.get(menuItem);
		items.put(menuItem, qty);			
	}

	
	public double getTotal() {
		total = 0.0;
		items.entrySet().forEach(e -> {
			total += e.getValue() * e.getKey().getPrice();
		});
		return total;
	}

	public OrderStatus getStatus() {
		return status;
	}


	public void setStatus(OrderStatus status) {
		this.status = status;
	}


	public int getItemQuantity(MenuItem menuItem) {
		return items.get(menuItem);
	}
	
	public List<String> getMenuItems() {
		return items.entrySet().stream()
					.map(e -> e.getKey().getDescription()+", "+ e.getValue())
					.collect(toList());
	}


	public int getShippingTime() {
		int delay = 5, transport = 15;
		preparationTimeItems = 0;
		
		items.entrySet().forEach(e -> {
			preparationTimeItems  = (preparationTimeItems < e.getKey().getPrepTime()) ? 
									e.getKey().getPrepTime() : preparationTimeItems;
		});
		
		shippingTime = delay + transport + preparationTimeItems;
		
		return shippingTime;
	}


	
}
