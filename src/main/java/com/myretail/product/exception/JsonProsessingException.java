package com.myretail.product.exception;

import java.io.Serializable;

public class JsonProsessingException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -6563218748988016571L;

	public JsonProsessingException(String message) {
		super(message);
	}

	public JsonProsessingException(String string, Throwable cause) {
		super(string, cause);
	}

}
