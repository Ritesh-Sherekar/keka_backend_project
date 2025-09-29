package com.example.query_service.service;

import com.example.query_service.entity.Department;
import com.example.query_service.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentFindService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> findAllDepartment(){
        return departmentRepository.findAll();
    }

}
