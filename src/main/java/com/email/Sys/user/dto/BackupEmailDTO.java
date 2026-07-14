package com.email.Sys.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public class BackupEmailDTO {
	
	@NotBlank(message = "fill in the fields.")
	@Email(message = "Email must be valid.")
	private String backupEmail;
	
	public String getBackupEmail() { return backupEmail; }

  // Email backup  
	public void setNewEmail(String backupEmail) { this.backupEmail = backupEmail; }
}
