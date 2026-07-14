package com.email.Sys.service;

import com.email.Sys.dto.RegisterRequestDTO;
import com.email.Sys.dto.LoginRequestDTO;
import com.email.Sys.config.JwtUtil;
import com.email.Sys.dto.AuthResponseDTO;
import com.email.Sys.model.User;
import com.email.Sys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.ArrayList;

@Service
public class AuthService implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AuthResponseDTO register(RegisterRequestDTO dto) {	
		if (repository.existsByEmail(dto.getEmail())) {
			return new AuthResponseDTO("Email already exists.", false);
		}
		
		String encrypted_passw = passwordEncoder.encode(dto.getPassword());
		
		User user = new User(
				dto.getName(),
				dto.getBirthDate(),
				dto.getEmail(),
				encrypted_passw,
        dto.getPhone()
				);
		
		repository.save(user);
		
		return new AuthResponseDTO(
				user.getEmail(),
				user.getName(),
				jwtUtil.generateToken(user.getEmail()) 
				);
	}
	
	public AuthResponseDTO login(LoginRequestDTO dto) {
		User user = repository.findByEmail(dto.getEmail()).orElse(null);
		
		if (user == null) {
			return new AuthResponseDTO("Email not found.", false);
		}
		
		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			return new AuthResponseDTO("Incorrect password.", false);
		}
		
		user.setStatus(User.Status.ONLINE);
		repository.save(user);
		
		return new AuthResponseDTO(
				user.getEmail(),
				user.getName(),
				jwtUtil.generateToken(user.getEmail())
				);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    User user = repository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
	    
	    return org.springframework.security.core.userdetails.User
	            .withUsername(user.getEmail())
	            .password(user.getPassword())
	            .authorities(new ArrayList<>())
	            .build();
	}
}
