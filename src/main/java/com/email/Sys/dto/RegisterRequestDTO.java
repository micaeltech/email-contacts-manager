package com.email.Sys.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;

public class RegisterRequestDTO {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 4, message = "Nome deve ter no mínimo 4 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Nome deve conter apenas letras e espaços")
    private String name;
    
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate birthDate;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$",
             message = "Email não pode conter maiúsculas, espaços ou caracteres especiais inválidos")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
             message = "Senha deve conter letra maiúscula, minúscula, número e caractere especial")
    private String password;
    
    @AssertTrue(message = "Idade não pode ultrapassar 120 anos")
    public boolean isValidAge() {
        if (birthDate == null) return false; 
        LocalDate today = LocalDate.now();
        int age = Period.between(birthDate, today).getYears();
        return age <= 120;
    }
    
    // Getters e Setters
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
}


