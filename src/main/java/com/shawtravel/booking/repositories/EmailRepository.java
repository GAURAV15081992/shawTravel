package com.shawtravel.booking.repositories;

import org.springframework.data.repository.CrudRepository;
import com.shawtravel.booking.models.EmailVerification;

public interface EmailRepository extends CrudRepository<EmailVerification, String> {
	
	EmailVerification findByEmail(String email);

}
