package com.shawtravel.booking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shawtravel.booking.dbmanager.DBManager;
import com.shawtravel.booking.models.BookingDetails;
import com.shawtravel.booking.models.ReturnStatus;
import com.shawtravel.booking.repositories.BookingRepository;
import com.shawtravel.booking.service.BookingService;

@Service("BookingServiceImpl")
public class BookingServiceImpl implements BookingService{
	@Autowired
	DBManager dbManager;

	@Override
	public ResponseEntity<ReturnStatus> createBooking(BookingDetails bookingDetails) {
		return dbManager.createBooking(bookingDetails);
	}

	@Override
	public List<BookingDetails> getAllBookingOfUser(Long userId) {
		return dbManager.getAllBookingOfUser(userId);
	}

	@Override
	public ResponseEntity<ReturnStatus> cancelBooking(Long id) {
		return dbManager.cancelBooking(id);
	}

}
