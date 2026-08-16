# 📋 Task Manager API

A lightweight, RESTful task management service built with **Spring Boot 4** and **Java 21**. It exposes a clean CRUD API for creating, tracking, and searching tasks — backed by Spring Data JPA and an in-memory H2 database for zero-setup local development.

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen?logo=springboot&logoColor=white)
![H2 Database](https://img.shields.io/badge/Database-H2-blue?logo=h2&logoColor=white)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)

---

## ✨ Overview

Task Manager API is a hands-on Spring Boot project that demonstrates a full request-to-database flow using modern Spring conventions: a REST controller, a JPA-backed entity, and a Spring Data repository with both derived and custom `@Query` methods. It's intentionally small in scope so the architecture stays easy to read, extend, and use as a reference for building larger Spring Boot services.

## 🚀 Features

- **Full CRUD** — create, read, update, and delete tasks through a versioned REST API (`/api/v1/tasks`)
- **Completion tracking** — mark tasks complete/incomplete and filter by status
- **Search** — case-insensitive title search
- **Auto timestamps** — `createdAt` is stamped automatically via JPA lifecycle hooks
- **In-memory database** — H2 with a browser-based console for quick inspection, no external DB required
- **Layered architecture** — clear separation between controller, repository, and entity layers

## 🛠️ Tech Stack

| Layer          | Technology                                  |
|----------------|----------------------------------------------|
| Language       | Java 21                                      |
| Framework      | Spring Boot 4.1.0 (Web MVC, Validation, Actuator) |
| Persistence    | Spring Data JPA / Hibernate                  |
| Database       | H2 (in-memory)                               |
| Build Tool     | Maven (via Maven Wrapper)                    |
| Boilerplate    | Lombok                                       |

## 📂 Project Structure

```
src/main/java/com/dionisius/taskmanager/
├── TaskmanagerApplication.java   # Application entry point
├── controller/
│   └── TaskController.java       # REST endpoints
├── entity/
│   └── Task.java                 # JPA entity
└── repository/
    └── TaskRepository.java       # Spring Data repository
```

## ⚡ Getting Started

### Prerequisites

- JDK 21+
- No local Maven install needed — the project ships with the Maven Wrapper (`mvnw`)

### Run the application

```bash
# Windows
mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run
```

The API starts on **http://localhost:8080**.

### Run the tests

```bash
mvnw.cmd test
```

## 📡 API Reference

Base path: `/api/v1/tasks`

| Method | Endpoint                    | Description                          |
|--------|------------------------------|---------------------------------------|
| GET    | `/`                          | List all tasks                        |
| GET    | `/{id}`                      | Get a single task by ID               |
| POST   | `/`                          | Create a new task                     |
| PUT    | `/{id}`                      | Update an existing task               |
| DELETE | `/{id}`                      | Delete a task                         |
| GET    | `/completed/{status}`        | List tasks by completion status       |
| GET    | `/search?title={keyword}`    | Search tasks by title (case-insensitive) |

### Example: create a task

```bash
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Write project README",
    "description": "Document setup and API usage",
    "completed": false
  }'
```

### Example: search tasks

```bash
curl "http://localhost:8080/api/v1/tasks/search?title=readme"
```

## 🗄️ H2 Console

While the app is running, inspect the in-memory database at:

```
http://localhost:8080/h2-console
```

| Field    | Value                     |
|----------|---------------------------|
| JDBC URL | `jdbc:h2:mem:taskdb`      |
| Username | `sa`                      |
| Password | `password`                |

## 🗺️ Roadmap

- [ ] Bean Validation on request payloads (`@NotBlank`, `@Size`, etc.)
- [ ] Global exception handling with meaningful error responses
- [ ] Pagination and sorting for list endpoints
- [ ] DTO layer to decouple API contracts from JPA entities
- [ ] Swagger / OpenAPI documentation
- [ ] Persistent database profile (PostgreSQL/MySQL) for production

## 📄 License

This project is currently unlicensed and intended for learning purposes.
