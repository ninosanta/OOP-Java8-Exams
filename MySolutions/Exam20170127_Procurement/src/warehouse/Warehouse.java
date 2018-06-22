package warehouse;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

/**
 * Main class
 * @author ninosanta
 */

public class Warehouse {
	
	private static final String CODE = "ORD";
	
	
	private int num = 1;
	private Map<String,Product> products = new HashMap<>();
	private Map<String,Supplier> suppliers = new HashMap<>();
	private Map<String, Order> orders = new HashMap<>();
	
	public Product newProduct(String code, String description) {
		Product p = new Product(code, description);
		products.put(code, p);
		return p;
	}
	
	public Collection<Product> products() {
		return products.values();
	}

	public Product findProduct(String code) {
		return products.get(code);
	}

	public Supplier newSupplier(String code, String name) {
		Supplier s  = new Supplier(code, name);
		suppliers.put(code, s);
		return s;
	}
	
	public Supplier findSupplier(String code) {
		return suppliers.get(code);
	}

	public Order issueOrder(Product prod, int quantity, Supplier supp) throws InvalidSupplier {
		// prodotto, quantita' da ordinare e fornitore
		if (supp.supplies().stream().noneMatch(p -> p.equals(prod)))
			throw new InvalidSupplier();
			
		Order o = new Order(prod, quantity, supp);
		o.setCode(CODE+this.num++);
//		prod.addSupplier(supp);
		orders.put(o.getCode(), o);
		prod.addOrder(o);
//		supp.newSupply(prod);
		
		return o;
	}

	public Order findOrder(String code) {
		return orders.get(code);
	}
	
	public List<Order> pendingOrders() {
		return orders.values().stream()
				.filter(o -> o.delivered() == false)
				.sorted(comparing(o -> o.getProduct().getCode()))
				.collect(toList());
		
	}

	public Map<String,List<Order>> ordersByProduct() {
		return orders.values().stream()
		.collect(groupingBy(o -> o.getProduct().getCode(), 
				toList()));
	}
	
	public Map<String,Long> orderNBySupplier() {
	    return orders.values().stream()
	    .filter(o -> o.delivered() == true)
	    .collect(groupingBy(o -> o.getSupplier().getNome(), 
	    					TreeMap::new,
	    					counting()
	    					)
	    		);
	    
	}
	
	public List<String> countDeliveredByProduct() {
		return orders.values().stream()
		.filter(o -> o.delivered() == true)
		.collect(groupingBy(o -> o.getProduct().getCode(), counting()))
		.entrySet().stream()
		.sorted(comparing(Map.Entry<String,Long>::getValue, reverseOrder()))
		.map(e -> e.getKey() + " - " + e.getValue())
		.collect(toList());
	}
	
}
