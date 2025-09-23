package com.example.KekaActionService.service;

import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    // Add Employee
    public Employee addEmployee(Employee employee){
        return employeeRepo.save(employee);
    }

}
