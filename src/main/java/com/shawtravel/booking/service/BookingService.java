package com.shawtravel.booking.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.shawtravel.booking.models.BookingDetails;
import com.shawtravel.booking.models.ReturnStatus;

public interface BookingService {

	public ResponseEntity<ReturnStatus> createBooking(BookingDetails userDetails);
	
	public List<BookingDetails> getAllBookingOfUser(Long userId);

	public ResponseEntity<ReturnStatus> cancelBooking(Long id);
}
