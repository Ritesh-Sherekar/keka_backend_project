package com.example.KekaActionService.controller.Post;

import com.example.KekaActionService.dto.departmentDto.DepartmentRequestDto;
import com.example.KekaActionService.entity.Department;
import com.example.KekaActionService.service.Post.DepartmentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ActionPostDeptService")
public class DepartmentPostController {
    @Autowired
    private DepartmentPostService departmentPostService;

    // Add Department
    @PostMapping("/addDepartment")
    public ResponseEntity<Department> addDepartment(@RequestBody DepartmentRequestDto department){
        Department addDepartment = departmentPostService.addDepartment(department);
        return new ResponseEntity<>(addDepartment, HttpStatus.OK);
    }

    // Add List OF Department
    @PostMapping("/addListOfDepartment")
    public ResponseEntity<List<Department>> addListOfDepartment(@RequestBody List<DepartmentRequestDto> dtos){
        List<Department> departments = departmentPostService.addListOfDepartment(dtos);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }
}
