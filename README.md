# 📋 Task Manager API

A RESTful task management service built with **Spring Boot 4** and **Java 21**, structured as a proper layered application: controllers delegate to services, services work through mappers and repositories, and DTOs keep the API contract independent of the JPA model. It ships with pagination, filtering, categorized tasks, centralized error handling, and environment-specific configuration profiles — all backed by an in-memory H2 database for zero-setup local development.

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen?logo=springboot&logoColor=white)
![H2 Database](https://img.shields.io/badge/Database-H2-blue?logo=h2&logoColor=white)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)

---

## ✨ Overview

Task Manager API started as a simple CRUD exercise and has grown into a small reference implementation of common production patterns in a Spring Boot service: a service/mapper layer instead of controllers touching repositories directly, request/response DTOs with Bean Validation, a `@ControllerAdvice` that turns exceptions into consistent JSON error bodies, paginated and filterable listing endpoints, a `Task` → `Category` relationship, and per-environment configuration via Spring profiles (`dev`, `stg`, `prod`).

## 🚀 Features

- **Full CRUD** for tasks, validated on the way in via `TaskRequest` (`@NotBlank`, `@Size`)
- **Pagination & sorting** — `page`, `size`, `sortBy`, `sortDir` on the listing endpoints
- **Combined filtering** — search by title and/or completion status in one query, paginated
- **Task categories** — tasks optionally belong to a `Category` via a many-to-one relation
- **Centralized error handling** — not-found, validation, and unexpected errors all return a consistent JSON shape via `GlobalExceptionHandler`
- **Environment-specific config** — `dev` (active by default), `stg`, and `prod` Spring profiles
- **Externalized app metadata** — two side-by-side approaches (`@Value` vs. type-safe `@ConfigurationProperties`) exposed through `/info` endpoints
- **Actuator monitoring** — health, metrics, env, mappings, and more under `/actuator`
- **In-memory database** — H2 with a browser console, no external DB required

## 🛠️ Tech Stack

| Layer          | Technology                                              |
|----------------|-----------------------------------------------------------|
| Language       | Java 21                                                    |
| Framework      | Spring Boot 4.1.0 (Web MVC, Validation, Actuator)          |
| Persistence    | Spring Data JPA / Hibernate                                |
| Database       | H2 (in-memory)                                             |
| Build Tool     | Maven (via Maven Wrapper)                                  |
| Boilerplate    | Lombok                                                     |

## 🏗️ Architecture

```
Controller → Service → Mapper → Repository → Entity
     ↑                                          
   DTO (request/response)         GlobalExceptionHandler (cross-cutting)
```

- **Controllers** (`controller/`) handle HTTP concerns only — routing, request binding, status codes
- **Services** (`service/`) own business logic and transactions (`@Transactional`)
- **Mapper** (`mapper/TaskMapper.java`) converts between entities and DTOs, resolving the `Category` relation
- **Repositories** (`repository/`) are Spring Data JPA interfaces, mixing derived queries and custom `@Query` methods
- **Entities** (`entity/`) are the JPA model; DTOs (`dto/`) are the public API shape, kept intentionally separate

## 📂 Project Structure

```
src/main/java/com/dionisius/taskmanager/
├── TaskmanagerApplication.java     # Application entry point
├── config/
│   └── AppProperties.java          # Type-safe @ConfigurationProperties(prefix = "app")
├── controller/
│   ├── TaskController.java         # Task CRUD, search, pagination
│   ├── CategoryController.java     # Category read/create
│   ├── InfoController.java         # App info via @Value
│   └── InfoControllerAdvanced.java # App info via AppProperties
├── dto/
│   ├── TaskRequest.java            # Validated inbound task payload
│   ├── TaskResponse.java           # Outbound task representation
│   └── CategoryResponse.java       # Outbound category representation
├── entity/
│   ├── Task.java                   # Task JPA entity (→ Category, many-to-one)
│   └── Category.java                # Category JPA entity
├── exception/
│   ├── GlobalExceptionHandler.java # Centralized @ControllerAdvice
│   └── TaskNotFoundException.java
├── mapper/
│   └── TaskMapper.java             # Entity ↔ DTO conversion
└── repository/
    ├── TaskRepository.java
    └── CategoryRepository.java

src/main/resources/
├── application.yml                 # Base config, activates the "dev" profile
├── application-stg.yml             # Staging overrides
└── application-prod.yml            # Production overrides
```

## ⚡ Getting Started

### Prerequisites

- JDK 21+
- No local Maven install needed — the project ships with the Maven Wrapper (`mvnw`)

### Run the application

```bash
# Windows — runs with the default "dev" profile
mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run
```

The API starts on **http://localhost:8080**.

### Run with a specific profile

```bash
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=stg
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=prod
```

### Run the tests

```bash
mvnw.cmd test
```

## ⚙️ Configuration Profiles

| Profile | File                       | Notes                                   |
|---------|----------------------------|-------------------------------------------|
| `dev`   | `application.yml`          | Active by default; verbose SQL logging (`show-sql: true`) |
| `stg`   | `application-stg.yml`      | Staging overrides; quiet SQL logging      |
| `prod`  | `application-prod.yml`     | Production overrides; quiet SQL logging   |

All profiles currently point at the same in-memory H2 instance — `stg`/`prod` are wired up as real Spring profiles ready to be pointed at their own datasources.

App metadata (`app.name`, `app.version`, `app.max-task-per-page`) is defined once in `application.yml` and exposed two ways for comparison: raw `@Value` injection (`InfoController`) and a validated `@ConfigurationProperties` bean (`InfoControllerAdvanced` / `AppProperties`).

## 📡 API Reference

### Tasks — `/api/v1/tasks`

| Method | Endpoint                                        | Description                                              |
|--------|---------------------------------------------------|------------------------------------------------------------|
| GET    | `/`                                                | List all tasks (unpaginated)                               |
| GET    | `/page`                                            | Paginated + sorted task list                                |
| GET    | `/search`                                          | Filter by `title` and/or `completed`, paginated + sorted   |
| GET    | `/{id}`                                            | Get a single task by ID                                     |
| POST   | `/`                                                | Create a task (validated)                                   |
| PUT    | `/{id}`                                            | Update a task (validated)                                   |
| DELETE | `/{id}`                                            | Delete a task                                                |
| GET    | `/completed/{status}`                              | List tasks by completion status (unpaginated)               |
| GET    | `/searchByTitle?title={keyword}`                   | Case-insensitive title search (unpaginated)                 |

**Pagination/sorting query params** (on `/page` and `/search`): `page` (default `0`), `size` (default `10`), `sortBy` (default `createdAt`), `sortDir` (default `DESC`). `/search` additionally accepts optional `title` and `completed`.

### Categories — `/api/v1/categories`

| Method | Endpoint | Description                    |
|--------|----------|----------------------------------|
| POST   | `/`      | Create a category                |
| GET    | `/`      | ⚠️ Currently returns `500` — see [Known Issues](#-known-issues) |

### Info & Ops

| Method | Endpoint                    | Description                                  |
|--------|-------------------------------|------------------------------------------------|
| GET    | `/api/v1/info`                 | App metadata via `@Value`                       |
| GET    | `/api/v1/info/advanced`        | App metadata via `@ConfigurationProperties`     |
| GET    | `/actuator/health`             | Health check                                    |
| GET    | `/actuator/*`                  | Metrics, env, beans, mappings, logger (see `application.yml`) |

### Example: create a task with a category

```bash
curl -X POST http://localhost:8080/api/v1/categories \
  -H "Content-Type: application/json" \
  -d '{"name": "Work", "description": "Work related tasks"}'

curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Write project README",
    "description": "Document setup and API usage",
    "completed": false,
    "categoryId": 1
  }'
```

### Example: paginated, filtered search

```bash
curl "http://localhost:8080/api/v1/tasks/search?title=readme&completed=false&page=0&size=5&sortBy=title&sortDir=ASC"
```

### Example: validation error response

```bash
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": ""}'
```

```json
{
  "timestamp": "2026-08-17T13:34:50.10",
  "message": "Validation Failed",
  "errors": ["title: Title is mandatory"]
}
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

## ⚠️ Known Issues

- **`GET /api/v1/categories` returns HTTP 500.** The handler declares `@PathVariable Long id`, but neither the class- nor method-level `@RequestMapping` defines an `{id}` segment, so Spring can't resolve it (`Required URI template variable 'id' for method parameter type Long is not present`). It needs a route like `/{id}` (and a matching `GET` mapping) to work as intended.

## 🗺️ Roadmap

- [ ] Fix the `CategoryController` route above and add `GET /categories/{id}` / list / update / delete
- [ ] `CategoryRequest`/`CategoryResponse` DTOs, mirroring the `Task` pattern (currently the entity is exposed directly)
- [ ] Pagination for the category list
- [ ] Swagger / OpenAPI documentation
- [ ] Broader test coverage (currently just the Spring context load test)
- [ ] Point `stg`/`prod` profiles at real datastores instead of shared in-memory H2

## 📄 License

This project is currently unlicensed and intended for learning purposes.
