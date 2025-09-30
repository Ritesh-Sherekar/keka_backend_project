package com.example.query_service.controller;

import com.example.query_service.entity.Department;
import com.example.query_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @GetMapping("/getAllDepartment")
    public ResponseEntity<List<Department>> getAllDepartment(){
        List<Department> department = departmentService.getAllDepartment();
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
    @GetMapping("/getDepartmentByDepartmentName/{departmentName}")
    public ResponseEntity<Optional<Department>> getDepartmentByDepartmentName(@PathVariable String departmentName){
        Optional<Department> department = departmentService.getDepartmentByDepartmentName(departmentName);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }}


