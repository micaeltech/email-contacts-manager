package com.email.Sys.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {

	@NotBlank(message = "Email é obrigatório")
	@Email(message =  "Email deve ser válido")
	private String email;
	
	@NotBlank(message = "senha é obrigatória")
	@Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
	private String password;

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email != null ? email.toLowerCase().trim() : null;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
