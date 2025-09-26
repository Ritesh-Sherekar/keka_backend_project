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
@RequestMapping("/employees")
public class EmployeeFindController {
    @Autowired
    private EmployeeFindService employeeFindService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Employee>> findEmployee(){
        List<Employee> employee = employeeFindService.findEmployee();
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @GetMapping("/findByEmployeeId/{id}")
    public ResponseEntity<Employee> findByEmployeeID(@PathVariable int id){
        Employee employee = employeeFindService.findByEmployeeID(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
