package com.email.Sys.contact.controller;

import com.email.Sys.config.JwtUtil;
import com.email.Sys.contact.dto.AddContactRequestDTO;
import com.email.Sys.contact.dto.ContactResponseDTO;
import com.email.Sys.contact.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/contacts")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping
	public ResponseEntity<ContactResponseDTO> addContact(
			@RequestHeader("Authorization") String authHeader,
			@Valid @RequestBody AddContactRequestDTO dto) {
		
		Long userId = jwtUtil.extractUserIdFromToken(authHeader);
		
		ContactResponseDTO response = contactService.addContact(userId, dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
    @DeleteMapping("/{contactUserId}")
    public ResponseEntity<Void> removeContact(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long contactUserId) {
    	
    	Long userId = jwtUtil.extractUserIdFromToken(authHeader);
        
        contactService.removeContact(userId, contactUserId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<List<ContactResponseDTO>> listContacts(
    		@RequestHeader("Authorization") String authHeader) {
    	
    	Long userId = jwtUtil.extractUserIdFromToken(authHeader);
    	
    	List<ContactResponseDTO> contacts = contactService.listContacts(userId);
    	
    	return ResponseEntity.ok(contacts);
    }
    
    
    @GetMapping("/search")
    public ResponseEntity<List<ContactResponseDTO>> searchContacts(
    		@RequestHeader("Authorization") String authHeader,
    		@RequestParam("q") String searchTerm) {
    	
    	Long userId = jwtUtil.extractUserIdFromToken(authHeader);
    	
    	List<ContactResponseDTO> contacts = contactService.searchByNickname(userId, searchTerm);
    	
    	return ResponseEntity.ok(contacts);
    }
}
