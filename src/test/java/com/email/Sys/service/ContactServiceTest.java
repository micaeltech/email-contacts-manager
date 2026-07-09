package com.email.Sys.service;

import com.email.Sys.contact.dto.AddContactRequestDTO;
import com.email.Sys.contact.dto.ContactResponseDTO;
import com.email.Sys.contact.model.Contact;
import com.email.Sys.contact.repository.ContactRepository;
import com.email.Sys.contact.service.ContactService;
import com.email.Sys.model.User;
import com.email.Sys.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ContactService.
 * Tests contact operations: add, list, search, remove.
 */
@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ContactService contactService;

    private User user;
    private User contactUser;
    private Contact contact;

    @BeforeEach
    void setUp() {
        user = new User("João Silva", null, "joao@email.com", "pass", null);
        user.setId(1L);

        contactUser = new User("Maria Santos", null, "maria@email.com", "pass", null);
        contactUser.setId(2L);

        contact = new Contact(user, contactUser, "Mari");
        contact.setId(1L);
    }

    /* Tests for addContact() */
    @Test
    void shouldAddContactSuccessfully() {
        AddContactRequestDTO dto = new AddContactRequestDTO();
        dto.setContactEmail("maria@email.com");
        dto.setNickname("Mari");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("maria@email.com")).thenReturn(Optional.of(contactUser));
        when(contactRepository.existsByUserAndContactUser(user, contactUser)).thenReturn(false);
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        ContactResponseDTO response = contactService.addContact(1L, dto);

        assertNotNull(response);
        assertEquals("Maria Santos", response.getContactName());
        assertEquals("Mari", response.getNickname());
        verify(contactRepository).save(any(Contact.class));
    }

    @Test
    void shouldFailAddContactWhenAlreadyExists() {
        AddContactRequestDTO dto = new AddContactRequestDTO();
        dto.setContactEmail("maria@email.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("maria@email.com")).thenReturn(Optional.of(contactUser));
        when(contactRepository.existsByUserAndContactUser(user, contactUser)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> contactService.addContact(1L, dto));
        assertEquals("Este Contato já existe.", exception.getMessage());
        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void shouldFailAddContactWhenAddingSelf() {
        AddContactRequestDTO dto = new AddContactRequestDTO();
        dto.setContactEmail("joao@email.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> contactService.addContact(1L, dto));
        assertEquals("Não é possível adicionar a si mesmo.", exception.getMessage());
    }

    /* Tests for listContacts() */
    @Test
    void shouldListContactsSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(contactRepository.findByUser(user)).thenReturn(List.of(contact));

        List<ContactResponseDTO> contacts = contactService.listContacts(1L);

        assertEquals(1, contacts.size());
        assertEquals("Maria Santos", contacts.get(0).getContactName());
        verify(contactRepository).findByUser(user);
    }

    /* Tests for searchByNickname() */
    @Test
    void shouldSearchContactsByNickname() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(contactRepository.findByUserAndNicknameContainingIgnoreCase(user, "Mari"))
                .thenReturn(List.of(contact));

        List<ContactResponseDTO> results = contactService.searchByNickname(1L, "Mari");

        assertEquals(1, results.size());
        assertEquals("Mari", results.get(0).getNickname());
        verify(contactRepository).findByUserAndNicknameContainingIgnoreCase(user, "Mari");
    }

    @Test
    void shouldReturnEmptyWhenNicknameNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(contactRepository.findByUserAndNicknameContainingIgnoreCase(user, "unknown"))
                .thenReturn(List.of());

        List<ContactResponseDTO> results = contactService.searchByNickname(1L, "unknown");

        assertTrue(results.isEmpty());
        verify(contactRepository).findByUserAndNicknameContainingIgnoreCase(user, "unknown");
    }

    /* Tests for removeContact() */
    @Test
    void shouldRemoveContactSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(contactUser));
        when(contactRepository.findByUserAndContactUser(user, contactUser))
                .thenReturn(Optional.of(contact));

        contactService.removeContact(1L, 2L);

        verify(contactRepository).delete(contact);
    }

    @Test
    void shouldFailRemoveContactWhenNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(contactUser));
        when(contactRepository.findByUserAndContactUser(user, contactUser))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> contactService.removeContact(1L, 2L));
        assertEquals("Contato não encontrado.", exception.getMessage());
        verify(contactRepository, never()).delete(any(Contact.class));
    }
}
