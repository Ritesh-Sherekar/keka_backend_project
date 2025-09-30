package com.example.query_service.controller;

import com.example.query_service.entity.Department;
import com.example.query_service.entity.Employee;
import com.example.query_service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/getAllEmployee")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        List<Employee> employee = employeeService.getAllEmployee();
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @GetMapping("/getEmployeeByEmployeeID/{employeeID}")
    public ResponseEntity<Employee> getEmployeeByEmployeeID(@PathVariable int employeeID) {
        Employee employee = employeeService.getEmployeeByEmployeeID(employeeID);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @GetMapping("/getEmployeeByDepartmentName/{departmentName}")
    public ResponseEntity<List<Employee>> getEmployeeByDepartmentName(@PathVariable String departmentName){
        List<Employee> employee = employeeService.getEmployeeByDepartmentName(departmentName);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

}
