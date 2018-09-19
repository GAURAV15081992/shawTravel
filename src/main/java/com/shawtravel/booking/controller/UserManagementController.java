package com.shawtravel.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shawtravel.booking.models.ReturnStatus;
import com.shawtravel.booking.models.UserDetails;
import com.shawtravel.booking.models.UserResponse;
import com.shawtravel.booking.service.UserManagementService;

@RestController
public class UserManagementController {
	
	@Autowired
	UserManagementService userManagementService;
	
	@RequestMapping(value="/registeruser",method=RequestMethod.POST)
	public ResponseEntity<ReturnStatus> registerUser(@RequestBody UserDetails userDetails){
		return userManagementService.registerUser(userDetails);
	}
	
	@RequestMapping(value="/loginuser",method=RequestMethod.POST)
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserDetails userDetails) throws Exception{
		return userManagementService.loginUser(userDetails);
	}
	
	@RequestMapping(value="/otpverification",method=RequestMethod.GET)
	public ResponseEntity<UserResponse> accountVerification(@RequestParam("phone") String phone,@RequestParam("otp") String otp) throws Exception{
		return userManagementService.validateAccount(phone,otp);
	}
	
	@RequestMapping(value="/resetpassword",method=RequestMethod.POST)
	public ResponseEntity<ReturnStatus> resetPassword(@RequestBody UserDetails userDetails){
		return userManagementService.resetPassword(userDetails);
	}
}
