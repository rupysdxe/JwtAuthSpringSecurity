## JWT Authentication Implementation in Spring Boot



This project provides an example implementation of JWT (JSON Web Token) authentication in a Spring Boot application. JWT is a stateless authentication mechanism that allows secure communication between a client and a server by generating and validating tokens.

## Features

- User authentication
- Token generation and verification using JWT.
- Role-based access control for protected resources.



## Prerequisites

- Java Development Kit (JDK) 8 
- Gradle
- MySQL 

## Getting Started

1. Clone the Repository

   ```bash
   https://github.com/rupysdxe/JwtAuthentication.git
   ```

   

2. Configure the database connection in `application.properties` file:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/db_name
   spring.datasource.username=db_username
   spring.datasource.password=db_password
   ```

   

3. Build the project using gradle.

   ```bash
   gralde clean build
   ```

   

4. Run the JAR file

   ```bash
   java -jar target/your-app.jar
   ```

   

## API Endpoints

```http
POST /oauth/token
```

Request Body

```http
{
    "email":"rupesh@gmail.com",
    "password":"1234"
}
```

Response Body

```http
{
    "username": "rupesh@gmail.com",
    "jwt": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJydXBlc2hAZ21haWwuY29tIiwiaWF0IjoxNjg1MjEwNDUzLCJleHAiOjE2ODUyOTY4NTN9.raVpdlJDhNKqk-EjzRoxNY6J93QHnSFznwf3ONp7LQq8ep7M6xAPpkj7JI9tl0QqszeN8D5KVyNZeGa65mB-3g",
    "roles": [
        "ROLE_ADMIN",
        "ROLE_USER"
    ]
}
```

