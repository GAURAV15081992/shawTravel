package com.shawtravel.booking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shawtravel.booking.models.BookingDetails;

public interface BookingRepository extends CrudRepository<BookingDetails, Long>{
	
	BookingDetails findById(Long id);
	
	@Query(value = "select * from t_booking_details u where u.user_id=?1", nativeQuery = true)
	List<BookingDetails> geBookingDetails(Long userId);
	
	@Query(value = "select * from t_booking_details u where u.user_id=?1 order by booking_id desc limit 1", nativeQuery = true)
	BookingDetails getLatestBooking(Long userId);
}
