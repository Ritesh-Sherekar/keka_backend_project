package com.example.query_service.repository;

import com.example.query_service.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentName(String departmentName);
}
