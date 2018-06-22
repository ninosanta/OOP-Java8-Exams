package warehouse;


public class Order {
	
	private Product product; 
	private int quantity; 
	private Supplier supplier;
	private String code;
	private boolean delivered = false;
	
	
	public Order(Product product, int quantity, Supplier supplier) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.supplier = supplier;
	}

	
	public void setCode(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public Supplier getSupplier() {
		return supplier;
	}


	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}


	public boolean delivered() {
		return delivered;
	}

	public void setDelivered() throws MultipleDelivery {
		if (delivered == true)
			throw new MultipleDelivery();
		product.setQuantity(product.getQuantity() + this.quantity);
		delivered = true;
	}
	
	@Override
	public String toString() {
		return "Order "+this.getCode()+" for "+this.getQuantity()+" of "
				+product.getCode()+" : "+product.getDescription()
				+" from "+supplier.getNome();
	}
}
