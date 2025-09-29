package com.example.query_service.service;

import com.example.query_service.entity.Department;
import com.example.query_service.entity.Employee;
import com.example.query_service.repository.DepartmentRepository;
import com.example.query_service.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeFindService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAllEmployee() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeByEmployeeId(Long employeeId) {
        return employeeRepository.findEmployeeByEmployeeId(employeeId);
    }

    public List<Employee> findEmployeeByDepartmentName(String departmentName){
        Department department = departmentRepository.findByDepartmentName(departmentName).orElseThrow(() -> new RuntimeException("Invalid department id"));
        return employeeRepository.findEmployeeByDepartmentDepartmentId(department.getDepartmentId());
    }

}