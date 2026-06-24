package com.email.Sys.email.controller;

import com.email.Sys.email.dto.EmailResponseDTO;
import com.email.Sys.email.dto.SendEmailRequestDTO;
import com.email.Sys.email.service.EmailService;
import com.email.Sys.config.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    
    private Long getUserIdFromAuthHeader(String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        return emailService.findUserIdByEmail(email); 
    }

    
    @PostMapping("/send")
    public ResponseEntity<EmailResponseDTO> sendEmail(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody SendEmailRequestDTO dto) {
        
        Long userId = getUserIdFromAuthHeader(authHeader);
        EmailResponseDTO response = emailService.sendEmail(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    
    @GetMapping("/feed")
    public ResponseEntity<List<EmailResponseDTO>> getFeed(
            @RequestHeader("Authorization") String authHeader) {
        
        Long userId = getUserIdFromAuthHeader(authHeader);
        List<EmailResponseDTO> emails = emailService.getFeed(userId);
        return ResponseEntity.ok(emails);
    }

   
    @GetMapping("/feed/contact/{contactId}")
    public ResponseEntity<List<EmailResponseDTO>> getReceivedFromContact(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long contactId) {
        
        Long userId = getUserIdFromAuthHeader(authHeader);
        List<EmailResponseDTO> emails = emailService.getReceivedFromContact(userId, contactId);
        return ResponseEntity.ok(emails);
    }

 
    @GetMapping("/sent")
    public ResponseEntity<List<EmailResponseDTO>> getSentEmails(
            @RequestHeader("Authorization") String authHeader) {
        
        Long userId = getUserIdFromAuthHeader(authHeader);
        List<EmailResponseDTO> emails = emailService.getSentEmails(userId);
        return ResponseEntity.ok(emails);
    }

   
    @GetMapping("/sent/contact/{contactId}")
    public ResponseEntity<List<EmailResponseDTO>> getSentToContact(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long contactId) {
        
        Long userId = getUserIdFromAuthHeader(authHeader);
        List<EmailResponseDTO> emails = emailService.getSentToContact(userId, contactId);
        return ResponseEntity.ok(emails);
    }

    
    @GetMapping("/conversation/{contactId}")
    public ResponseEntity<List<EmailResponseDTO>> getConversationWithContact(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long contactId) {
        
        Long userId = getUserIdFromAuthHeader(authHeader);
        List<EmailResponseDTO> emails = emailService.getConversationWithContact(userId, contactId);
        return ResponseEntity.ok(emails);
    }

    
    @GetMapping("/{emailId}")
    public ResponseEntity<EmailResponseDTO> getEmailById(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long emailId) {
        
        Long userId = getUserIdFromAuthHeader(authHeader);
        EmailResponseDTO email = emailService.getEmailById(userId, emailId);
        return ResponseEntity.ok(email);
    }
}
