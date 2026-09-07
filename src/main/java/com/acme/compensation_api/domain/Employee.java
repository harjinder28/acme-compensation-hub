package com.acme.compensation_api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "employees")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "employee_code", nullable = false, unique = true, length = 50)
    private String employeeCode;

    @NotBlank
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 2)
    private String country;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String department;

    @NotBlank
    @Column(name = "job_title", nullable = false, length = 150)
    private String jobTitle;

    @NotBlank
    @Column(nullable = false, length = 3)
    private String currency;

    @NotNull
    @PositiveOrZero
    @Column(name = "salary_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal salaryAmount;

    @NotNull
    @PositiveOrZero
    @Column(name = "reporting_salary", nullable = false, precision = 15, scale = 2)
    private BigDecimal reportingSalary;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}