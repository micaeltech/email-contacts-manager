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
	
	// 1. Adicionar email de contato
	public ContactResponseDTO addContact(Long userId, AddContactRequestDTO dto) {
		
		User user = findUserById(userId);
		User contactUser = findUserByEmail(dto.getContactEmail());
		
		if (userId.equals(contactUser.getId())) {
			throw new RuntimeException("Não é possível adicionar a si mesmo.");
		}
		
		if (contactRepository.existsByUserAndContactUser(user, contactUser)) {
			throw new RuntimeException("Este Contato já existe.");
		}
		
		Contact contact = new Contact(user, contactUser, dto.getNickname());
		
		Contact savedContact = contactRepository.save(contact);
		
		return toResponseDTO(savedContact);
		
	}
	
	// 2. Remover email de contato
	public void removeContact(Long userId, Long contactUserId) {
		
		User user = findUserById(userId);
		User contactUser = findUserById(contactUserId);
		
		Contact contact = contactRepository.findByUserAndContactUser(user, contactUser)
				.orElseThrow(() -> new RuntimeException("Contato não encontrado."));
		
		contactRepository.delete(contact);
	}
	
	// 3. Listar emails de contato
	public List<ContactResponseDTO> listContacts(Long userId) {
		
		User user = findUserById(userId);
		List<Contact> contacts = contactRepository.findByUser(user);
		
		return contacts.stream()
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}
	
	// 4. Buscar email de contato pelo nickname
	public List<ContactResponseDTO> searchByNickname(Long userId, String searchTerm) {
		
		User user = findUserById(userId);
		List<Contact> contacts = contactRepository
				.findByUserAndNicknameContainingIgnoreCase(user, searchTerm);
		
		return contacts.stream()
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}
	
	// 5. buscar email de contato pelo email
	private User findUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Usuário com email "+ email + " não encontrado."));
	}
	
	private User findUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
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
