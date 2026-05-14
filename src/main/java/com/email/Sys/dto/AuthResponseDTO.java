package com.email.Sys.dto;

public class AuthResponseDTO {
	
	private String email;
	private String name;
	private String token;
	private String message;
	private boolean success;
	
	public AuthResponseDTO(String email, String name, String token) {
		this.email = email;
		this.name = name;
		this.token = token;
		this.success = true;
		this.message = "Autenticado com sucesso";
	}
	
	public AuthResponseDTO(String message, boolean success) {
		this.message = message;
		this.success = success;
	}
	
	//Getters
	public String getEmail() { return email; }
    public String getName() { return name; }
    public String getToken() { return token; }
    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
    
    // Setters
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) { this.name = name; }
    public void setToken(String token) { this.token = token; }
    public void setMessage(String message) { this.message = message; }
    public void setSuccess(boolean success) { this.success = success; }

}
