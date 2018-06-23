package groups;

public class Bid {
	
	private String groupName;
	private String supplier;
	private int amount;
	
	
	public Bid(String groupName, String supplier, int amount) {
		super();
		this.groupName = groupName;
		this.supplier = supplier;
		this.amount = amount;
	}

	
	public String getGroupName() {
		return groupName;
	}


	public String getSupplier() {
		return supplier;
	}


	public int getAmount() {
		return amount;
	}

}
