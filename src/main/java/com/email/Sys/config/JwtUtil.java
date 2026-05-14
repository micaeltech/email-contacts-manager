package com.email.Sys.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
	
	private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	private static final long EXPIRATION_TIME = 86400000;
	
	// Gerar token
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SECRET_KEY)
				.compact();
				
	}
	
	public String extractEmail(String token) {
		return getClaims(token).getSubject();
	}
	
	public boolean isTokenValid(String token, String email) {
		return (email.equals(extractEmail(token)) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}
	
	private Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
