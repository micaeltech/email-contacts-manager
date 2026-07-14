package com.email.Sys.contact.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddContactRequestDTO {
	
	@NotBlank(message = "Contact email is required.")
	@Email(message = "Email must be valid.")
	private String contactEmail;
	
	@Size(min = 3, max = 30, message = "Nickname must be at least 3 characters and spaces.")
	@Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nickname must contain only letters and spaces.")
	private String nickname;
	
	public String getContactEmail() { return contactEmail; }
	public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
	public String getNickname() { return nickname; }
	public void setNickname(String nickname) { this.nickname = nickname; }
}
