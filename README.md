# Email Contacts Manager

Backend API for managing contacts and emails. Built with Spring Boot.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Security + JWT
- PostgreSQL
- Maven

## Features

- User registration, login, and logout
- Profile management (email, password, photo, status)
- Contact list (add, remove, search)
- Email system (send, inbox, sent, conversation)

## API Endpoints

### Auth

- `POST /auth/register` – Create account
- `POST /auth/login` – Login
- `POST /auth/logout` – Logout

### User Profile

- `PUT /User/email` – Update email
- `PUT /User/password` – Update password
- `POST /User/backup-email` – Add backup email
- `DELETE /User/backup-email` – Remove backup email
- `PUT /User/status` – Set online/offline
- `PUT /User/photo` – Update photo
- `DELETE /User/photo` – Remove photo

### Contacts

- `POST /contacts` – Add contact
- `GET /contacts` – List contacts
- `GET /contacts/search?q=` – Search by nickname
- `DELETE /contacts/{id}` – Remove contact

### Emails

- `POST /emails/send` – Send email
- `GET /emails/feed` – Inbox
- `GET /emails/sent` – Sent emails
- `GET /emails/feed/contact/{id}` – Inbox from a contact
- `GET /emails/sent/contact/{id}` – Sent to a contact
- `GET /emails/conversation/{id}` – Conversation with a contact
- `GET /emails/{id}` – Email details

### Prerequisites

- Java 17
- PostgreSQL 12+

---------
### Setup

1. Clone the repo
2. Create a PostgreSQL database named `email_manager`
3. Update `application.properties` with your database credentials
4. Run `./mvnw spring-boot:run`

The API will be available at `http://localhost:8080`.


## Test Commands

### Auth

### Register
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","birthDate":"1990-05-15","email":"john@email.com","password":"Senha123!@#"}'

### Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@email.com","password":"Senha123!@#"}'

### Logout
curl -X POST http://localhost:8080/auth/logout \
  -H "Authorization: Bearer YOUR_TOKEN"

```
### USER PROFILE
```
### Update email
curl -X PUT http://localhost:8080/User/email \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"newEmail":"john.new@email.com"}'

### Add backup email
curl -X POST http://localhost:8080/User/backup-email \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"backupEmail":"backup@email.com"}'

### Update status
curl -X PUT http://localhost:8080/User/status \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"status":"OFFLINE"}'

### Update photo
curl -X PUT http://localhost:8080/User/photo \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"photoUrl":"https://example.com/photo.png"}'

### Remove photo
curl -X DELETE http://localhost:8080/User/photo \
  -H "Authorization: Bearer YOUR_TOKEN"

```
### CONTACTS
```
### Add contact
curl -X POST http://localhost:8080/contacts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"contactEmail":"maria@email.com","nickname":"Maria"}'

### List contacts
curl -X GET http://localhost:8080/contacts \
  -H "Authorization: Bearer YOUR_TOKEN"

### Search contact
curl -X GET http://localhost:8080/contacts/search?q=Mar \
  -H "Authorization: Bearer YOUR_TOKEN"

### Remove contact
curl -X DELETE http://localhost:8080/contacts/2 \
  -H "Authorization: Bearer YOUR_TOKEN"

```
### EMAILS
```
### Send email
curl -X POST http://localhost:8080/emails/send \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"to":"maria@email.com","subject":"Hello","content":"How are you?"}'

### Inbox
curl -X GET http://localhost:8080/emails/feed \
  -H "Authorization: Bearer YOUR_TOKEN"

### Sent
curl -X GET http://localhost:8080/emails/sent \
  -H "Authorization: Bearer YOUR_TOKEN"

### Inbox from a contact
curl -X GET http://localhost:8080/emails/feed/contact/2 \
  -H "Authorization: Bearer YOUR_TOKEN"

### Sent to a contact
curl -X GET http://localhost:8080/emails/sent/contact/2 \
  -H "Authorization: Bearer YOUR_TOKEN"

### Conversation
curl -X GET http://localhost:8080/emails/conversation/2 \
  -H "Authorization: Bearer YOUR_TOKEN"

### Email details
curl -X GET http://localhost:8080/emails/1 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### Author

Micael Tech – [GitHub](https://github.com/micaeltech)

