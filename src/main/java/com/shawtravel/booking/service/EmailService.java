package com.shawtravel.booking.service;

public interface EmailService {

	public void sendEmail(String to,String subject,String message,String bccUser);
}
