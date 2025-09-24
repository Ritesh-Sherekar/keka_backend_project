package com.example.KekaActionService.controller.Update;

import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.service.Update.EmployeeUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ActionUpdateEmpService")
public class EmployeeUpdateController {
    private final EmployeeUpdateService employeeUpdateService;

    public EmployeeUpdateController(EmployeeUpdateService employeeUpdateService){
        this.employeeUpdateService = employeeUpdateService;
    }

    // Update Employee Details
    @PutMapping("/updateDetails/{id}")
    public ResponseEntity<Employee> updateDetails(@PathVariable int id, @RequestBody Map<String, Object> updates){
        Employee employee = employeeUpdateService.updateEmployee(id, updates);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
