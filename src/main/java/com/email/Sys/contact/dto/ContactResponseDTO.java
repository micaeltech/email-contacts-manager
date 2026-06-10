package com.email.Sys.contact.dto;

public class ContactResponseDTO {

	private Long contactUserId;
	private String contactName;
	private String contactEmail;
	private String nickname;
	private String status;
	
	public ContactResponseDTO(Long contactUserId, String contactName, String contactEmail, String nickname, String status) {
        this.contactUserId = contactUserId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.nickname = nickname;
        this.status = status;
    }
       
    public Long getContactUserId() { return contactUserId; }
    public String getContactName() { return contactName; }
    public String getContactEmail() { return contactEmail; }
    public String getNickname() { return nickname; }
    public String getStatus() { return status; }
}
