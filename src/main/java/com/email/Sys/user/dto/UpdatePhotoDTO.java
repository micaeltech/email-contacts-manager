package com.email.Sys.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
public class UpdatePhotoDTO {
	
	@NotBlank(message = "Please upload an image.")
	@Pattern(regexp = "^(https?://[\\w\\-./?=&%]+|data:image/[\\w]+;base64,.*)?$", // "^[^\\s]+\\.(?i)(jpg|jpeg|png|gif|webp)$"
			message = "Invalid photo URL.")
	private String photoUrl;
	
	public String getPhotoUrl() { return photoUrl; }
	public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
}
