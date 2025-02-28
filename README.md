# Test University Learning App Backend

Welcome to the **Test University Learning App Backend**—a robust, secure, and scalable API built with **Spring Boot** to power an e-learning platform. This backend supports user authentication, test series management, and role-based access control, designed for seamless integration with a frontend application. It leverages modern technologies and best practices to deliver a production-grade solution.

## Overview
The Test University Learning App Backend provides RESTful APIs for managing user authentication, test series (streams, topics, questions, and quizzes), and administrative tasks. It incorporates **JWT authentication** for security and **role-based access control (RBAC)** to differentiate between **Admin** and **User** permissions, ensuring a tailored experience for each role.

## Tech Stack
- **Backend**: Spring Boot, Java
- **Database**: MySQL
- **Security**: JWT (JSON Web Token) Authentication, Role-Based Access Control (RBAC)
- **API Documentation**: Swagger (OpenAPI)
- **Build Tool**: Maven

## Features
- **User Authentication**:
  - Login, registration, and secure JWT-based session management.
- **Role-Based Access Control (RBAC)**:
  - **Admin**: Full control over users and test content.
  - **User**: Access to test series and quizzes.
- **Test Series Management**:
  - CRUD operations for **Streams**, **Topics**, **Questions**, and **Quizzes**.
  - Structured hierarchy for organizing educational content.
- **API Documentation**:
  - Interactive Swagger UI—explore and test APIs effortlessly.
- **Exception Handling**:
  - Robust error management—consistent, user-friendly responses.

## Setup & Installation
Follow these steps to get the backend up and running locally:

### Prerequisites
- **Java**: JDK 17+
- **Maven**: Latest version
- **MySQL**: Server running locally or remotely
- **Git**: For cloning the repository

### Steps
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/sumitcs20/test-university-learning-server-app.git
   cd test-university-learning-server-app
