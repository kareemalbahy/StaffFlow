# 🏢 Employee Management System (EMS)

A modern, production-ready Spring Boot web application for managing employee records. The system provides robust **REST APIs** for integrations and a **Thymeleaf-based user interface** for direct interaction. It features soft deletions, pagination, sorting, search filters, custom input validations, and Swagger/OpenAPI documentation.

---

## ✨ Features

- **CRUD Operations**: Complete management of employee records (Create, Read, Update, Delete).
- **Soft Deletion**: Employees are never hard-deleted from the database; instead, they are flagged as inactive (`active = false`).
- **Paginated Search & Filtering**: Advanced search capabilities filtering by `firstName` and `department`, with customizable pagination and sorting.
- **Custom Validations**: Custom annotations such as `@ValidSalary` to enforce business logic (e.g., minimum wage validations).
- **Multi-Profile Configurations**: Separated settings for development (`local` with H2 Database) and production (`prod` with MySQL).
- **Interactive API Documentation**: Fully integrated Swagger UI for testing API endpoints.

---

## 🛠️ Tech Stack & Dependencies

- **Backend Framework**: Spring Boot 4.1.0
- **Language**: Java 26
- **Build Tool**: Maven
- **Database**:
  - **Local Development**: H2 Database (In-Memory)
  - **Production/Staging**: MySQL Database
- **Object Mapping**: ModelMapper 3.2.0 (for mapping between Entity and DTO)
- **API Documentation**: Springdoc OpenAPI Starter 3.0.3 (Swagger UI)
- **Boilerplate Reduction**: Lombok

---

## 📂 Project Structure

```
employee_management_system
 ├── src
 │    ├── main
 │    │    ├── java/com/ems/employee_management_system
 │    │    │    ├── config                     # OpenAPI/Swagger & ModelMapper configuration
 │    │    │    ├── controller                 # REST and MVC View Controllers
 │    │    │    ├── dto                        # Request & Response Data Transfer Objects
 │    │    │    ├── entity                     # Hibernate/JPA Entities
 │    │    │    ├── exception                  # Custom Exceptions & Global Exception Handler
 │    │    │    ├── mapper                     # Entity-to-DTO conversion logic
 │    │    │    ├── repository                 # Spring Data JPA Repository interface
 │    │    │    ├── service                    # Business Logic Interfaces and Implementations
 │    │    │    └── validation                 # Custom Validators (e.g., SalaryValidator)
 │    │    └── resources
 │    │         ├── templates                  # Thymeleaf HTML Templates (e.g., index.html)
 │    │         ├── static                     # Static assets (CSS, JS, Images)
 │    │         ├── application.properties     # Core app properties (port, profiles)
 │    │         ├── application-local.properties # Local profile (H2 settings)
 │    │         └── application-prod.properties  # Production profile (MySQL settings)
 │    └── test                                 # JUnit 5 Unit and Integration Tests
 ├── pom.xml                                   # Project Object Model
 └── README.md                                 # Project Documentation
```

---

## ⚡ Getting Started

### Prerequisites
- **JDK 26** or higher installed.
- **Maven 3.8+** installed.
- (Optional) **MySQL Server** running if you intend to run in the `prod` profile.

### Configuration Profiles

The application uses Spring Boot profiles to determine database connectivity. By default, it runs with the `local` profile.

1. **Local Profile (`local`)**:
   - Uses an in-memory **H2 Database**.
   - H2 Console is enabled at: `http://localhost:8080/h2-console`
   - **Credentials**: JDBC URL: `jdbc:h2:mem:emsdb` | User: `sa` | Password: `password`

2. **Production Profile (`prod`)**:
   - Connects to a **MySQL Database**.
   - DB URL: `jdbc:mysql://localhost:3306/emsdb`
   - **Credentials**: User: `root` | Password: `rootpassword` (configurable in [application-prod.properties](file:///src/main/resources/application-prod.properties))

---

### Running the Application

To run the application locally using Maven:

```bash
# Clone the repository
git clone <repository-url>
cd employee_management_system

# Run with local profile (default)
./mvnw spring-boot:run

# Or run explicitly with production profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

Once started, the application will be accessible at: **`http://localhost:8080`**

---

## 🔌 API Endpoints

All REST APIs are prefixed with `/api/employees`.

| Method | Endpoint | Description | Request Body / Params |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/employees` | Create a new active employee | `EmployeeRequestDto` (JSON) |
| **GET** | `/api/employees/{id}` | Get active employee by ID | *None* |
| **GET** | `/api/employees` | Search & Page active employees | Query Params: `pageNo`, `pageSize`, `sortBy`, `sortDir`, `firstName`, `department` |
| **PUT** | `/api/employees/{id}` | Update existing employee | `EmployeeRequestDto` (JSON) |
| **DELETE**| `/api/employees/{id}` | Soft delete employee | *None* (Sets status to inactive) |

---

## 📖 Swagger / OpenAPI Docs

Interactive API documentation and playground are available via Swagger UI. Start the application and navigate to:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **JSON OpenAPI Specs**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🛡️ Business Logic & Validations

The application enforces specific enterprise business logic:
- **Email Uniqueness**: Restricts creating or updating an employee with an email that already exists in the system.
- **Minimum Wage Constraint (`@ValidSalary`)**: Uses a custom validation constraint to ensure that an employee's salary is greater than or equal to the minimum wage threshold of **`1,000.00`**.
- **Past or Present Hire Date**: Ensures the hire date cannot be in the future.
- **Phone Number Format**: Validates telephone numbers according to E.164 standards (`^\+[1-9]\d{7,14}$`).
