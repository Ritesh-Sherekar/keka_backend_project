package com.example.KekaActionService.controller.Delete;

import com.example.KekaActionService.entity.Department;
import com.example.KekaActionService.service.Delete.DepartmentDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ActionDeleteDeptService")
public class DepartmentDeleteController {
    @Autowired
    private DepartmentDeleteService departmentDeleteService;

    // Delete Department By Dept ID
    @DeleteMapping("/deleteDepartmentByDeptID")
    public ResponseEntity<Department> deleteDepartmentByDeptID(@RequestParam int deptId){
        Department department = departmentDeleteService.deleteDepartmentByDeptId(deptId);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
}
