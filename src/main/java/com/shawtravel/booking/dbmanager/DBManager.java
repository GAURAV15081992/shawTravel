package com.shawtravel.booking.dbmanager;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shawtravel.booking.constants.AppConstant;
import com.shawtravel.booking.exception.ConnectionRefusedException;
import com.shawtravel.booking.models.BookingDetails;
import com.shawtravel.booking.models.EmailVerification;
import com.shawtravel.booking.models.ReturnStatus;
import com.shawtravel.booking.models.UserDetails;
import com.shawtravel.booking.models.UserResponse;
import com.shawtravel.booking.repositories.BookingRepository;
import com.shawtravel.booking.repositories.EmailRepository;
import com.shawtravel.booking.repositories.UserRepository;
import com.shawtravel.booking.service.EmailService;
import com.shawtravel.booking.service.SmsService;
import com.shawtravel.booking.utils.ShaSecurity;


@Service("DBManager")
@Configuration
@PropertySource(value = "classpath:config.properties", ignoreResourceNotFound = true)
public class DBManager {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	EmailRepository emailRepository;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	private Environment environment;

	private static final Logger logger = Logger.getLogger(DBManager.class);

	public ResponseEntity<ReturnStatus> registerUser(UserDetails userDetails)
	{
		ReturnStatus response = new ReturnStatus();
		String encryptedPassword = null;
		try
		{
			UserDetails userbyPhone = userRepository.findByPhoneNumber(userDetails.getPhoneNumber());
			UserDetails userbyEmail = userRepository.findByEmail(userDetails.getEmail());
			
			if(userbyPhone != null || userbyEmail!=null)
			{
				response.setStatusCode("409");
				response.setStatus("User Already Exists");
				response.setMessage("Please provide unique phonenumber and email address");
				return new ResponseEntity<ReturnStatus>(response, HttpStatus.CONFLICT);
			}
			encryptedPassword = ShaSecurity.hashMD5(userDetails.getPassword());
			userDetails.setStatus("inactive");
			userDetails.setPassword(encryptedPassword);
			userRepository.save(userDetails);
			response.setStatusCode("201");
			response.setStatus("created");
			response.setMessage("user created successfully");
		}catch(Exception e)
		{
			logger.info("exception caught : "+ e.getMessage());
		}
		return new ResponseEntity<ReturnStatus>(response, HttpStatus.CREATED);
	}
	
