# Spring Boot Backend App

A simple Spring Boot backend application with JWT authentication for showcase.

## Features

- ✅ JWT Authentication
- ✅ RESTful APIs
- ✅ CORS Configuration
- ✅ Docker Support
- ✅ Demo User Data

## API Endpoints

### Authentication

- `POST /api/auth/login` - User login
- `POST /api/auth/validate` - Validate token

### Users

- `GET /api/users` - Get all users (requires auth)
- `GET /api/users/{id}` - Get user by ID (requires auth)
- `GET /api/users/profile/{username}` - Get user profile (requires auth)

## Demo Credentials

- **Admin:** admin/password
- **User:** user/password

## Quick Start

```bash
# Run with Maven
mvn spring-boot:run

# Build and run
mvn clean package
java -jar target/springboot-backend-1.0.0.jar
```
