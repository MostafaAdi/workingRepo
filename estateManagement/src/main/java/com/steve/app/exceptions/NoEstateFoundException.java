package com.steve.app.exceptions;

public class NoEstateFoundException extends RuntimeException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoEstateFoundException(String message) {
		super(message);
	}
}
