package com.myretail.product.exception;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ExceptionResponse {
	
	private Date date;
	private String message;
	private String status;
	
	public ExceptionResponse() {
		
	}
	
	public ExceptionResponse(Date date, String message, String status) {
		super();
		this.date = date;
		this.message = message;
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
