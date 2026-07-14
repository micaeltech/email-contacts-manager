package com.email.Sys.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateEmailDTO {

	@NotBlank(message = "Fill in the field.")
	@Email(message = "Email must be valid.")
	private String newEmail;
	
	public String getNewEmail() { return newEmail; }
	
	public void setNewEmail(String newEmail) { this.newEmail = newEmail; }
}
