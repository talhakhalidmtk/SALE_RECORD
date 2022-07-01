package POS;

public class Product {
public String code,title,category,salePrice,available;

public Product(String code, String title, String category, String salePrice, String available) {
	super();
	this.code = code;
	this.title = title;
	this.category = category;
	this.salePrice = salePrice;
	this.available = available;
}

public Product() {
	// TODO Auto-generated constructor stub
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

public String getSalePrice() {
	return salePrice;
}

public void setSalePrice(String salePrice) {
	this.salePrice = salePrice;
}

public String getAvailable() {
	return available;
}

public void setAvailable(String available) {
	this.available = available;
}
	
	
	
}
