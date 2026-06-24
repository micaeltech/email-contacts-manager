package com.email.Sys.contact.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.email.Sys.contact.model.Contact;
import com.email.Sys.model.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	List<Contact> findByUser(User user);
	
	List<Contact> findByUserAndNicknameContainingIgnoreCase(User user, String nickname);

	boolean existsByUserAndContactUser(User user, User contactUser);
	
	Optional<Contact> findByUserAndNickname(User user, String nickname);
	
	Optional<Contact> findByUserAndContactUser(User user, User contactUser);
}

