package com.acme.compensation_api.repositories;

import com.acme.compensation_api.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
