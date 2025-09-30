package com.example.query_service.service;

import com.example.query_service.entity.Department;
import com.example.query_service.repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    public List<Department> getAllDepartment(){
        return departmentRepo.findAll();
    }
    public Optional<Department> getDepartmentByDepartmentName(String departmentName){
        return departmentRepo.findByDepartmentName(departmentName);
    }
}
