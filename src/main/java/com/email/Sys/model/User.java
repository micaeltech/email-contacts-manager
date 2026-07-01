package com.email.Sys.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    
    @Column(name = "secondary_email", unique = true)
    private String secondaryEmail;
    
    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String password;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(length = 20)
    private String phone;
    
    @Column(name = "photo_url", length = 1000)
    private String photoUrl;
    
    public enum Status {
        OFFLINE, ONLINE
    }
    
    @PrePersist
    public void prePersist() {
    	this.createdAt = LocalDateTime.now();
    }
    
    public User() {}
    public User(String name, LocalDate birthDate, String email, String password, String phone) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.phone = phone;
        
        this.secondaryEmail = null;
        this.status = Status.OFFLINE;
        this.photoUrl = null;
    }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSecondaryEmail() { return secondaryEmail; }
    public void setSecondaryEmail(String secondaryEmail) { this.secondaryEmail = secondaryEmail; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    
    public String getPhotoUrl() { return photoUrl;}
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    
    
}
