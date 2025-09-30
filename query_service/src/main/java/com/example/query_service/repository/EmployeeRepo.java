package com.example.query_service.repository;

import com.example.query_service.entity.Department;
import com.example.query_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    Employee findByEmployeeID(int id);
    List<Employee> findEmployeeByDepartmentDepartmentId(long departmentId);
}
