package com.shawtravel.booking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.shawtravel.booking.dbmanager.DBManager;
import com.shawtravel.booking.models.ReturnStatus;
import com.shawtravel.booking.models.UserDetails;
import com.shawtravel.booking.models.UserResponse;
import com.shawtravel.booking.service.UserManagementService;

@Service("UserManagementServiceImpl")
public class UserManagementServiceImpl implements UserManagementService{
	@Autowired
	DBManager dbManager;
	
	@Override
	public ResponseEntity<ReturnStatus> registerUser(UserDetails userDetails) {
		return dbManager.registerUser(userDetails);
	}

	@Override
	public ResponseEntity<UserResponse> loginUser(UserDetails userDetails) throws Exception {
		return dbManager.loginUser(userDetails);
	}

	@Override
	public ResponseEntity<UserResponse> validateAccount(String phone, String otp) throws Exception {
		return dbManager.validateAccount(phone,otp);
	}

	@Override
	public ResponseEntity<ReturnStatus> resetPassword(UserDetails userDetails) {
		return dbManager.resetPassword(userDetails);
	}

}
