package com.shawtravel.booking.exception;

public class ConnectionRefusedException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ConnectionRefusedException(String message) {
		super(message);
	}

}
