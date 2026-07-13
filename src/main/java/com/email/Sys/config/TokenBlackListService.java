package com.email.Sys.config;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Set;

@Service
public class TokenBlackListService {

	private static final String PREFIX = "blacklist:";
	private static final long EXPIRATION_TIME = 86400000; 

	private final StringRedisTemplate redisTemplate;

	public TokenBlackListService(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void invalidateToken(String token) {
		redisTemplate.opsForValue().set(PREFIX + token, "true", Duration.ofMillis(EXPIRATION_TIME));
	}

	public boolean isTokenInvalidated(String token) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + token));
	}

	public void removeToken(String token) {
		redisTemplate.delete(PREFIX + token);
	}

	public int getBlacklistSize() {
		Set<String> keys = redisTemplate.keys(PREFIX + "*");
		return keys != null ? keys.size() : 0;
	}
}
