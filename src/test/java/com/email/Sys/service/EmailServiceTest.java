package com.email.Sys.service;

import com.email.Sys.email.dto.EmailResponseDTO;
import com.email.Sys.email.dto.SendEmailRequestDTO;
import com.email.Sys.email.model.Email;
import com.email.Sys.email.repository.EmailRepository;
import com.email.Sys.email.service.EmailService;
import com.email.Sys.model.User;
import com.email.Sys.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for EmailService.
 * Tests email operations: send, inbox, sent, conversation.
 */
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmailService emailService;

    private User sender;
    private User receiver;
    private Email email;

    @BeforeEach
    void setUp() {
        sender = new User("João Silva", null, "joao@email.com", "pass", null);
        sender.setId(1L);

        receiver = new User("Maria Santos", null, "maria@email.com", "pass", null);
        receiver.setId(2L);

        email = new Email(sender, receiver, "Olá!", "Como você está?");
        email.setId(1L);
        email.setSentAt(LocalDateTime.now());
    }

    /* Tests for sendEmail() */
    @Test
    void shouldSendEmailSuccessfully() {
        SendEmailRequestDTO dto = new SendEmailRequestDTO();
        dto.setTo("maria@email.com");
        dto.setSubject("Olá!");
        dto.setContent("Como você está?");

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findByEmail("maria@email.com")).thenReturn(Optional.of(receiver));
        when(emailRepository.save(any(Email.class))).thenReturn(email);

        EmailResponseDTO response = emailService.sendEmail(1L, dto);

        assertNotNull(response);
        assertEquals("joao@email.com", response.getFrom());
        assertEquals("maria@email.com", response.getTo());
        assertEquals("Olá!", response.getSubject());
        verify(emailRepository).save(any(Email.class));
    }

    @Test
    void shouldFailSendEmailToSelf() {
        SendEmailRequestDTO dto = new SendEmailRequestDTO();
        dto.setTo("joao@email.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(sender));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> emailService.sendEmail(1L, dto));
        assertEquals("Não é possível enviar email para si mesmo", exception.getMessage());
        verify(emailRepository, never()).save(any(Email.class));
    }

    @Test
    void shouldFailSendEmailWhenReceiverNotFound() {
        SendEmailRequestDTO dto = new SendEmailRequestDTO();
        dto.setTo("unknown@email.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findByEmail("unknown@email.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> emailService.sendEmail(1L, dto));
        assertEquals("Usuário com email unknown@email.com não encontrado", exception.getMessage()); // ← CORRIGIDO (removido o ponto final)
    }

    /* Tests for getFeed() (inbox) */
    @Test
    void shouldGetFeedSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(emailRepository.findByReceiverOrderBySentAtDesc(sender)).thenReturn(List.of(email));

        List<EmailResponseDTO> feed = emailService.getFeed(1L);

        assertEquals(1, feed.size());
        assertEquals("Olá!", feed.get(0).getSubject());
        verify(emailRepository).findByReceiverOrderBySentAtDesc(sender);
    }

    @Test
    void shouldReturnEmptyFeedWhenNoEmails() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(emailRepository.findByReceiverOrderBySentAtDesc(sender)).thenReturn(List.of());

        List<EmailResponseDTO> feed = emailService.getFeed(1L);

        assertTrue(feed.isEmpty());
        verify(emailRepository).findByReceiverOrderBySentAtDesc(sender);
    }

    /* Tests for getSentEmails() */
    @Test
    void shouldGetSentEmailsSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(emailRepository.findBySenderOrderBySentAtDesc(sender)).thenReturn(List.of(email));

        List<EmailResponseDTO> sent = emailService.getSentEmails(1L);

        assertEquals(1, sent.size());
        assertEquals("joao@email.com", sent.get(0).getFrom());
        verify(emailRepository).findBySenderOrderBySentAtDesc(sender);
    }

    /* Tests for getReceivedFromContact() */
    @Test
    void shouldGetReceivedFromContactSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));
        when(emailRepository.findByReceiverAndSenderOrderBySentAtDesc(sender, receiver))
                .thenReturn(List.of(email));

        List<EmailResponseDTO> results = emailService.getReceivedFromContact(1L, 2L);

        assertEquals(1, results.size());
        verify(emailRepository).findByReceiverAndSenderOrderBySentAtDesc(sender, receiver);
    }

    /* Tests for getSentToContact() */
    @Test
    void shouldGetSentToContactSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));
        when(emailRepository.findBySenderAndReceiverOrderBySentAtDesc(sender, receiver))
                .thenReturn(List.of(email));

        List<EmailResponseDTO> results = emailService.getSentToContact(1L, 2L);

        assertEquals(1, results.size());
        verify(emailRepository).findBySenderAndReceiverOrderBySentAtDesc(sender, receiver);
    }

    /* Tests for getConversationWithContact() */
    @Test
    void shouldGetConversationWithContactSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));
        when(emailRepository.findConversationWithContact(sender, receiver))
                .thenReturn(List.of(email));

        List<EmailResponseDTO> conversation = emailService.getConversationWithContact(1L, 2L);

        assertEquals(1, conversation.size());
        verify(emailRepository).findConversationWithContact(sender, receiver);
    }

    /* Tests for getEmailById() */
    @Test
    void shouldGetEmailByIdSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(emailRepository.findById(1L)).thenReturn(Optional.of(email));

        EmailResponseDTO response = emailService.getEmailById(1L, 1L);

        assertNotNull(response);
        assertEquals("Olá!", response.getSubject());
        verify(emailRepository).findById(1L);
    }

    @Test
    void shouldMarkEmailAsReadWhenReceiverAccesses() {
        email.setRead(false);
        when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));
        when(emailRepository.findById(1L)).thenReturn(Optional.of(email));
        when(emailRepository.save(any(Email.class))).thenReturn(email);

        EmailResponseDTO response = emailService.getEmailById(2L, 1L);

        assertTrue(response.isRead());
        verify(emailRepository).save(email);
    }

    @Test
    void shouldFailGetEmailWhenUserNotPartOfConversation() {
        User otherUser = new User("Pedro", null, "pedro@email.com", "pass", null);
        otherUser.setId(3L);

        when(userRepository.findById(3L)).thenReturn(Optional.of(otherUser));
        when(emailRepository.findById(1L)).thenReturn(Optional.of(email));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> emailService.getEmailById(3L, 1L));
        assertEquals("Acesso negado: este email não pertence ao usuário", exception.getMessage());
    }
}
