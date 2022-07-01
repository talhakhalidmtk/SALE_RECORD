package Extras;

import java.util.ArrayList;

import POS.Product;

public class DataTransferTable {

	String className;
	String salesPerson;
	public String getSalesPerson() {
		return salesPerson;
	}
	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}
	public DataTransferTable(String className, String salesPerson, String bill, String customerID, String customerName,
			String billType, boolean mem, String discount, String comment, ArrayList<dataTransferProduct> array,
			Product pro) {
		super();
		this.className = className;
		this.salesPerson = salesPerson;
		this.bill = bill;
		this.customerID = customerID;
		this.customerName = customerName;
		this.billType = billType;
		this.mem = mem;
		this.discount = discount;
		this.comment = comment;
		this.array = array;
		this.pro = pro;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	String bill;
	String customerID;
	String customerName;

	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public boolean isMem() {
		return mem;
	}
	public void setMem(boolean mem) {
		this.mem = mem;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public ArrayList<dataTransferProduct> getArray() {
		return array;
	}
	public void setArray(ArrayList<dataTransferProduct> array) {
		this.array = array;
	}
	public Product getPro() {
		return pro;
	}
	public void setPro(Product pro) {
		this.pro = pro;
	}
	String billType;
	boolean mem;
	String discount;
	String comment;
	ArrayList<dataTransferProduct> array = new ArrayList<dataTransferProduct>();
	Product pro = null;
	
}
