package com.email.Sys.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public class BackupEmailDTO {
	
	@NotBlank(message = "Preencha o campo.")
	@Email(message = "Email deve ser válido.")
	private String backupEmail;
	
	public String getBackupEmail() { return backupEmail; }
	
	public void setNewEmail(String backupEmail) { this.backupEmail = backupEmail; }
}
