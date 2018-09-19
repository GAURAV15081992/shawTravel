package com.shawtravel.booking.service;

public interface SmsService {
	
	public void sendSms(String recipient,String message) throws Exception;

}
