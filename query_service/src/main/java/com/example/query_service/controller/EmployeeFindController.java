package com.example.query_service.controller;

import com.example.query_service.entity.Employee;
import com.example.query_service.service.EmployeeFindService;
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
public class EmployeeFindController {

    @Autowired
    private EmployeeFindService employeeFindService;

    @GetMapping("/findAllEmployee")
    public ResponseEntity<List<Employee>> findAllEmployee(){
        List<Employee> employee = employeeFindService.findAllEmployee();
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("/findEmployeeByEmployeeId/{employeeId}")
    public ResponseEntity<Employee> findEmployeeByEmployeeId(@PathVariable Long employeeId){
        Employee employee = employeeFindService.findEmployeeByEmployeeId(employeeId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("/findEmployeeByDepartmentName/{departmentName}")
    public ResponseEntity<List<Employee>> findEmployeeByDepartmentName(@PathVariable String departmentName){
        List<Employee> employee = employeeFindService.findEmployeeByDepartmentName(departmentName);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

}
