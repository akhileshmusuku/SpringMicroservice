package com.myretail.product.exception;

import java.io.Serializable;

public class InternalServerErrorException extends RuntimeException implements Serializable {
	
	private static final long serialVersionUID = -4034898801657123674L;

	public InternalServerErrorException(String string) {
		super(string);
	}

	public InternalServerErrorException(String string, Throwable cause) {
		super(string, cause);
	}

}
