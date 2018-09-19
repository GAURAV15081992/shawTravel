package com.shawtravel.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.QueryParam;
import com.shawtravel.booking.models.BookingDetails;
import com.shawtravel.booking.models.ReturnStatus;
import com.shawtravel.booking.service.BookingService;

@RestController
public class BookingController {
	@Autowired
	BookingService bookingservice;
	
	@RequestMapping(value="/createbooking",method=RequestMethod.POST)
	public ResponseEntity<ReturnStatus> createBooking(@RequestBody BookingDetails bookingDetails){
		return bookingservice.createBooking(bookingDetails);
	}
	
	@RequestMapping(value = "/getbookings" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BookingDetails> getBookingDetailsByUser(@QueryParam(value = "userId") Long userId)
	{
		return bookingservice.getAllBookingOfUser(userId);
	}
	
	@RequestMapping(value="/cancelbooking",method=RequestMethod.POST)
	public ResponseEntity<ReturnStatus> cancelBooking(@RequestParam("booking_id") Long id){
		return bookingservice.cancelBooking(id);
	}

}
