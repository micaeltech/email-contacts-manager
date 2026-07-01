package com.email.Sys.controller;

import com.email.Sys.dto.RegisterRequestDTO;
import com.email.Sys.dto.LoginRequestDTO;
import com.email.Sys.dto.AuthResponseDTO;
import com.email.Sys.auth.dto.ForgotPasswordRequestDTO;
import com.email.Sys.auth.dto.VerifyCodeRequestDTO;
import com.email.Sys.auth.dto.ResetPasswordRequestDTO;
import com.email.Sys.service.AuthService;
import com.email.Sys.service.PasswordResetService;
import com.email.Sys.config.TokenBlackListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8081")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private TokenBlackListService tokenBlacklistService;

  @Autowired
  private PasswordResetService passwordResetService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO dto) {
		
		AuthResponseDTO response = authService.register(dto);
		
		if (response.isSuccess()) { 
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
		
		AuthResponseDTO response = authService.login(dto);
		
		if (response.isSuccess()) {
			return ResponseEntity.ok(response);
			
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}
  
  @PostMapping("/forgot-password")
  public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO dto) {
    passwordResetService.forgotPassword(dto);
    return ResponseEntity.ok().build();
  }
  
  @PostMapping("/verify-code")
  public ResponseEntity<Void> verifyCode(@Valid @RequestBody VerifyCodeRequestDTO dto) {
    passwordResetService.verifyCode(dto);
    return ResponseEntity.ok().build();
  }
  
  @PostMapping("/reset-password")
  public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO dto) {
    passwordResetService.resetPassword(dto);
    return ResponseEntity.ok().build();
  }
	
	@PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        
        String token = authHeader.substring(7);
        
        tokenBlacklistService.invalidateToken(token);
        
        return ResponseEntity.ok().build();
    }
}
