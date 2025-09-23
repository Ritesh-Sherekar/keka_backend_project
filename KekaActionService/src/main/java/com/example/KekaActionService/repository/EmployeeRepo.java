package com.example.KekaActionService.repository;

import com.example.KekaActionService.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
}
