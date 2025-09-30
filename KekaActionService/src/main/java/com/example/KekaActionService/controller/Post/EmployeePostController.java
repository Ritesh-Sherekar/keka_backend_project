package com.example.KekaActionService.controller.Post;

import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.service.Post.EmployeePostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ActionPostEmpService")
public class EmployeePostController {
    private final EmployeePostService employeePostService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeePostController.class);


    public EmployeePostController(EmployeePostService employeeService){
        this.employeePostService = employeeService;
    }

    // Add Employee
    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        logger.info(String.valueOf(employee));
        Employee addEmployee = employeePostService.addEmployee(employee);
        logger.info(String.valueOf(addEmployee));
        return new ResponseEntity<>(addEmployee, HttpStatus.OK);
    }

    // Add List Of Employee
    @PostMapping("/addListOfEmployee")
    public ResponseEntity<List<Employee>> addListOfEmployee(@RequestBody List<Employee> employeeList){
        logger.info(String.valueOf(employeeList));
        List<Employee> employees = employeePostService.addListOfEmployee(employeeList);
        logger.info(String.valueOf(employees));
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
