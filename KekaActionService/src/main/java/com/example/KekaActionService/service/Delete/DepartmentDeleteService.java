package com.example.KekaActionService.service.Delete;

import com.example.KekaActionService.entity.Department;
import com.example.KekaActionService.exception.DepartmentNotFoundException;
import com.example.KekaActionService.repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentDeleteService {
    @Autowired
    private DepartmentRepo departmentRepo;

    // Delete Department By Dept ID
    public Department deleteDepartmentByDeptId(int deptId){
        Department department = departmentRepo.findById((long) deptId).orElseThrow(() -> new DepartmentNotFoundException("Department Not Found"));

        if (department != null){
            departmentRepo.deleteById((long) deptId);
        }
        return department;
    }

}
