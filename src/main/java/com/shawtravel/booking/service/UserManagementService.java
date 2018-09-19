package com.shawtravel.booking.service;

import org.springframework.http.ResponseEntity;

import com.shawtravel.booking.models.ReturnStatus;
import com.shawtravel.booking.models.UserDetails;
import com.shawtravel.booking.models.UserResponse;

public interface UserManagementService {
	
	public ResponseEntity<ReturnStatus> registerUser(UserDetails userDetails);
	
	public ResponseEntity<UserResponse> loginUser(UserDetails userDetails) throws Exception;
	
	public ResponseEntity<UserResponse> validateAccount(String phone,String otp) throws Exception;

	public ResponseEntity<ReturnStatus> resetPassword(UserDetails userDetails);

}
