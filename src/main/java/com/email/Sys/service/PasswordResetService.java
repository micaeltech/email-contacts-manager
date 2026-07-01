package com.email.Sys.service;

import com.email.Sys.auth.dto.ForgotPasswordRequestDTO;
import com.email.Sys.auth.dto.ResetPasswordRequestDTO;
import com.email.Sys.auth.dto.VerifyCodeRequestDTO;
import com.email.Sys.model.PasswordResetToken;
import com.email.Sys.model.User;
import com.email.Sys.repository.PasswordResetTokenRepository;
import com.email.Sys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int CODE_EXPIRATION_MINUTES = 15;

    public void forgotPassword(ForgotPasswordRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        String method = dto.getMethod();
        String destination = null;

        if ("email".equals(method) && user.getSecondaryEmail() == null) {
            throw new RuntimeException("No backup email registered. Please use SMS.");
        }

        if ("sms".equals(method) && user.getPhone() == null) {
            throw new RuntimeException("No phone number registered. Please use Email.");
        }

        if ("email".equals(method)) {
            destination = "Email to " + user.getSecondaryEmail();
        } else {
            destination = "SMS to " + user.getPhone();
        }

        String code = String.format("%06d", new Random().nextInt(999999));

        PasswordResetToken token = new PasswordResetToken(
                user,
                code,
                method,
                LocalDateTime.now().plusMinutes(CODE_EXPIRATION_MINUTES)
        );
        tokenRepository.save(token);

        System.out.println("--------------------------------------------------");
        System.out.println("RECOVERY CODE");
        System.out.println("Method: " + method.toUpperCase());
        System.out.println("Destination: " + destination);
        System.out.println("Code: " + code);
        System.out.println("Expires in: " + CODE_EXPIRATION_MINUTES + " minutes");
        System.out.println("--------------------------------------------------");
    }

    public void verifyCode(VerifyCodeRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        PasswordResetToken token = tokenRepository
                .findByUserAndCodeAndIsUsedFalse(user, dto.getCode())
                .orElseThrow(() -> new RuntimeException("Invalid or expired code"));

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Code has expired");
        }
    }

    @Transactional
    public void resetPassword(ResetPasswordRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        PasswordResetToken token = tokenRepository
                .findByUserAndCodeAndIsUsedFalse(user, dto.getCode())
                .orElseThrow(() -> new RuntimeException("Invalid or expired code"));

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Code has expired");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);

        tokenRepository.deleteByUser(user);
    }
}
