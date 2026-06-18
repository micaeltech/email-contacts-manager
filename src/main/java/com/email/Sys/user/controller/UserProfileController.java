package com.email.Sys.user.controller;

import com.email.Sys.config.JwtUtil;
import com.email.Sys.model.User;
import com.email.Sys.user.dto.*;
import com.email.Sys.user.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.email.Sys.user.dto.UserResponseDTO;
@RestController
@RequestMapping("/User")
public class UserProfileController {
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	// 1. Atualizar email
	@PutMapping("/email")
	public ResponseEntity<UserResponseDTO> updateEmail(
			@RequestHeader("Authorization") String authHeader, 
			@Valid @RequestBody UpdateEmailDTO dto) {
		
		Long userId = jwtUtil.extractUserIdFromToken(authHeader);
		User updatedUser = userProfileService.updateEmail(userId, dto);
		
		String newToken = jwtUtil.generateToken(updatedUser.getEmail());
		
		return ResponseEntity.ok(new UserResponseDTO(updatedUser, newToken));
	}
	
	// 2. Adicionar email backup
		@PostMapping("/backup-email")
		public ResponseEntity<User> addBackupEmail(
				@RequestHeader("Authorization") String authHeader,
				@Valid @RequestBody BackupEmailDTO dto) {
			
			Long userId = jwtUtil.extractUserIdFromToken(authHeader);
			User updatedUser = userProfileService.addBackupEmail(userId, dto);
			
			return ResponseEntity.ok(updatedUser);
	}
		
	// 3. Deletar email de backup
		@DeleteMapping("/backup-email")
		public ResponseEntity<User> removeBackupEmail(
				@RequestHeader("Authorization") String authHeader) {
			
			Long userId = jwtUtil.extractUserIdFromToken(authHeader);
			User updatedUser = userProfileService.removeBackupEmail(userId);
			
			return ResponseEntity.ok(updatedUser);
	}
	
		
	// 4. Redefinir senha
	@PutMapping("/password")
	public ResponseEntity<Void> updatePassword(
			@RequestHeader("Authorization") String authHeader,
					@Valid @RequestBody UpdatePasswordDTO dto) {
		
		Long userId = jwtUtil.extractUserIdFromToken(authHeader);
		userProfileService.updatePassword(userId, dto);
		
		return ResponseEntity.ok().build();

	}
	
	// 5. Atualizar foto
	@PutMapping("/photo")
	public ResponseEntity<User> updatePhoto(
			@RequestHeader("Authorization") String authHeader,
			@Valid @RequestBody UpdatePhotoDTO dto) {
		
		Long userId = jwtUtil.extractUserIdFromToken(authHeader);
		User updatedUser = userProfileService.updatePhoto(userId, dto);
		
		return ResponseEntity.ok(updatedUser);
	}
	
	// 6. Deletar foto
	@DeleteMapping("/photo")
	public ResponseEntity<User> removePhoto(
			@RequestHeader("Authorization") String authHeader) {
		
		Long userId = jwtUtil.extractUserIdFromToken(authHeader);
		User updatedUser = userProfileService.removePhoto(userId);
		
		return ResponseEntity.ok(updatedUser);
	}
	
	// 7. Atualizar status
	@PutMapping("/status")
	public ResponseEntity<User> updateStatus(
			@RequestHeader("Authorization") String authHeader,
			@Valid @RequestBody UpdateStatusDTO dto) {
		
		Long userId = jwtUtil.extractUserIdFromToken(authHeader);
		User updatedUser = userProfileService.updateStatus(userId, dto);
		
		return ResponseEntity.ok(updatedUser);
	}
}
