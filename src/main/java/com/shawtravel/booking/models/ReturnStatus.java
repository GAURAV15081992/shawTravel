package com.shawtravel.booking.models;

public class ReturnStatus {
	private String status;
	
	private String statusCode;
	
	private String message;

	public ReturnStatus()
	{
		
	}
	public ReturnStatus(String status, String statusCode, String message) {
		super();
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ReturnStatus [status=" + status + ", statusCode=" + statusCode + ", message=" + message + "]";
	}
	
}
