package com.example.query_service.controller;

import com.example.query_service.entity.Department;
import com.example.query_service.service.DepartmentFindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentFindController {

    @Autowired
    private DepartmentFindService departmentFindService;

    @GetMapping("/findAllDepartment")
    public ResponseEntity<List<Department>> findAllDepartment(){
        List<Department> department = departmentFindService.findAllDepartment();
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

}
