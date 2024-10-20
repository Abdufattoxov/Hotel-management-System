# Hotel Management System

## Overview

The **Hotel Management System** is a robust backend solution designed to streamline and automate core functionalities of hotel operations. Built using **Spring Boot** and **Java**, this system offers secure and efficient handling of hotel services such as room management, booking, customer administration, and more. The system is designed with a modular architecture to ensure flexibility, scalability, and ease of maintenance.

## Key Features

- **User Authentication & Authorization**: Implements role-based access control (RBAC) using Spring Security and JWT for secure user authentication, supporting roles like Admin and Customer.
- **Room Management**: Manages rooms with features like availability status updates, room type classifications, and booking status.
- **Booking System**: Provides booking functionality for users, allowing them to reserve rooms based on preferences. Also users can oroder rooms for future by setting check in and check out dates.
- **Service Management**: Supports hotel services like dining, housekeeping, and maintenance, which can be managed by the admin.
- **Admin Panel**: Administrators can manage users, assign roles, and perform administrative tasks such as adding or updating rooms, services, and handling bookings.
- **JWT Authentication**: Secure API communication with JSON Web Tokens (JWT) to ensure user credentials and sessions are handled securely.
- **Swagger Documentation**: Provides an interactive API documentation via Swagger UI for easy testing and understanding of the available endpoints.
- **PDF Generation**: Generates a pdf about users, hotels and orders.

## Technologies Used

- **Java 21**: Core programming language for developing the backend logic.
- **Spring Boot**: For building and deploying the RESTful APIs.
- **Spring Security**: For user authentication and authorization.
- **JWT**: JSON Web Tokens for secure API communication.
- **Spring Data JPA**: ORM for database interactions.
- **PostgreSQL**: The relational database for persistent data storage.
- **Lombok**: For reducing boilerplate code in data classes.
- **Swagger**: For API documentation and testing.
- **iText PDF**: For generating and sending PDF documents.

## Installation and Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Abdufattoxov/hotel-management-system.git

2. Navigate to the project directory
   ```bash
   cd hotel-management-system
   
3. Install project dependencies:
   ```bash
    mvn clean install

4. Configure the PostgreSQL database settings in application.properties
   ```bash
   spring.datasource.url=jdbc:postgresql://localhost:5432/hotel_manage_system_db
   spring.datasource.username=postgres
   spring.datasource.password=your-password

5. Run application
   ```bash
   mvn spring-boot:run

6. Swaggers can be seen with this url:
    ```bash
    http://localhost:8080/swagger-ui/index.html

