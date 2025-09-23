package com.example.KekaActionService.controller.Delete;

import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.service.Delete.EmployeeDeleteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ActionDeleteService")
public class EmployeeDeleteController {
    private final EmployeeDeleteService employeeDeleteService;

    public EmployeeDeleteController(EmployeeDeleteService employeeDeleteService){
        this.employeeDeleteService = employeeDeleteService;
    }

    // Delete Employee
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable int id){
        Employee employee = employeeDeleteService.deleteEmployee(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
