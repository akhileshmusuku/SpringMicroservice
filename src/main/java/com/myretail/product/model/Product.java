package com.myretail.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
	@JsonProperty(value = "id")
	private long id;
	
	@JsonProperty(value = "name")
	private String name;
	
	@JsonProperty(value = "current_price")
	private Pricing current_price;
	
	public Product() {
		
	}
	
	public Product(long id, String name, Pricing current_price) {
		super();
		this.id = id;
		this.name = name;
		this.current_price = current_price;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", current_price=" + current_price + "]";
	}

	@JsonProperty(value = "id")
	public long getId() {
		return id;
	}
	@JsonProperty(value = "id")
	public void setId(long id) {
		this.id = id;
	}
	@JsonProperty(value = "name")
	public String getName() {
		return name;
	}
	@JsonProperty(value = "name")
	public void setName(String name) {
		this.name = name;
	}
	@JsonProperty(value = "current_price")
	public Pricing getPricing() {
		return current_price;
	}
	@JsonProperty(value = "current_price")
	public void setPricing(Pricing current_price) {
		this.current_price = current_price;
	}
	
	

}
