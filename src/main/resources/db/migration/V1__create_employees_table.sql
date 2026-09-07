CREATE TABLE employees (
                           id BIGSERIAL PRIMARY KEY,

                           employee_code VARCHAR(50) NOT NULL UNIQUE,

                           first_name VARCHAR(100) NOT NULL,
                           last_name VARCHAR(100) NOT NULL,

                           email VARCHAR(255) NOT NULL UNIQUE,

                           country VARCHAR(2) NOT NULL,
                           department VARCHAR(100) NOT NULL,
                           job_title VARCHAR(150) NOT NULL,

                           currency VARCHAR(3) NOT NULL,

                           salary_amount NUMERIC(15, 2) NOT NULL,
                           reporting_salary NUMERIC(15, 2) NOT NULL,

                           created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                           updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

                           CONSTRAINT salary_amount_non_negative
                               CHECK (salary_amount >= 0),

                           CONSTRAINT reporting_salary_non_negative
                               CHECK (reporting_salary >= 0)
);

CREATE INDEX idx_employees_country
    ON employees(country);

CREATE INDEX idx_employees_department
    ON employees(department);