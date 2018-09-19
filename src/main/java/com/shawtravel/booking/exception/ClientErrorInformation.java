package com.shawtravel.booking.exception;

public class ClientErrorInformation {

	private final String message;
	private final String status;
	private final String code;

	public ClientErrorInformation(String message, String status, String code) {
		super();
		this.code = code;
		this.message = message;
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getStatus() {
		return status;
	}

}

