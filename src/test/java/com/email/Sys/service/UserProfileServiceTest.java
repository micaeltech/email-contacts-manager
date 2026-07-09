package com.email.Sys.service;

import com.email.Sys.model.User;
import com.email.Sys.repository.UserRepository;
import com.email.Sys.user.dto.*;
import com.email.Sys.user.service.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserProfileService.
 * Tests profile update operations: email, password, backup email, status.
 */
@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserProfileService userProfileService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
                "João Silva",
                LocalDate.of(1990, 5, 15),
                "joao@email.com",
                "encoded_password",
                "+5511999999999"
        );
        user.setId(1L);
    }

    /* Tests for updateEmail() */
    @Test
    void shouldUpdateEmailSuccessfully() {
        UpdateEmailDTO dto = new UpdateEmailDTO();
        dto.setNewEmail("novo@email.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(dto.getNewEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updated = userProfileService.updateEmail(1L, dto);
        assertEquals("novo@email.com", updated.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void shouldFailUpdateEmailWhenEmailAlreadyExists() {
        UpdateEmailDTO dto = new UpdateEmailDTO();
        dto.setNewEmail("exists@email.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(dto.getNewEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userProfileService.updateEmail(1L, dto));
        assertEquals("Email já está em uso.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    /* Tests for updatePassword() */
    @Test
    void shouldUpdatePasswordSuccessfully() {
        UpdatePasswordDTO dto = new UpdatePasswordDTO();
        dto.setCurrentPassword("Senha123!@#");
        dto.setNewPassword("NovaSenha456!@#");
        dto.setConfirmNewPassword("NovaSenha456!@#");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(dto.getNewPassword())).thenReturn("encoded_new_password");

        userProfileService.updatePassword(1L, dto);
        verify(userRepository).save(user);
        assertEquals("encoded_new_password", user.getPassword());
    }

    @Test
    void shouldFailUpdatePasswordWhenCurrentPasswordIsWrong() {
        UpdatePasswordDTO dto = new UpdatePasswordDTO();
        dto.setCurrentPassword("senha_errada");
        dto.setNewPassword("NovaSenha456!@#");
        dto.setConfirmNewPassword("NovaSenha456!@#");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userProfileService.updatePassword(1L, dto));
        assertEquals("Senha incorreta.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldFailUpdatePasswordWhenPasswordsDoNotMatch() {
      UpdatePasswordDTO dto = new UpdatePasswordDTO();
      dto.setCurrentPassword("Senha123!@#");
      dto.setNewPassword("NovaSenha456!@#");
      dto.setConfirmNewPassword("Diferente!@#");

      when(userRepository.findById(1L)).thenReturn(Optional.of(user));
      when(passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())).thenReturn(true); 

      RuntimeException exception = assertThrows(RuntimeException.class,
          () -> userProfileService.updatePassword(1L, dto));
      assertEquals("Nova senha e confirmação não coincidem.", exception.getMessage());

    }

    /* Tests for backup email */
    @Test
    void shouldAddBackupEmailSuccessfully() {
        BackupEmailDTO dto = new BackupEmailDTO();
        dto.setNewEmail("backup@email.com"); 

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(dto.getBackupEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updated = userProfileService.addBackupEmail(1L, dto);
        assertEquals("backup@email.com", updated.getSecondaryEmail());
        verify(userRepository).save(user);
    }

    @Test
    void shouldRemoveBackupEmailSuccessfully() {
        user.setSecondaryEmail("backup@email.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updated = userProfileService.removeBackupEmail(1L);
        assertNull(updated.getSecondaryEmail());
        verify(userRepository).save(user);
    }

    /* Tests for status update */
    @Test
    void shouldUpdateStatusToOnline() {
        UpdateStatusDTO dto = new UpdateStatusDTO();
        dto.setStatus("ONLINE");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updated = userProfileService.updateStatus(1L, dto);
        assertEquals(User.Status.ONLINE, updated.getStatus());
        verify(userRepository).save(user);
    }

    @Test
    void shouldUpdateStatusToOffline() {
        UpdateStatusDTO dto = new UpdateStatusDTO();
        dto.setStatus("OFFLINE");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updated = userProfileService.updateStatus(1L, dto);
        assertEquals(User.Status.OFFLINE, updated.getStatus());
        verify(userRepository).save(user);
    }
}
