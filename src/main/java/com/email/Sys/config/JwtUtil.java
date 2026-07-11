package com.email.Sys.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import com.email.Sys.model.User;
import com.email.Sys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	private static final long EXPIRATION_TIME = 86400000;
	
	@Autowired
	private UserRepository userRepository;
	
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getSigningKey())
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
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public Long extractUserIdFromToken(String authHeader) {
		String token = authHeader.substring(7);
		String email = extractEmail(token);
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado no token"));
		
		return user.getId();
	}
}
