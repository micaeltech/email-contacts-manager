package com.email.Sys.controller;

import com.email.Sys.dto.RegisterRequestDTO;
import com.email.Sys.dto.LoginRequestDTO;
import com.email.Sys.dto.AuthResponseDTO;
import com.email.Sys.service.AuthService;
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
}
