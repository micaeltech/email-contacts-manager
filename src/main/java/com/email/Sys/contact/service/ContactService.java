package com.email.Sys.contact.service;

import com.email.Sys.contact.dto.AddContactRequestDTO;
import com.email.Sys.contact.dto.ContactResponseDTO;
import com.email.Sys.contact.model.Contact;
import com.email.Sys.contact.repository.ContactRepository;
import com.email.Sys.model.User;
import com.email.Sys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public ContactResponseDTO addContact(Long userId, AddContactRequestDTO dto) {
		
		User user = findUserById(userId);
		User contactUser = findUserByEmail(dto.getContactEmail());
		
		if (userId.equals(contactUser.getId())) {
			throw new RuntimeException("You cannot add yourself.");
		}
		
		if (contactRepository.existsByUserAndContactUser(user, contactUser)) {
			throw new RuntimeException("This contact already exists.");
		}
		
		Contact contact = new Contact(user, contactUser, dto.getNickname());
		
		Contact savedContact = contactRepository.save(contact);
		
		return toResponseDTO(savedContact);
		
	}
	
	public void removeContact(Long userId, Long contactUserId) {
		
		User user = findUserById(userId);
		User contactUser = findUserById(contactUserId);
		
		Contact contact = contactRepository.findByUserAndContactUser(user, contactUser)
				.orElseThrow(() -> new RuntimeException("Contact not found."));
		
		contactRepository.delete(contact);
	}
	
	public List<ContactResponseDTO> listContacts(Long userId) {
		
		User user = findUserById(userId);
		List<Contact> contacts = contactRepository.findByUser(user);
		
		return contacts.stream()
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}
	
	public List<ContactResponseDTO> searchByNickname(Long userId, String searchTerm) {
		
		User user = findUserById(userId);
		List<Contact> contacts = contactRepository
				.findByUserAndNicknameContainingIgnoreCase(user, searchTerm);
		
		return contacts.stream()
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}
	
	private User findUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User with email "+ email + " not found."));
	}
	
	private User findUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found."));
	}
	
	private ContactResponseDTO toResponseDTO(Contact contact) {
		User contactUser = contact.getContactUser();
		return new ContactResponseDTO(
				contactUser.getId(),
				contactUser.getName(),
				contactUser.getEmail(),
				contact.getNickname(),
				contactUser.getStatus().name()
				);
	}
}
