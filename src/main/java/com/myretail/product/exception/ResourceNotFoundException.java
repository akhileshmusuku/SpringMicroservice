package com.myretail.product.exception;

import java.io.Serializable;

public class ResourceNotFoundException extends RuntimeException implements Serializable{
	
	private static final long serialVersionUID = 5561040348988016571L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
