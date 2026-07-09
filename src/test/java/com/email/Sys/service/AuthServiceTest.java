package com.email.Sys.service;

import com.email.Sys.dto.RegisterRequestDTO;
import com.email.Sys.dto.LoginRequestDTO;
import com.email.Sys.dto.AuthResponseDTO;
import com.email.Sys.config.JwtUtil;
import com.email.Sys.model.User;
import com.email.Sys.repository.UserRepository;
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
 * Unit tests for AuthService.
 * This class tests the authentication logic (register and login) in isolation,
 * using mocks for external dependencies (UserRepository, JwtUtil, PasswordEncoder).
 * 
 * It ensures that:
 * 1) Registration works with valid data
 * 2) Registration fails when email is already taken
 * 3) Login works with correct credentials
 * 4) Login fails when email is not found or password is incorrect
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private RegisterRequestDTO registerDTO;
    private LoginRequestDTO loginDTO;
    private User user;

    @BeforeEach
    void setUp() {
        registerDTO = new RegisterRequestDTO();
        registerDTO.setName("João Silva");
        registerDTO.setBirthDate(LocalDate.of(1990, 5, 15));
        registerDTO.setEmail("joao@email.com");
        registerDTO.setPassword("Senha123!@#");
        registerDTO.setPhone("+5511999999999");

        loginDTO = new LoginRequestDTO();
        loginDTO.setEmail("joao@email.com");
        loginDTO.setPassword("Senha123!@#");

        user = new User(
                "João Silva",
                LocalDate.of(1990, 5, 15),
                "joao@email.com",
                "encoded_password",
                "+5511999999999"
        );
    }

    // Register Tests 

    @Test
    void shouldRegisterUserSuccessfully() {
        when(userRepository.existsByEmail(registerDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerDTO.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtUtil.generateToken(registerDTO.getEmail())).thenReturn("jwt_token");

        AuthResponseDTO response = authService.register(registerDTO);

        assertTrue(response.isSuccess());
        assertEquals("joao@email.com", response.getEmail());
        assertEquals("João Silva", response.getName());
        assertNotNull(response.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldFailWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(registerDTO.getEmail())).thenReturn(true);

        AuthResponseDTO response = authService.register(registerDTO);

        assertFalse(response.isSuccess());
        assertEquals("Email já existe.", response.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    // Login Tests

    @Test
    void shouldLoginSuccessfully() {
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(loginDTO.getEmail())).thenReturn("jwt_token");

        AuthResponseDTO response = authService.login(loginDTO);

        assertTrue(response.isSuccess());
        assertEquals("joao@email.com", response.getEmail());
        assertEquals("João Silva", response.getName());
        assertNotNull(response.getToken());
        verify(userRepository).save(user);
    }

    @Test
    void shouldFailWhenEmailNotFound() {
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.empty());

        AuthResponseDTO response = authService.login(loginDTO);

        assertFalse(response.isSuccess());
        assertEquals("Email não encontrado.", response.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldFailWhenPasswordIsIncorrect() {
        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())).thenReturn(false);

        AuthResponseDTO response = authService.login(loginDTO);

        assertFalse(response.isSuccess());
        assertEquals("Senha incorreta.", response.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}
