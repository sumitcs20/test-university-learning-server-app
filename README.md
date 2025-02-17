Overview
The Test University Learning App backend is built using Spring Boot and provides APIs for user authentication, test series. It includes role-based access control and JWT authentication.

Tech Stack
Backend: Spring Boot, Java
Database: MySQL
Security: JWT Authentication, Role-based Access Control
API Documentation: Swagger

Features
User Authentication (Login, Register, JWT Security)
Role-based Access Control (ADMIN, USER)
Test Series (Streams, Topics, Questions, and Quizzes)
CRUD Operations for Streams, Topics, Questions, and Options
Swagger API Documentation
Exception Handling


Setup & Installation
1. Clone the Repository
git clone https://github.com/sumitcs20/test-university-learning-server-app.git
cd test-university-learning-server-app

3. Configure Database
Create a MySQL database (e.g., testuniversity).
Update src/main/resources/application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/testuniversity
spring.datasource.username=root
spring.datasource.password=root1234

5. Run the Application
./mvnw spring-boot:run  # For Mac/Linux
mvnw.cmd spring-boot:run  # For Windows

7. API Documentation (Swagger)
After running the server, visit:
http://localhost:8080/swagger-ui/index.html

Role-Based Access Control
Role	Permissions
Admin	Manage Users, Streams, Topics, Questions
User	Attempt Tests, View Questions


Contributors
Sumit (sumitcs20)

License
