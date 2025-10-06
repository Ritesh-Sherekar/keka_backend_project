package com.example.KekaActionService.repository;

import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmployeeID(long employeeID);

    Optional<Employee> findEmployeeByShift_ShiftId(Long shiftId);

    Optional<Employee> findByEmployeeEmail(String userName);
}
