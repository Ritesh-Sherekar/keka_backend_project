package com.example.KekaActionService.service.Delete;

import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.exception.EmployeeIdNotFoundException;
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
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new EmployeeIdNotFoundException("Employee Not Found"));

        if (employee != null){
            employee.setIsDeleted(true);
            return employeeRepo.save(employee);
        }
        throw new EmployeeIdNotFoundException("Employee Not Found");
    }
}
