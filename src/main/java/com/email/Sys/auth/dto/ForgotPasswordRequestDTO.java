package com.email.Sys.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ForgotPasswordRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Method is required")
    @Pattern(regexp = "^(sms|email)$", message = "Method must be 'sms' or 'email'")
    private String method;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}
