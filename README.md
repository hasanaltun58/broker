
# Broker API

This project is a backend API for a brokerage firm where employees can manage customer orders, deposits, and withdrawals. The API is built using **Spring Boot** and provides JWT-based authentication and role-based access control for **USER** and **ADMIN** roles.

## Features

- **Create Orders**: Employees can create buy/sell stock orders for customers.
- **List Orders**: List all orders for a specific customer.
- **Cancel Orders**: Cancel a pending order.
- **Deposit/Withdraw Money**: Deposit or withdraw money for a customer.
- **Match Orders (Admin Only)**: Admins can match pending orders.
- **JWT Authentication**: Secure endpoints using JWT tokens with role-based access control.
- **Swagger API Documentation**: Explore and test APIs with Swagger.

## Requirements

- Java 11+
- Maven
- PostgreSQL (can be replaced with H2 for testing)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/broker-api.git
cd broker-api
```

### 2. Database Configuration

Modify the `application.properties` file located in `src/main/resources` to configure your database:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/broker_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

To use **H2** for testing, you can replace it with:

```properties
spring.datasource.url=jdbc:h2:mem:broker_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
```

### 3. Build the Project

Use Maven to build the project:

```bash
mvn clean install
```

### 4. Run the Application

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

### 5. Access Swagger UI

You can access the Swagger UI for testing the APIs at:

```
http://localhost:8080/swagger-ui/index.html
```

## API Endpoints

### Authentication

- **Login**: `POST /api/auth/login`
  
  Example request body:
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }
  ```
  
  On success, the response will include a JWT token:
  ```json
  {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer"
  }
  ```

- **Register**: `POST /api/auth/register` (Admin can use this endpoint to create users)
