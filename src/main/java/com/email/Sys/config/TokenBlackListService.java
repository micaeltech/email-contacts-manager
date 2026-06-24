package com.email.Sys.config;

import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class TokenBlackListService {
	
	private final Set<String> blacklist = ConcurrentHashMap.newKeySet();
	
	public void invalidateToken(String token) {
		blacklist.add(token);
	}
	
	public boolean isTokenInvalidated(String token) {
		return blacklist.contains(token);
	}
	
	public void removeToken(String token) {
		blacklist.remove(token);
	}
	
	public int getBlacklistSize() {
		return blacklist.size();
	}
}
