package com.email.Sys.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SendEmailRequestDTO {

	@NotBlank(message = "Destinatário é obrigatório")
    @Email(message = "Email do destinatário deve ser válido")
    private String to;      
    
    @Size(max = 200, message = "Máximo de 200 caracteres")
    private String subject;
    
    @Size(max = 1500, message = "Máximo de 1.500 caracteres")
    @NotBlank(message = "Conteúdo é obrigatório")
    private String content;
    
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
