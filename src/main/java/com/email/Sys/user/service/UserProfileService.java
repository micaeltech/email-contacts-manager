package com.email.Sys.user.service;

import com.email.Sys.model.User;
import com.email.Sys.repository.UserRepository;
import com.email.Sys.user.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserProfileService {
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User updateEmail(Long userId, UpdateEmailDTO dto) {
		User user = findUserById(userId);
		
		if (repository.existsByEmail(dto.getNewEmail())) {
			throw new RuntimeException("Email is already in use.");
		}
		
		user.setEmail(dto.getNewEmail());
		return repository.save(user);
	}
	
	
	public User addBackupEmail(Long userId, BackupEmailDTO dto) {
		User user = findUserById(userId);
		
		if (repository.existsByEmail(dto.getBackupEmail())) {
			throw new RuntimeException("This email already exists.");
		}
		
		user.setSecondaryEmail(dto.getBackupEmail());
		return repository.save(user);
	}
	
	public User removeBackupEmail(Long userId) {
		User user = findUserById(userId);
		user.setSecondaryEmail(null);
		
		return repository.save(user);
	}
	
	
	public void updatePassword(Long userId, UpdatePasswordDTO dto) {
		User user = findUserById(userId);
		
		if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
			throw new RuntimeException("Incorrect password.");
		}
		
		if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
			throw new RuntimeException("New password and confirmation do not match.");
		}
		
		user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		repository.save(user);
	}
	
	public User updatePhoto(Long userId, UpdatePhotoDTO dto) {
		User user = findUserById(userId);
		
		String url = dto.getPhotoUrl().toLowerCase();
        if (!url.matches(".*\\.(jpg|jpeg|png|gif|webp)$") && 
            !url.startsWith("data:image/")) {
            throw new RuntimeException("Unsupported image format.");
        }
        
        user.setPhotoUrl(dto.getPhotoUrl());
        
		return repository.save(user);
	}
	
	
	public User removePhoto(Long userId) {	
		
    User user = findUserById(userId);
		user.setPhotoUrl(null);
		
		return repository.save(user);	
	}
	
	public User updateStatus(Long userId, UpdateStatusDTO dto) {
		User user = findUserById(userId);
		
		user.setStatus(User.Status.valueOf(dto.getStatus()));
		return repository.save(user);
	}
	
	private User findUserById(Long userId) {
		return repository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found."));
	}
}
