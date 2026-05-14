package com.email.Sys.service;

import com.email.Sys.dto.RegisterRequestDTO;
import com.email.Sys.dto.LoginRequestDTO;
import com.email.Sys.config.JwtUtil;
import com.email.Sys.dto.AuthResponseDTO;
import com.email.Sys.model.User;
import com.email.Sys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	public AuthResponseDTO register(RegisterRequestDTO dto) {
		
		// Verificar se o email já existe
		if (repository.existsByEmail(dto.getEmail())) {
			return new AuthResponseDTO("Email já existe.", false);
		}
		
		// Criptografar a senha
		String encrypted_passw = passwordEncoder.encode(dto.getPassword());
		
		// Criar novo usuário
		User user = new User(
				dto.getName(),
				dto.getBirthDate(),
				dto.getEmail(),
				encrypted_passw
				);
		
		// salvar no banco de dados
		repository.save(user);
		
		// retornar sucesso
		return new AuthResponseDTO(
				user.getEmail(),
				user.getName(),
				jwtUtil.generateToken(user.getEmail()) // token
				);
	}
	
	public AuthResponseDTO login(LoginRequestDTO dto) {
		
		// Buscar usuario por id
		User user = repository.findByEmail(dto.getEmail()).orElse(null);
		
		// Verificar se usuário existe
		if (user == null) {
			return new AuthResponseDTO("Email não encontrado.", false);
		}
		
		// Verificar se a senha está correta
		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			return new AuthResponseDTO("Senha incorreta.", false);
		}
		
		// Atualizar status para online
		user.setStatus(User.Status.ONLINE);
		repository.save(user);
		
		// Retornar sucesso
		return new AuthResponseDTO(
				user.getEmail(),
				user.getName(),
				jwtUtil.generateToken(user.getEmail())
				);
	}
}
