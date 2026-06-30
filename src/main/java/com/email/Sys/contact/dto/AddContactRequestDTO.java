package com.email.Sys.contact.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddContactRequestDTO {
	
	@NotBlank(message = "Email do contato é obrigatório")
	@Email(message = "Email deve ser válido")
	private String contactEmail;
	
	@Size(min = 3, max = 30, message = "Apelido deve ter no mínimo 3 caracteres")
	@Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Apelido deve conter apenas letras e espaços")
	private String nickname;
	
	public String getContactEmail() { return contactEmail; }
	public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
	public String getNickname() { return nickname; }
	public void setNickname(String nickname) { this.nickname = nickname; }
}
