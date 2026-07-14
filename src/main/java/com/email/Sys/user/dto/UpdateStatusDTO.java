package com.email.Sys.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
public class UpdateStatusDTO {
    
    @NotNull(message = "Status is required.")
    @Pattern(regexp = "^(ONLINE|OFFLINE)$", 
             message = "Status must be ONLINE or OFFLINE.")
    private String status;
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
