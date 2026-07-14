package com.email.Sys.email.service;

import com.email.Sys.email.dto.EmailResponseDTO;
import com.email.Sys.email.dto.SendEmailRequestDTO;
import com.email.Sys.email.model.Email;
import com.email.Sys.email.repository.EmailRepository;
import com.email.Sys.model.User;
import com.email.Sys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UserRepository userRepository;

    
    public EmailResponseDTO sendEmail(Long senderId, SendEmailRequestDTO dto) {
        User sender = findUserById(senderId);
        User receiver = findUserByEmail(dto.getTo());

        if (sender.getId().equals(receiver.getId())) {
            throw new RuntimeException("You cannot send an email to yourself.");
        }

        Email email = new Email(sender, receiver, dto.getSubject(), dto.getContent());
        Email savedEmail = emailRepository.save(email);

        return toResponseDTO(savedEmail);
    }

   
    public List<EmailResponseDTO> getFeed(Long userId) {
        User user = findUserById(userId);
        List<Email> emails = emailRepository.findByReceiverOrderBySentAtDesc(user);
        return emails.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

   
    public List<EmailResponseDTO> getReceivedFromContact(Long userId, Long contactId) {
        User user = findUserById(userId);
        User contact = findUserById(contactId);
        List<Email> emails = emailRepository.findByReceiverAndSenderOrderBySentAtDesc(user, contact);
        return emails.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    
    public List<EmailResponseDTO> getSentEmails(Long userId) {
        User user = findUserById(userId);
        List<Email> emails = emailRepository.findBySenderOrderBySentAtDesc(user);
        return emails.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    
    public List<EmailResponseDTO> getSentToContact(Long userId, Long contactId) {
        User user = findUserById(userId);
        User contact = findUserById(contactId);
        List<Email> emails = emailRepository.findBySenderAndReceiverOrderBySentAtDesc(user, contact);
        return emails.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    
    public List<EmailResponseDTO> getConversationWithContact(Long userId, Long contactId) {
        User user = findUserById(userId);
        User contact = findUserById(contactId);
        List<Email> emails = emailRepository.findConversationWithContact(user, contact);
        return emails.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    
    public EmailResponseDTO getEmailById(Long userId, Long emailId) {
        User user = findUserById(userId);
        Email email = emailRepository.findById(emailId)
                .orElseThrow(() -> new RuntimeException("Email not found."));

        
        if (!email.getSender().getId().equals(userId) && !email.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("Access denied: this email does not belong to the user.");
        }

       
        if (email.getReceiver().getId().equals(userId) && !email.isRead()) {
            email.setRead(true);
            emailRepository.save(email);
        }

        return toResponseDTO(email);
    }
    
    public Long findUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));
        return user.getId();
    }
  
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found."));
    }

    private EmailResponseDTO toResponseDTO(Email email) {
        return new EmailResponseDTO(
                email.getId(),
                email.getSender().getEmail(),
                email.getReceiver().getEmail(),
                email.getSender().getName(),
                email.getReceiver().getName(),
                email.getSubject(),
                email.getContent(),
                email.getSentAt(),
                email.isRead()
        );
    }
}
