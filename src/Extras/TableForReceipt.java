package Extras;

public class TableForReceipt {
	
	String serial,title,quantity,perUnit,netTotal;

	public TableForReceipt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TableForReceipt(String serial, String title, String quantity, String perUnit, 
			String netTotal) {
		super();
		this.serial = serial;
		this.title = title;
		this.quantity = quantity;
		this.perUnit = perUnit;
		this.netTotal = netTotal;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPerUnit() {
		return perUnit;
	}

	public void setPerUnit(String perUnit) {
		this.perUnit = perUnit;
	}


	public String getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(String netTotal) {
		this.netTotal = netTotal;
	}

	
}
