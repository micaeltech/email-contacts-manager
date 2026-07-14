package com.email.Sys.email.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.email.Sys.model.User;

@Entity
@Table(name = "emails")
public class Email {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id", nullable = false, referencedColumnName = "id")
	private User sender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id", nullable = false, referencedColumnName = "id")
	private User receiver;
	
	@Column(nullable = false, length = 200)
	private String subject;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;
	
	@Column(name = "sent_at", nullable = false) 
	private LocalDateTime sentAt;
	
	@Column(name = "is_read", nullable = false)
	private boolean isRead = false;
	
	@PrePersist
	protected void onCreate() { sentAt = LocalDateTime.now(); }
	
	public Email(){}
	
	public Email(User sender, User receiver, String subject, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }
    
    public User getReceiver() { return receiver; }
    public void setReceiver(User receiver) { this.receiver = receiver; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
}
