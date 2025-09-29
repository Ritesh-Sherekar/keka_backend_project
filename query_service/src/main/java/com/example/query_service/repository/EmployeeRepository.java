package com.example.query_service.repository;

import com.example.query_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findEmployeeByEmployeeId(Long employeeId);

    List<Employee> findEmployeeByDepartmentDepartmentId(long departmentId);

}
