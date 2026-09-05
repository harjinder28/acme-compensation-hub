# ACME Salary Management System

## 1. Goal

Build a web-based salary management system for ACME that replaces spreadsheet-based salary management for approximately 10,000 employees across multiple countries.

The system will enable HR Managers to efficiently maintain employee salary information and answer common compensation-related questions such as how salaries are distributed across countries, departments, and the organization as a whole.

The primary goal is to make salary data **searchable, maintainable, auditable, and useful for decision-making** while keeping the solution simple and maintainable.

## 2. Primary User

**HR Manager**

The HR Manager is responsible for maintaining employee compensation data and reviewing organization-level salary information.

## 3. Scope & Features

### Employee Management

* View employee records in a paginated list.
* Search employees by name, employee ID, or email.
* Filter employees by country and department.
* Filter by salary range.
* Sort employee records by relevant fields.

### Salary Management

* View an employee's current salary and currency.
* Update an employee's salary.
* Validate salary changes, including preventing invalid or negative values.
* Maintain salary history so previous compensation is not lost when a salary changes.

### Compensation Dashboard

Provide organization-level insights including:

* Total number of employees.
* Average salary.
* Median salary.
* Minimum and maximum salary.
* Salary distribution.
* Average salary by country.
* Average salary by department.

Because employees operate across multiple countries and currencies, salary data will retain its original/local currency while also maintaining a normalized reporting value for organization-wide comparisons.

### Data & Scale

* Provide deterministic seed data for 10,000 employees.
* Support multiple countries, departments, roles, and currencies.
* Use server-side pagination, filtering, sorting, and database-level aggregation where appropriate.

## 4. Non-Functional Requirements

The application should provide:

* A responsive and intuitive web interface.
* A maintainable backend and frontend structure.
* Clear API validation and error handling.
* Automated tests covering important business behavior.
* Fast and deterministic tests.
* A deployment-ready architecture suitable for the expected scale.
* CI automation to build and test the application.

## 5. Deliberately Out of Scope

The following are excluded from the initial version:

**Authentication and authorization** — The assessment focuses on salary management functionality rather than implementing an identity system. The application assumes an authorized HR user.

**Payroll processing, tax calculation, bonuses, and benefits** — These are separate business domains and are not required to solve the stated salary-management problem.

**Employee self-service** — The defined persona is the HR Manager, so employee-facing functionality is outside the initial scope.

**Real-time currency exchange rates** — Organization-wide reporting requires currency normalization, but live FX integration introduces an external dependency that is not necessary for the core problem. A defined/fixed reporting conversion model will therefore be used.

**Notifications and approval workflows** — No requirement has been provided for salary approval or notification processes, so they are intentionally excluded.

**Microservices and other distributed infrastructure** — With approximately 10,000 employees and a cohesive domain, a modular monolithic application is sufficient. Distributed services would add operational complexity without a demonstrated need.

## 6. Success Criteria

The solution will be considered successful when an HR Manager can:

1. Quickly find and filter employees.
2. View and update salary information safely.
3. Review salary history after compensation changes.
4. Understand organization-wide compensation through the dashboard.
5. Reliably perform these actions against a dataset of approximately 10,000 employees.

The solution should demonstrate **clear product thinking, pragmatic architecture, maintainable code, meaningful automated tests, and intentional use of AI throughout development**.
