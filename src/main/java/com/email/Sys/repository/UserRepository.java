package com.email.Sys.repository;

import com.email.Sys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	List<User> findByName(String name);
	
	List<User> findByStatus(User.Status status);
	
	long countByStatus(User.Status status);
}
