package Extras;

public class dataTransferProduct {
	
	String code, title, category, quantity, petUnit, total, discount, netTotal; 

	public dataTransferProduct(String code, String title, String category, String quantity, String petUnit,
			String total, String discount, String netTotal) {
		super();
		this.code = code;
		this.title = title;
		this.category = category;
		this.quantity = quantity;
		this.petUnit = petUnit;
		this.total = total;
		this.discount = discount;
		this.netTotal = netTotal;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPetUnit() {
		return petUnit;
	}

	public void setPetUnit(String petUnit) {
		this.petUnit = petUnit;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(String netTotal) {
		this.netTotal = netTotal;
	}

	public dataTransferProduct() {
		// TODO Auto-generated constructor stub
	}

}
