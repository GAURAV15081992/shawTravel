/*
 * @file UserRepository.java
 *
 */
package com.shawtravel.booking.repositories;

import org.springframework.data.repository.CrudRepository;

import com.shawtravel.booking.models.UserDetails;

public interface UserRepository extends CrudRepository<UserDetails, Long> {
	
	UserDetails findById(Long id);
	
	UserDetails findByPhoneNumber(String phoneNumber);
    
	UserDetails findByEmail(String email);
}
