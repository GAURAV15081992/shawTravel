package com.shawtravel.booking.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.shawtravel.booking.service.EmailService;

@Service("EmailServiceImpl")
public class EmailServiceImpl implements EmailService{
	
	public static final Logger logger = Logger.getLogger(EmailServiceImpl.class);
	
	@Autowired
    public JavaMailSender emailSender;

	@Override
	public void sendEmail(String to, String subject, String body, String bccUser) {
		try
		{
			SimpleMailMessage message = new SimpleMailMessage(); 
	        message.setTo(to); 
	        if(bccUser != null)
	        	message.setBcc(bccUser);
	        message.setSubject(subject); 
	        message.setText(body);
	        emailSender.send(message);
		}
		catch(Exception e)
		{
			logger.error("unable to send email. getting exception : "+ e.getMessage());
		}
		
	}
	
	

}
