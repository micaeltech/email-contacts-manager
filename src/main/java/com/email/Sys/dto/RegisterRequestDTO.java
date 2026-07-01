package com.email.Sys.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;

public class RegisterRequestDTO {
    
    @NotBlank(message = "Name is required")
    @Size(min = 4, message = "Name must have at least 4 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Name must contain only letters and spaces")
    private String name;
    
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$",
             message = "Email cannot contain uppercase, spaces or invalid special characters")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must have at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
             message = "Password must contain uppercase, lowercase, number and special character")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", 
             message = "Phone must be valid. Use format: +xx xxxxxxxxxx (10 to 15 digits)")
    private String phone;
    
    @AssertTrue(message = "Age cannot exceed 120 years")
    public boolean isValidAge() {
        if (birthDate == null) return false; 
        LocalDate today = LocalDate.now();
        int age = Period.between(birthDate, today).getYears();
        return age <= 120;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email != null ? email.toLowerCase().trim() : null;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
