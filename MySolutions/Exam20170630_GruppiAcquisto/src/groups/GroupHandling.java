package groups;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;



/**
 * Main class
 * @author ninosanta
 *
 */
public class GroupHandling {
	
	private List<String> suppliers = new LinkedList<>();
	private List<Bid> bids = new LinkedList<>();
	private Map<String, ProductType> products = new HashMap<>();
	private Map<String, Group> groups = new HashMap<>();
	private List<Bid> votes = new LinkedList<>();
	
	
// R1	
	public void addProduct(String productTypeName, String... supplierNames) 
			throws GroupHandlingException {
		if (products.containsKey(productTypeName))
			throw new GroupHandlingException("Exception in addProduct");
		
		ProductType p = new ProductType(productTypeName);
		for (String supplier : supplierNames) {
			p.addSupplier(supplier);
			this.suppliers.add(supplier);
		}
		this.products.put(productTypeName, p);
	}
	
	public List<String> getProductTypes(String supplierName) {
		return products.values().stream()
				.map(ProductType::getName)
				.sorted()
				.collect(toList());
	}
	
	
// R2	
	public void addGroup(String name, String productName, String... customerNames) 
			throws GroupHandlingException {
		if (groups.containsKey(name) || !products.containsKey(productName))
			throw new GroupHandlingException("Exception in addGroup");
		
		Group g = new Group(name, productName);
		for (String customer : customerNames) {
			g.addCustomer(customer);
		}

		this.groups.put(name, g);
	}
	
	public List<String> getGroups(String customerName) {
        return groups.values().stream()
        		.filter(g -> g.getCustomers().contains(customerName))
        		.map(Group::getGroupName)
        		.sorted()
        		.collect(toList());
	}

// R3
	public void setSuppliers(String groupName, String... supplierNames)
			throws GroupHandlingException {
		
		Group g = groups.get(groupName);
		for (String supplier : supplierNames) {
			if (!suppliers.contains(supplier)  || 
				products.get(groups.get(groupName).getProductName())
					.getSuppliers().stream().noneMatch(s -> s.equals(supplier)))
				throw new GroupHandlingException("Exception in setSuppliers");
				
			g.addSupplier(supplier);
		}
		
	}
	
	public void addBid(String groupName, String supplierName, int price)
			throws GroupHandlingException {

		if (groups.get(groupName).getSuppliers().stream().noneMatch(s -> s.equals(supplierName)))
			throw new GroupHandlingException("Exception in addBid");
		
		Bid bid = new Bid(groupName, supplierName, price);
		bids.add(bid);
	}
	
	public String getBids (String groupName) {
		// collect con joining
		return bids.stream().sorted(comparing(Bid::getAmount).thenComparing(Bid::getSupplier))
				.map(bid -> bid.getSupplier()+ ":" + bid.getAmount())
				.collect(joining(",")); 
	}
	
	
// R4	
	public void vote(String groupName, String customerName, String supplierName)
			throws GroupHandlingException {
		if (groups.get(groupName).getCustomers().stream()
				  .noneMatch(s -> s.equals(customerName)) || 
			bids.stream().noneMatch(bid -> (bid.getSupplier().equals(supplierName) && 
					                        bid.getGroupName().equals(groupName))))
			throw new GroupHandlingException("Exception in vote()");
		
		Bid b = bids.stream().filter(bid -> bid.getSupplier()
				.equals(supplierName)).findFirst().orElse(null);
		votes.add(b);
	}
	
	public String getVotes(String groupName) {
		return votes.stream().filter(bid -> bid.getGroupName().equals(groupName))
			 .sorted(comparing(Bid::getSupplier))
			 .collect(groupingBy(Bid::getSupplier, 
					 			 TreeMap::new, 
					 			 counting()
					 			 )
					 )
			 .entrySet().stream()
			 .filter(e -> e.getValue() > 0)
			 .map(e -> e.getKey() + ":" + e.getValue())
			 .collect(joining(","));
	}
	
	public String getWinningBid (String groupName) {
		return votes.stream().filter(bid -> bid.getGroupName().equals(groupName))
		 .sorted(comparing(Bid::getSupplier))
		 .collect(groupingBy(Bid::getSupplier, 
//				 			 TreeMap::new, 
				 			 counting()
				 			 )
				 )
		 .entrySet().stream()
		 // primo ordinamento, per voti dal maggiore al minore:
		 .sorted(comparing(e -> e.getValue(), reverseOrder()))
		 // secondo ordinamento, per costo inferiore:
		 .sorted(comparing(e -> bids.stream()
				 					.filter(bid -> bid.getSupplier().equals(e.getKey()))
				 					.map(Bid::getAmount)
				 					.findFirst().orElse(0)
				 		  ))
		 .filter(e -> e.getValue() > 0)
		 .map(e -> e.getKey() + ":" + e.getValue())
		 .findFirst().orElse(null);
	}
	
// R5
	public SortedMap<String, Integer> maxPricePerProductType() { 
		// serve toMap
		return bids.stream()  // Stream<Bid>
		.collect(groupingBy(bid -> groups.get(bid.getGroupName()).getProductName(),
							TreeMap::new,
							collectingAndThen(toList(), 
									          l -> l.stream()
									          	    .map(b -> b.getAmount())
									          	    .sorted(reverseOrder())
									          	    .findFirst().orElse(0)
									         )
							)
					);
	}
	
	
	
	public SortedMap<Integer, List<String>> suppliersPerNumberOfBids() {
       return bids.stream()
        	.collect(groupingBy(Bid::getSupplier, counting()))
        	.entrySet().stream()  // Stream<Map.Entry<String, Long>>
        			 .collect(groupingBy(e -> e.getValue(),
//        					  			() -> new TreeMap<Long,List<String>>(reverseOrder()),
        					 			mapping(Map.Entry<String, Long>::getKey, 
        					 					toList()))
        					 )
        			 .entrySet().stream()
        			 .collect(toMap(e -> e.getKey().intValue(),
        					        e -> e.getValue().stream().sorted().collect(toList()),
        					        (a,b) -> (a),  // inutile
        					        () -> new TreeMap<Integer,List<String>>(reverseOrder())));
	}
	
	
	public SortedMap<String, Long> numberOfCustomersPerProductType() {
		return groups.values().stream()
		.collect(groupingBy(Group::getProductName,
							TreeMap::new,
							summingLong(group -> group.getCustomers().size())));
	}
	
}
