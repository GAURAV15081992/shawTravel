package com.shawtravel.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import com.shawtravel.booking.constants.AppConstant;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = DataNotFoundException.class)
	public ResponseEntity<ClientErrorInformation> handleDataNotFoundException(Exception e) {
		ClientErrorInformation error = new ClientErrorInformation(e.getMessage(), AppConstant.FAIL,
				AppConstant.NOT_FOUND);
		return new ResponseEntity<ClientErrorInformation>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = ConnectionRefusedException.class)
	public ResponseEntity<ClientErrorInformation> handleConnectionRefusedException(Exception e) {
		ClientErrorInformation error = new ClientErrorInformation(e.getMessage(), AppConstant.FAIL,
				AppConstant.CONNECTION_NOT_FOUND);
		return new ResponseEntity<ClientErrorInformation>(error, HttpStatus.FORBIDDEN);
	}
}

