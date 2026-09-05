# ACME Compensation Hub — Database Design

## 1. Database

PostgreSQL will be used as the relational database.

The domain naturally consists of structured entities with relationships and requires filtering, sorting, transactions, and aggregation queries. PostgreSQL provides these capabilities while remaining simple to operate for the expected scale.

## 2. Entity Relationship

```text
┌─────────────────────────┐
│       employees         │
├─────────────────────────┤
│ id PK                   │
│ employee_code UK        │
│ first_name              │
│ last_name               │
│ email UK                │
│ country                 │
│ department              │
│ job_title               │
│ currency                │
│ salary_amount           │
│ reporting_salary        │
│ created_at              │
│ updated_at              │
└────────────┬────────────┘
             │
             │ 1 : N
             ▼
┌─────────────────────────┐
│     salary_history      │
├─────────────────────────┤
│ id PK                   │
│ employee_id FK          │
│ previous_salary         │
│ new_salary              │
│ currency                │
│ previous_reporting      │
│ new_reporting           │
│ changed_at              │
└─────────────────────────┘
```

## 3. Employees

The `employees` table stores the current state of each employee.

### Fields

| Field              | Purpose                                        |
| ------------------ | ---------------------------------------------- |
| `id`               | Internal database identifier                   |
| `employee_code`    | Business identifier visible to HR              |
| `first_name`       | Employee first name                            |
| `last_name`        | Employee last name                             |
| `email`            | Unique employee email                          |
| `country`          | Employee country                               |
| `department`       | Organizational department                      |
| `job_title`        | Employee role                                  |
| `currency`         | Local salary currency                          |
| `salary_amount`    | Salary in local currency                       |
| `reporting_salary` | Normalized salary for organizational reporting |
| `created_at`       | Record creation time                           |
| `updated_at`       | Last update time                               |

`employee_code` and `email` will be unique.

## 4. Salary History

The `salary_history` table records salary changes rather than overwriting previous compensation without a record.

Each salary change creates a new history entry containing:

* Previous salary
* New salary
* Currency
* Previous reporting salary
* New reporting salary
* Time of change

This allows HR to understand how an employee's compensation changed over time.

## 5. Salary Update Transaction

A salary update should update the employee record and create the corresponding salary-history record within the same database transaction.

Conceptually:

```text
BEGIN TRANSACTION

Read current salary
        ↓
Validate new salary
        ↓
Update employee salary
        ↓
Insert salary history

COMMIT
```

If any operation fails, the entire transaction should be rolled back.

This prevents the employee's current salary from becoming inconsistent with its history.

## 6. Currency Representation

The employee's local salary is stored using:

```text
salary_amount
currency
```

A normalized reporting value is additionally stored as:

```text
reporting_salary
```

This allows:

* Displaying the employee's actual/local salary.
* Performing organization-wide compensation analysis.

The reporting conversion will use a deterministic conversion model for the assessment rather than depending on a live external exchange-rate provider.

## 7. Indexing Strategy

Indexes will be considered for fields frequently used by employee search and filtering.

Initial candidates include:

```text
employee_code
email
country
department
```

Indexes will be validated against actual query patterns rather than adding indexes indiscriminately.

Pagination will be used for employee-list queries.

## 8. Data Integrity

The database should enforce important invariants where practical:

* Employee code must be unique.
* Email must be unique.
* Salary must not be negative.
* Salary history must reference an existing employee.
* Required employee fields must not be null.

Application-level validation will provide user-friendly API errors, while database constraints provide an additional integrity boundary.

## 9. Seed Data

A deterministic seed process will generate approximately 10,000 employees.

The generated dataset will contain variation across:

* Countries
* Departments
* Job titles
* Salaries
* Currencies

Deterministic generation ensures the same seed produces reproducible data, making local development and testing easier.
