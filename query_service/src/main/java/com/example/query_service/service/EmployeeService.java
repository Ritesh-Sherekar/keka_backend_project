package com.example.query_service.service;

import com.example.query_service.entity.Department;
import com.example.query_service.entity.Employee;
import com.example.query_service.repository.DepartmentRepo;
import com.example.query_service.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    public List<Employee> getAllEmployee() {
        return employeeRepo.findAll();
    }

    public Employee getEmployeeByEmployeeId(int employeeID) {
        return employeeRepo.findByEmployeeID(employeeID);
    }

    public List<Employee> getEmployeeByDepartmentName(String departmentName){
        Department department = departmentRepo.findByDepartmentName(departmentName).orElseThrow(() -> new RuntimeException("Invalid department id"));
        return employeeRepo.findEmployeeByDepartmentDepartmentId(department.getDepartmentId());
    }

}