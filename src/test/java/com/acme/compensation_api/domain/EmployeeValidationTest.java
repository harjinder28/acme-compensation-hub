package com.acme.compensation_api.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Test
    void shouldAcceptValidEmployee() {
        Employee employee = validEmployee();

        Set<ConstraintViolation<Employee>> violations =
                validator.validate(employee);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldRejectBlankEmployeeCode() {
        Employee employee = validEmployee();
        employee.setEmployeeCode("");

        Set<ConstraintViolation<Employee>> violations =
                validator.validate(employee);

        assertThat(violations)
                .extracting(ConstraintViolation::getPropertyPath)
                .anyMatch(path -> path.toString().equals("employeeCode"));
    }

    @Test
    void shouldRejectInvalidEmail() {
        Employee employee = validEmployee();
        employee.setEmail("not-an-email");

        Set<ConstraintViolation<Employee>> violations =
                validator.validate(employee);

        assertThat(violations)
                .extracting(ConstraintViolation::getPropertyPath)
                .anyMatch(path -> path.toString().equals("email"));
    }

    @Test
    void shouldRejectNegativeSalary() {
        Employee employee = validEmployee();
        employee.setSalaryAmount(new BigDecimal("-1"));

        Set<ConstraintViolation<Employee>> violations =
                validator.validate(employee);

        assertThat(violations)
                .extracting(ConstraintViolation::getPropertyPath)
                .anyMatch(path -> path.toString().equals("salaryAmount"));
    }

    @Test
    void shouldRejectNullSalary() {
        Employee employee = validEmployee();
        employee.setSalaryAmount(null);

        Set<ConstraintViolation<Employee>> violations =
                validator.validate(employee);

        assertThat(violations)
                .extracting(ConstraintViolation::getPropertyPath)
                .anyMatch(path -> path.toString().equals("salaryAmount"));
    }

    private Employee validEmployee() {
        return Employee.builder()
                .employeeCode("EMP-00001")
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@example.com")
                .country("US")
                .department("Engineering")
                .jobTitle("Software Engineer")
                .currency("USD")
                .salaryAmount(new BigDecimal("95000.00"))
                .reportingSalary(new BigDecimal("95000.00"))
                .build();
    }
}