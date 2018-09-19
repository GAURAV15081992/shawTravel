package com.shawtravel.booking.models;

public class UserResponse {
	
private String status;

	private UserDetails userDetails;
	
	private String statusCode;
	
	private String message;

	public UserResponse()
	{
		
	}
	
	public UserResponse(UserDetails userDetails,String status, String statusCode, String message) {
		super();
		this.userDetails =  userDetails;
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public UserDetails getUserDetails() {
		return userDetails;
	}
	
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
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

}
