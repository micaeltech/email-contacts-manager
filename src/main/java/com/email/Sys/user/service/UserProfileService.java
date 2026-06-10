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
	
	// 1. Atualizar email principal
	public User updateEmail(Long userId, UpdateEmailDTO dto) {
		
		User user = findUserById(userId);
		
		if (repository.existsByEmail(dto.getNewEmail())) {
			throw new RuntimeException("Email já está em uso.");
		}
		
		user.setEmail(dto.getNewEmail());
		return repository.save(user);
	}
	
	
	// 2. Adicionar email backup
	public User addBackupEmail(Long userId, BackupEmailDTO dto) {
		
		User user = findUserById(userId);
		
		if (repository.existsByEmail(dto.getBackupEmail())) {
			throw new RuntimeException("Esse email já existe.");
		}
		
		user.setSecondaryEmail(dto.getBackupEmail());
		return repository.save(user);
	}
	
	// 3. Remover email backup (email secundário)
	public User removeBackupEmail(Long userId) {
		User user = findUserById(userId);
		user.setSecondaryEmail(null);
		
		return repository.save(user);
	}
	
	
	// 4. Atualizar senha
	public void updatePassword(Long userId, UpdatePasswordDTO dto) {
		
		User user = findUserById(userId);
		
		// Verificar se senha atual está correta
		if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
			throw new RuntimeException("Senha incorreta.");
		}
		
		// Verificar se nova senha e confirmação são iguais
		if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
			throw new RuntimeException("Nova senha e confirmação não coincidem.");
		}
		
		user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		repository.save(user);
	}
	
	
	// 5. Atualizar foto
	public User updatePhoto(Long userId, UpdatePhotoDTO dto) {
		
		User user = findUserById(userId);
		
		String url = dto.getPhotoUrl().toLowerCase();
        if (!url.matches(".*\\.(jpg|jpeg|png|gif|webp)$") && 
            !url.startsWith("data:image/")) {
            throw new RuntimeException("Formato de imagem não suportado");
        }
        
        user.setPhotoUrl(dto.getPhotoUrl());
        
		return repository.save(user);
	}
	
	
	// 6. Remover foto
	public User removePhoto(Long userId) {	
		User user = findUserById(userId);
		user.setPhotoUrl(null);
		
		return repository.save(user);	
	}
	
	// . Atualizar status (on/off)
	public User updateStatus(Long userId, UpdateStatusDTO dto) {
		
		User user = findUserById(userId);
		
		user.setStatus(User.Status.valueOf(dto.getStatus()));
		return repository.save(user);
	}
	
	// Método que busca usuário por ID
	private User findUserById(Long userId) {
		return repository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
	}
}