	public ResponseEntity<UserResponse> loginUser(UserDetails userDetails) throws Exception {
		UserDetails userbyPhone = userRepository.findByPhoneNumber(userDetails.getPhoneNumber());
		UserResponse response = new UserResponse();
		if(userbyPhone == null)
		{
			response.setStatusCode("404");
			response.setStatus("User not found");
			response.setMessage("Please provide correct loginname");
			return new ResponseEntity<UserResponse>(response, HttpStatus.NOT_FOUND);
		}
		String actualPassword = ShaSecurity.hashMD5(userDetails.getPassword());
		String expectedPassword =  userbyPhone.getPassword();
		if(actualPassword.equals(expectedPassword))
		{
			if(userbyPhone.getStatus().equals("active"))
			{
				response.setUserDetails(userbyPhone);
				response.setStatusCode("200");
				response.setStatus("LOGIN_SUCCESS");
				response.setMessage("Successfully logged in");
			}
			else
			{
				String otp = generateOtp();
				String message = otp + " is the Onetime password(OTP) for your account verification. This is usable once and valid "+
						"for " + AppConstant.OTP_EXPIRY_TIME_IN_MINUTES + " mins from the request.";
				String recipients =  "91"+userbyPhone.getPhoneNumber();
				sendOtp(userbyPhone,otp);
				smsService.sendSms(recipients,message);
				
				response.setStatusCode("405");
				response.setStatus("ACCOUNT_VERIFICATION_PENDING");
				response.setMessage("User is in inactive state. Please validate your phone number");
			}
			return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
		}
		response.setStatusCode("404");
		response.setStatus("LOGIN_FAILED");
		response.setMessage("Invalid UserName or Password");
		return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<ReturnStatus> createBooking(BookingDetails bookingDetails) {
		ReturnStatus response = new ReturnStatus();
		try
		{
			bookingDetails.setBookingStatus("Active");
			bookingRepository.save(bookingDetails);
			Long userId =  bookingDetails.getUser_id();
			UserDetails userDetails = userRepository.findById(userId);
			BookingDetails booking = bookingRepository.getLatestBooking(userId);
			String subject = "Booking Created";
			String toAddressUser = userDetails.getEmail();
			String toAddressOwner = environment.getProperty("sms.owner.email");
			String recipientUser = "91"+userDetails.getPhoneNumber();
			String recipientOwner = "91"+environment.getProperty("sms.ownerphone");
			String shareRide = "No";
			if(bookingDetails.isShareRide()==1)
				shareRide = "Yes";
			String isReturn = "No";
			if(bookingDetails.getIsReturn()==1)
				isReturn = "Yes";
			
			String messageEmailUser = "Dear " + userDetails.getFirstName()+ "," + "\n\n" + "Your booking has been created successfully with below details: \n"+
			"Booking Id : " + booking.getId() +"\n"
			+"Start Location : " + bookingDetails.getStartLocation() +"\n"
			+"End Location : " + bookingDetails.getEndLocation() +"\n"
			+"Start Date : " + bookingDetails.getBookingCreationDate() +"\n"
			+"Return Availability : " + isReturn +"\n"
			+"Shared Ride Availability : " + shareRide +"\n"
			+ "For any further queries, Please contact us at " + environment.getProperty("sms.ownerphone");
			
			String messageEmailOwner = "Hi," + "\n\n" + "New booking has been created with below details: \n"
					+"Booking Id : " + booking.getId() +"\n"
					+"Contact Number : " + userDetails.getPhoneNumber() +"\n"
					+"Start Location : " + bookingDetails.getStartLocation() +"\n"
					+"End Location : " + bookingDetails.getEndLocation() +"\n"
					+"Start Date : " + bookingDetails.getBookingCreationDate() +"\n"
					+"Return Availability : " + isReturn +"\n"
					+"Shared Ride Availability : " + shareRide +"\n"
					+"Created By : " + toAddressUser +"\n";
			
			String bookingMessageUser =  "Your booking is created with Shaw Travels successfully.Please find below details: \n"
			+"Booking Id : " + booking.getId() +"\n"
			+"Start Location : " + bookingDetails.getStartLocation() +"\n"
			+"End Location : " + bookingDetails.getEndLocation() +"\n"
			+"Start Date : " + bookingDetails.getBookingCreationDate() +"\n"
			+"Return Availability : " + isReturn +"\n"
			+"Shared Ride Availability : " + shareRide +"\n"
			+"For any further queries, Please contact us at " + environment.getProperty("sms.ownerphone");
			
			String bookingMessageOwner =  "New booking has been created by "+ userDetails.getFirstName() + " " + userDetails.getLastName() +"."
					+ "Please find below details: \n"
					+ "Booking Id : " + booking.getId() +"\n"
					+ "Contact Number : " + userDetails.getPhoneNumber() +"\n"
					+ "Start Location : " + bookingDetails.getStartLocation() +"\n"
					+ "End Location : " + bookingDetails.getEndLocation() +"\n"
					+ "Start Date : " + bookingDetails.getBookingCreationDate() +"\n"
					+ "Return Availability : " + isReturn +"\n"
					+ "Shared Ride Availability : " + shareRide +"\n"
					+ "Created By : " + toAddressUser +"\n";
			
			try {
				smsService.sendSms(recipientUser, bookingMessageUser);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Unable to send message due to error "+ e.getMessage());
			}
			try {
				smsService.sendSms(recipientOwner, bookingMessageOwner);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Unable to send message due to error "+ e.getMessage());
			}
			emailService.sendEmail(toAddressUser, subject, messageEmailUser, null);
			emailService.sendEmail(toAddressOwner, subject, messageEmailOwner, null);
			
			
			response.setStatusCode("201");
			response.setStatus("CREATED");
			response.setMessage("booking created successfully");
		}
		catch(Exception e)
		{
			logger.info("Excepion occured while create booking "+ e.getMessage());
		}
		return new ResponseEntity<ReturnStatus>(response, HttpStatus.CREATED);
	}

	public List<BookingDetails> getAllBookingOfUser(Long userId) {
		return bookingRepository.geBookingDetails(userId);
	}
	
	public static String generateOtp()
	{
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		return String.valueOf(n);
	}
	
	public static long timeDifferenceInMinutes(Date time1,Date time2)
	{
		long diff = time1.getTime() - time2.getTime();
		return diff / (60 * 1000) % 60;
	}
	
	public void sendOtp(UserDetails userDetails,String otp)
	{
		EmailVerification emailVerify = emailRepository.findByEmail(userDetails.getEmail());
		logger.info("Email Verification value is coming as :"+emailVerify);
		if(emailVerify != null)
		{
			emailRepository.delete(emailVerify);
		}
		EmailVerification emailVerification = new EmailVerification();
		emailVerification.setEmail(userDetails.getEmail());
		emailVerification.setOtp(otp);
		emailRepository.save(emailVerification);
		String subject = "OTP Generated";
		String toAddress = userDetails.getEmail();
		String message = "Dear " + userDetails.getFirstName() + ",\n\n" + otp + " is the Onetime password(OTP) for your account verification. This is usable once and valid "+
		"for " + AppConstant.OTP_EXPIRY_TIME_IN_MINUTES + " mins from the request.";
		emailService.sendEmail(toAddress, subject, message, null);
	}

	public ResponseEntity<UserResponse> validateAccount(String phone, String otp) throws Exception {
		UserResponse response = new UserResponse();
		UserDetails userDetails = userRepository.findByPhoneNumber(phone);
		if(userDetails != null)
		{
			response.setUserDetails(userDetails);
			String email = userDetails.getEmail();
			EmailVerification emailVerification = emailRepository.findByEmail(email);
			if(emailVerification != null)
			{
				String expectedToken = emailVerification.getOtp();
				Date creationTime =  emailVerification.getCreatedAt();
				if(!expectedToken.equals(otp))
				{
					response.setStatus("404");
					response.setStatus("INVALID_OTP");
					response.setMessage("Invalid OTP supplied. Please provide valid OTP");
				}
				else
				{
					Date currentTime = new Date();
					if(timeDifferenceInMinutes(currentTime,creationTime) > AppConstant.OTP_EXPIRY_TIME_IN_MINUTES)
					{
						String message = otp + " is the Onetime password(OTP) for your account verification. This is usable once and valid "+
								"for " + AppConstant.OTP_EXPIRY_TIME_IN_MINUTES + " mins from the request.";
						String recipients =  "91"+userDetails.getPhoneNumber();
						emailRepository.delete(emailVerification);
						sendOtp(userDetails,otp);
						
						smsService.sendSms(recipients,message);
						response.setStatusCode("404");
						response.setStatus("INVALID_OTP");
						response.setMessage("OTP is expired");
						
					}
					else
					{
						userDetails.setStatus("active");
						userRepository.save(userDetails);
						response.setStatusCode("200");
						response.setStatus("LOGIN_SUCCESS");
						response.setMessage("Login Successful");
					}
				}
			}
		}
		else
		{
			response.setStatusCode("404");
			response.setStatus("INVALID_USER");
			response.setMessage("USER NOT FOUND");
		}
		return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<ReturnStatus> resetPassword(UserDetails userDetails) {
		String phone = userDetails.getPhoneNumber();
		ReturnStatus response = new ReturnStatus();
		UserDetails user = userRepository.findByPhoneNumber(phone);
		if(user == null)
		{
			response.setStatus("RESET_PASSWORD_FAILURE");
			response.setStatusCode("406");
			response.setMessage("No Account is associated with this Phone Number");
		}
		else
		{
			String encryptedPassword = ShaSecurity.hashMD5(userDetails.getPassword());
			user.setPassword(encryptedPassword);
			user.setStatus("inactive");
			userRepository.save(user);
			response.setStatus("RESET_PASSWORD_SUCCESS");
			response.setStatusCode("200");
			response.setMessage("Reset Password Successful");
		}
		return new ResponseEntity<ReturnStatus>(response, HttpStatus.OK);
	}

	public ResponseEntity<ReturnStatus> cancelBooking(Long id) {
		ReturnStatus response = new ReturnStatus();
		try
		{
			BookingDetails bookingDetails = bookingRepository.findById(id);
			bookingDetails.setBookingStatus("Cancelled");
			bookingRepository.save(bookingDetails);
			Long userId =  bookingDetails.getUser_id();
			UserDetails userDetails = userRepository.findById(userId);
			String subject = "Booking Cancelled";
			String toAddressUser = userDetails.getEmail();
			String toAddressOwner = environment.getProperty("sms.owner.email");
			String recipientUser = "91"+userDetails.getPhoneNumber();
			String recipientOwner = "91"+environment.getProperty("sms.ownerphone");
			
			String messageEmailUser = "Hi " + userDetails.getFirstName() +"," + "\n\n" + "Your booking has been cancelled with below details: \n"
					+"Booking Id : " + bookingDetails.getId() +"\n"
					+"Start Location : " + bookingDetails.getStartLocation() +"\n"
					+"End Location : " + bookingDetails.getEndLocation() +"\n"
					+"Start Date : " + bookingDetails.getBookingCreationDate() +"\n";
			
			String messageEmailOwner = "Hi,"+ "\n\n" + "Booking has been cancelled. Please find below booking details: \n"
					+"Booking Id : " + bookingDetails.getId() +"\n"
					+"Contact Number : " + userDetails.getPhoneNumber() +"\n"
					+"Start Location : " + bookingDetails.getStartLocation() +"\n"
					+"End Location : " + bookingDetails.getEndLocation() +"\n"
					+"Start Date : " + bookingDetails.getBookingCreationDate() +"\n"
					+"Contact Number : " + userDetails.getFirstName() + " " + userDetails.getLastName() +"\n";
			
			String smsMessageUser = "Your booking has been cancelled with Booking Id "+ bookingDetails.getId() ;
			
			String smsMessageOwner = "Booking has been cancelled. Please find below booking details: \n"
					+"Booking Id : " + bookingDetails.getId() +"\n"
					+"Contact Number : " + userDetails.getPhoneNumber() +"\n"
					+"Start Location : " + bookingDetails.getStartLocation() +"\n"
					+"End Location : " + bookingDetails.getEndLocation() +"\n"
					+"Start Date : " + bookingDetails.getBookingCreationDate() +"\n"
					+"Contact Number : " + userDetails.getFirstName() + " " + userDetails.getLastName() +"\n";
			
			emailService.sendEmail(toAddressUser, subject, messageEmailUser, null);
			emailService.sendEmail(toAddressOwner, subject, messageEmailOwner, null);
			
			try {
				smsService.sendSms(recipientUser, smsMessageUser);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Unable to send message due to error "+ e.getMessage());
			}
			try {
				smsService.sendSms(recipientOwner, smsMessageOwner);
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Unable to send message due to error "+ e.getMessage());
			}
			
			response.setStatus("SUCCESS");
			response.setStatusCode("200");
			response.setMessage("Successfully Cancelled the booking");
			
			return new ResponseEntity<ReturnStatus>(response, HttpStatus.OK);
		}
		catch(Exception e)
		{
			logger.error("Unable to delete booking" + e.getMessage());
			throw new ConnectionRefusedException("Unable to delete booking");
		}	
	}
}
