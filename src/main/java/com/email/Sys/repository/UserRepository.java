package com.email.Sys.repository;

import com.email.Sys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	// User search at login & add email contacts
	Optional<User> findByEmail(String email);
	
	// prevent duplicates
	boolean existsByEmail(String email);
	
	// SEARCH
	
	// find by name
	List<User> findByName(String name);
	
	// find by status
	List<User> findByStatus(User.Status status);
	
	// count by status
	long countByStatus(User.Status status);
}
