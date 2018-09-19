package com.shawtravel.booking.constants;

/*****************************************************************************************
* 
* @author 					Gaurav Gupta(gaurav16.gupta@aricent.com)
* @version 					1.0
* @since   					1.0
* created on				2018/03/17
* 
*****************************************************************************************/

public interface AppConstant {

	int SUCCESS = 1;
	int FAILURE = 0;
	String INVALID_AUTHORIZATION_HEADER = "Invalid Authorization header";
	public static final String PANNET_AUTHORIZATION = "TOKEN_INTERNAL";
	public static final String UNAUTHORIZED_EXCEPTION = "Unauthorized Exception";
	public static final String FAIL = "Failed";
	public static final String NOT_FOUND="404";
	public static final String NO_TICKET_FOUND="No Ticket Found";
	public static final String CONNECTION_NOT_FOUND="500";
	public static final String UNABLE_TO_RETRIEVE="Unable to retrieve data";
	public static final long OTP_EXPIRY_TIME_IN_MINUTES = 10;
	
}
