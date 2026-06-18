package com.email.Sys.email.repository;

import com.email.Sys.email.model.Email;
import com.email.Sys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
	
    List<Email> findByReceiverOrderBySentAtDesc(User receiver);
    
    List<Email> findByReceiverAndSenderOrderBySentAtDesc(User receiver, User sender);
    
    List<Email> findBySenderOrderBySentAtDesc(User sender);
    
    List<Email> findBySenderAndReceiverOrderBySentAtDesc(User sender, User receiver);
    
    @Query("SELECT e FROM Email e WHERE " +
           "(e.sender = :user AND e.receiver = :contact) OR " +
           "(e.sender = :contact AND e.receiver = :user) " +
           "ORDER BY e.sentAt DESC")
    List<Email> findConversationWithContact(@Param("user") User user, @Param("contact") User contact);
}