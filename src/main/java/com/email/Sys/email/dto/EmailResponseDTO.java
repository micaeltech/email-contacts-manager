package com.email.Sys.email.dto;

import java.time.LocalDateTime;
public class EmailResponseDTO {
    
    private Long id;
    private String from;       
    private String fromName;   
    private String to;        
    private String toName;     
    private String subject;
    private String content;
    private LocalDateTime sentAt;
    private boolean isRead;
    
    public EmailResponseDTO(Long id, String from, String to, String fromName, String toName,
                            String subject, String content, LocalDateTime sentAt, boolean isRead) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.fromName = fromName;
        this.toName = toName;
        this.subject = subject;
        this.content = content;
        this.sentAt = sentAt;
        this.isRead = isRead;
    }
    
    public Long getId() { return id; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getFromName() { return fromName; }
    public String getToName() { return toName; }
    public String getSubject() { return subject; }
    public String getContent() { return content; }
    public LocalDateTime getSentAt() { return sentAt; }
    public boolean isRead() { return isRead; }
}