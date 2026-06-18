package com.email.Sys.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdatePhotoDTO {
	
	@NotBlank(message = "Faça upload da imagem.")
	@Pattern(regexp = "^(https?://[\\w\\-./?=&%]+|data:image/[\\w]+;base64,.*)?$", // "^[^\\s]+\\.(?i)(jpg|jpeg|png|gif|webp)$"
			message = "URL da foto inválida. ")
	private String photoUrl;
	
	public String getPhotoUrl() { return photoUrl; }
	public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
}
