# 📝 Todo List Service

A resilient backend service for managing todo items.  
Implements REST APIs, automatic status updates (past due), immutability for overdue tasks,  
and is fully containerized with Docker.

---

## 1. Service Description
This service allows users to:
- Add a todo item with description + due date.
- Update an item’s description.
- Mark an item as done or not done.
- Retrieve single or multiple todo items.
- Automatically mark items as **past due** if overdue.
- Prevent any modifications once an item is **past due**.

**Assumptions**:
- No user authentication is required (per assignment).
- Past-due detection happens automatically when fetching/updating items.
- H2 in-memory DB is used, so data is lost when the app stops.

---

## 2. Tech Stack
- **Java 21**  
- **Spring Boot 3** (Web + JPA + Validation)  
- **H2 Database** (in-memory)  
- **JUnit 5 + MockMvc** for tests  
- **Docker & Docker Compose** for containerization  

---

## 3. How-To Guide

### Build the Service
```bash
./mvnw clean package
