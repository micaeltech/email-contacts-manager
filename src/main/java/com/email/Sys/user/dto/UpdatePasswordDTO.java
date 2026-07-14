package com.email.Sys.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdatePasswordDTO {
	
	@NotBlank(message = "Fill in the field.")
    private String currentPassword;
	
	@NotBlank(message = "New password is required.")
    @Size(min = 8, message = "New password must be at least 8 characters long.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
             message = "New password must contain an uppercase letter, a lowercase letter, a number, and a special character.")
    private String newPassword;
	
    @NotBlank(message = "Password confirmation is required.")
    private String confirmNewPassword;
    
    
    public String getCurrentPassword() { return currentPassword; }
    public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
    
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    
    public String getConfirmNewPassword() { return confirmNewPassword; }
    public void setConfirmNewPassword(String confirmNewPassword) { this.confirmNewPassword = confirmNewPassword; }
}
