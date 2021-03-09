package com.myretail.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pricing {
	
	@JsonProperty(value = "value")
	private double value;
	
	@JsonProperty(value = "currency_code")
	private String currency_code;
	
	public Pricing() {
		
	}
	
	public Pricing(double value, String currency_code) {
		super();
		this.value = value;
		this.currency_code = currency_code;
	}

	@Override
	public String toString() {
		return "Pricing [value=" + value + ", currency_code=" + currency_code + "]";
	}

	@JsonProperty(value = "value")
	public double getValue() {
		return value;
	}

	@JsonProperty(value = "value")
	public void setValue(double value) {
		this.value = value;
	}

	@JsonProperty(value = "currency_code")
	public String getCurrency_code() {
		return currency_code;
	}

	@JsonProperty(value = "currency_code")
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}


	

}
