package com.example.query_service.repository;

import com.example.query_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    Employee findByEmployeeID(int id);
}
