package com.example.KekaActionService.service.Update;

import com.example.KekaActionService.entity.Department;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.repository.DepartmentRepo;
import com.example.KekaActionService.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeUpdateService {

    @Autowired
    private DepartmentRepo departmentRepo;

    private final EmployeeRepo employeeRepo;

    public EmployeeUpdateService(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    // Update employee details
    public Employee updateEmployee(int id , Map<String, Object> updates){
        Employee employee = employeeRepo.findById(id).orElseThrow(()-> new RuntimeException("Not Found"));

        Department department;

        if (updates.containsKey("department")){

            department = departmentRepo.findByDepartmentName((String) updates.get("department")).orElseThrow(() -> new RuntimeException("Department not found"));
        } else {
            department = null;
        }

        if (employee != null){
            updates.forEach((key, value)->{
                switch (key){
                    case "firstName" : employee.setFirstName((String) value); break;
                    case "lastName" : employee.setLastName((String) value); break;
                    case "email" : employee.setEmail((String) value); break;
                    case "phone" : employee.setPhone((String) value); break;
                    case "designation" : employee.setDesignation((String) value); break;
                    case "department" : employee.setDepartment(department); break;
                    case "joinDate" : employee.setJoinDate((LocalDate) value); break;
                    case "active" : employee.setActive((Boolean) value); break;
                    case "easyDelete" : employee.setIsDeleted((Boolean) value); break;
                }
            });
            employee.setUpdatedAt(LocalDateTime.now());
            return employeeRepo.save(employee);
        }
        throw new RuntimeException("Not Found");
    }
}
