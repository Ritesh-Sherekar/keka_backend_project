package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeePostService {
    private final EmployeeRepo employeeRepo;

    public EmployeePostService(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    // Add Employee
    public Employee addEmployee(Employee employee){
        return employeeRepo.save(employee);
    }

    // Add List Of Employee
    public List<Employee> addListOfEmployee(List<Employee> employeeList){
        return employeeRepo.saveAll(employeeList);
    }
}
