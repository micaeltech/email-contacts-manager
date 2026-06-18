package com.email.Sys.user.dto;

import com.email.Sys.model.User;

public class UserResponseDTO {
	private User user;
	private String token;
	
	public UserResponseDTO(User user, String token) {
		this.user = user;
		this.token = token;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getToken() {
		return token;
	}
}
