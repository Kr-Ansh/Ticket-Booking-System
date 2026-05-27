# 🎫 Ticket Booking Architecture Engine

A production-grade, highly optimized backend REST API engine engineered to manage seat allocations and transactional consistency. Built with **Java 17+**, **Spring Boot**, **Spring Data JPA**, and an in-memory **H2 Database**, this project showcases clean-code enterprise development patterns, automated test coverage, and self-documenting API structures.

---

## 🏗️ System Architecture & Design Patterns

The system adheres strictly to the **Layered Architecture (Controller-Service-Repository-Model)** design paradigm, ensuring high cohesion, low coupling, and a clean separation of concerns:

* **Presentation Layer (Controllers):** Handles incoming HTTP web requests, decouples public API contracts using **DTOs**, and enforces automatic constraint validations.
* **Business Logic Layer (Services):** Directs core transactional rules. It utilizes Spring's **Aspect-Oriented Programming (AOP)** to handle atomicity using `@Transactional` guardrails.
* **Data Access Layer (Repositories):** Leverages **Spring Data JPA** dynamic proxies to eliminate raw boilerplate SQL strings and execute safe database runtime operations.
* **Global Interception Watchtower (Exception Handling):** Centralizes error handling away from business paths using a global `@RestControllerAdvice` component.

---

## 🛠️ Tech Stack & Dependencies

* **Core Platform:** Java 17+ / Spring Boot 3.x
* **Data Layer:** Spring Data JPA, Hibernate, H2 In-Memory Database
* **Validation & Mapping:** Jakarta Bean Validation, Lombok
* **Testing Suite:** JUnit 5, Mockito
* **API Documentation:** Springdoc OpenAPI v2 (Swagger UI)

---

## 📂 Project Directory Structure

```text
src/
├── main/
│   ├── java/com/irons/ticketbooking/
│   │   ├── config/        # OpenAPI/Swagger Document Configurations
│   │   ├── controller/    # REST API Gatekeepers & Routing Nodes
│   │   ├── dto/           # Data Transfer Objects & Input Validations
│   │   ├── exception/     # Global Exception Interception Watchtower
│   │   ├── model/         # Database Entity Blueprints & ORM Models
│   │   ├── repository/    # JPA Data Access Interfaces
│   │   └── service/       # Business Logic Engine & Transaction Handling
│   └── resources/
│       ├── application.properties   # Application Configuration Profiles
│       └── data.sql                 # Automated Local Database Seeding Script
└── test/
    └── java/com/irons/ticketbooking/
        └── service/       # Isolated Service Layer Unit Tests (Mockito)