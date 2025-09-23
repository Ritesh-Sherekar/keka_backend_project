package com.example.KekaActionService.service.Delete;

import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDeleteService {
    private final EmployeeRepo employeeRepo;

    public EmployeeDeleteService(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    // Delete Employee
    public Employee deleteEmployee(int id){
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));

        if (employee != null){
            employee.setEasyDelete(true);
            return employeeRepo.save(employee);
        }
        throw new RuntimeException("Not Found");
    }
}
