package com.example.query_service.service;

import com.example.query_service.entity.Employee;
import com.example.query_service.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeFindService {
    @Autowired
    private EmployeeRepo employeeRepo;

    public List<Employee> findEmployee(){
        return employeeRepo.findAll();
    }
    public Employee findByEmployeeID(int id){
        return employeeRepo.findByEmployeeID(id);
    }
}