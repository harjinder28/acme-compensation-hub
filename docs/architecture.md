# ACME Compensation Hub — Architecture

## 1. Overview

ACME Compensation Hub is a web-based application for HR Managers to manage employee salary information and understand compensation patterns across the organization.

The system consists of a React frontend, a Spring Boot backend, and a relational PostgreSQL database.

```text
┌──────────────────────────────┐
│        React Web App         │
│                              │
│ Dashboard                    │
│ Employee Search              │
│ Employee Details             │
│ Salary Management            │
└──────────────┬───────────────┘
               │
            REST API
               │
┌──────────────▼───────────────┐
│        Spring Boot API       │
│                              │
│ Employee Management          │
│ Salary Management            │
│ Compensation Analytics       │
│ Validation & Error Handling  │
└──────────────┬───────────────┘
               │
          JPA / SQL
               │
┌──────────────▼───────────────┐
│         PostgreSQL           │
│                              │
│ employees                    │
│ salary_history               │
└──────────────────────────────┘
```

## 2. Architectural Style

A **modular monolith** will be used for the backend.

The application will be deployed as a single Spring Boot service while keeping business responsibilities separated into modules such as employee management, salary management, and compensation analytics.

This approach provides a simple deployment model while keeping the codebase maintainable and allowing individual modules to evolve independently.

## 3. Why a Modular Monolith?

The expected dataset is approximately 10,000 employees, and the requirements do not indicate a need for independently deployed services or extremely high request volumes.

Introducing microservices would add infrastructure, deployment, networking, observability, and operational complexity without solving a demonstrated problem.

The modular monolith therefore provides a better balance between maintainability, simplicity, and scalability for the current requirements.

## 4. Backend Structure

The backend will follow a layered structure:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Controller

Responsible for:

* HTTP endpoints
* Request/response handling
* Input validation
* HTTP status codes

### Service

Contains business logic such as:

* Salary validation
* Salary changes
* Salary history creation
* Compensation calculations

### Repository

Responsible for database access using Spring Data JPA and appropriate database queries.

### DTOs

API requests and responses will use DTOs instead of exposing database entities directly.

This keeps the API contract independent from the persistence model.

## 5. Frontend

The frontend will use React with TypeScript.

The UI will be organized around the primary HR workflows:

```text
Dashboard
Employees
Employee Details
Salary Update
```

Server-side filtering, sorting, and pagination will be used for employee data rather than loading the entire employee dataset into the browser.

## 6. Data & Performance

The system is designed for approximately 10,000 employees.

Important decisions include:

* Server-side pagination for employee lists.
* Database-side filtering and sorting.
* Database-side aggregation for dashboard statistics.
* Appropriate indexes for frequently queried fields.
* Transactional salary updates to ensure salary and salary-history records remain consistent.

The application will not retrieve all employees into application memory for normal search or dashboard operations.

## 7. Currency Handling

Employees may be paid in different currencies.

The original salary amount and currency will be retained.

A normalized reporting value will also be stored so compensation can be compared across countries.

Real-time foreign exchange integration is outside the current scope. The reporting conversion model will therefore be deterministic and documented.

## 8. Error Handling

The backend will provide consistent API error responses for cases such as:

* Employee not found
* Invalid salary
* Invalid request data
* Duplicate employee identifiers
* Invalid filtering parameters

The frontend will provide meaningful loading, success, and error states.

## 9. Testing Strategy

Testing will focus on business-critical behavior.

The test suite will include:

* Unit tests for salary and employee business logic.
* Controller/API tests for important endpoints.
* Repository/integration tests for important database queries.
* Validation and error scenarios.

Tests should remain fast, deterministic, and easy to understand.

## 10. Deployment

The frontend and backend will be independently deployable, while PostgreSQL will be provided as a managed relational database.

A CI pipeline will automatically build and test the project on repository changes.

## 11. Future Evolution

The architecture leaves room for future additions such as:

* Authentication and role-based authorization.
* Salary approval workflows.
* Employee self-service.
* Audit reporting.
* Real-time exchange rates.
* Additional compensation components.

These are intentionally not part of the first version.
