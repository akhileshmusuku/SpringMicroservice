package com.myretail.product.dbentity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Product")
public class ProductEntity {
	
	@Id
	private long id;
	private double price;
	private String currenc_Code;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCurrenc_Code() {
		return currenc_Code;
	}

	public void setCurrenc_Code(String currenc_Code) {
		this.currenc_Code = currenc_Code;
	}
	

}
