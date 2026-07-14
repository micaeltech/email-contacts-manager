package com.email.Sys.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SendEmailRequestDTO {

	@NotBlank(message = "Recipient is required.")
    @Email(message = "Recipient email must be valid..")
    private String to;      
    
    @Size(max = 100, message = "Maximum of 100 characters.")
    private String subject;
    
    @Size(max = 1500, message = "Maximum of 1.500 characters.")
    @NotBlank(message = "Content is required.")
    private String content;
    
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
