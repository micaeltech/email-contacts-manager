# Email Contacts Manager - Backend API

A RESTful API for managing email contacts with authentication, contact management, and messaging features. Built with Spring Boot 3, Spring Security, JWT, and H2/PostgreSQL.

## 🚀 Features

- **User Authentication** - Register and login with JWT tokens
- **Contact Management** - Add, list, search, and organize contacts
- **Messaging System** - Send and receive messages between contacts
- **Real-time Status** - Online/offline user tracking
- **Secure Validation** - Input validation, password encryption (BCrypt), duplicate prevention

## 🛠️ Tech Stack

- **Java 17** - Core language
- **Spring Boot 3.1.5** - Application framework
- **Spring Security** - Authentication & authorization
- **JWT** - Token-based authentication
- **Spring Data JPA** - Database operations
- **H2 Database** - Development database
- **PostgreSQL** - Production database (ready to switch)
- **Maven** - Dependency management
- **Lombok** - Boilerplate reduction

## 📋 API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Create a new user account |
| POST | `/auth/login` | Authenticate and receive JWT token |

### Contacts *(coming soon)*

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/contacts` | List all contacts |
| GET | `/contacts/search` | Search contacts by email/name |
| POST | `/contacts` | Add a new contact |
| PUT | `/contacts/{email}` | Update contact information |
| DELETE | `/contacts/{email}` | Delete a contact |

### Messages *(coming soon)*

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/messages/send` | Send an email message |
| GET | `/messages/{email}` | Get all messages from a contact |
| GET | `/messages/thread/{contactId}` | Get message thread history |

## 🔧 Validation Rules

### Registration
- **Name**: Minimum 4 characters, letters and spaces only
- **Email**: Valid format, no uppercase letters or spaces
- **Birth Date**: Must be in the past, maximum age 120 years
- **Password**: Minimum 8 characters, must include uppercase, lowercase, number, and special character

### Login
- **Email**: Valid email format
- **Password**: Minimum 8 characters

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone git@github.com:micaeltech/email-contacts-manager.git
   cd email-contacts-manager
