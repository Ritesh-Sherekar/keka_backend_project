package com.example.KekaActionService.service.Update;

import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class EmployeeUpdateService {
    private final EmployeeRepo employeeRepo;

    public EmployeeUpdateService(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    // Update employee details
    public Employee updateEmployee(int id , Map<String, Object> updates){
        Employee employee = employeeRepo.findById(id).orElseThrow(()-> new RuntimeException("Not Found"));

        if (employee != null){
            updates.forEach((key, value)->{
                switch (key){
                    case "firstName" : employee.setFirstName((String) value); break;
                    case "lastName" : employee.setLastName((String) value); break;
                    case "email" : employee.setEmail((String) value); break;
                    case "phone" : employee.setPhone((String) value); break;
                    case "designation" : employee.setDesignation((String) value); break;
                    case "department" : employee.setDepartment((String) value); break;
                    case "joinDate" : employee.setJoinDate((LocalDate) value); break;
                    case "active" : employee.setActive((Boolean) value); break;
                    case "easyDelete" : employee.setEasyDelete((Boolean) value); break;
                }
            });
            employee.setUpdatedAt(LocalDateTime.now());
            Employee save = employeeRepo.save(employee);
            return save;
        }
        throw new RuntimeException("Not Found");
    }
}
