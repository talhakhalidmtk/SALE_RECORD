package POS;

public class billView {
	
	String ID,customerName,salesPerson,quantity,totalAmount,discount,Adjustment,grandTotal,
	received,change,member, billType,comments;
	

	public billView(String iD, String customerName, String salesPerson, String quantity, String totalAmount,
			String discount, String adjustment, String grandTotal, String received, String change, String member,
			String billType, String comments) {
		super();
		ID = iD;
		this.customerName = customerName;
		this.salesPerson = salesPerson;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
		this.discount = discount;
		Adjustment = adjustment;
		this.grandTotal = grandTotal;
		this.received = received;
		this.change = change;
		this.member = member;
		this.billType = billType;
		this.comments = comments;
	}


	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getSalesPerson() {
		return salesPerson;
	}


	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}


	public String getQuantity() {
		return quantity;
	}


	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}


	public String getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}


	public String getDiscount() {
		return discount;
	}


	public void setDiscount(String discount) {
		this.discount = discount;
	}


	public String getAdjustment() {
		return Adjustment;
	}


	public void setAdjustment(String adjustment) {
		Adjustment = adjustment;
	}


	public String getGrandTotal() {
		return grandTotal;
	}


	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}


	public String getReceived() {
		return received;
	}


	public void setReceived(String received) {
		this.received = received;
	}


	public String getChange() {
		return change;
	}


	public void setChange(String change) {
		this.change = change;
	}


	public String getMember() {
		return member;
	}


	public void setMember(String member) {
		this.member = member;
	}


	public String getBillType() {
		return billType;
	}


	public void setBillType(String billType) {
		this.billType = billType;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public billView() {
		// TODO Auto-generated constructor stub
	}

}
