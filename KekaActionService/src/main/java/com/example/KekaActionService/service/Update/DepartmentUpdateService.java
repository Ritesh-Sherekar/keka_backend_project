package com.example.KekaActionService.service.Update;

import com.example.KekaActionService.entity.Department;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.repository.DepartmentRepo;
import com.example.KekaActionService.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentUpdateService {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    // Update Manager By DeptID
    public Department updateManagerByDeptId(int deptId, int managerId){
        Department department = departmentRepo.findById((long) deptId).orElseThrow(() -> new RuntimeException("Not Found"));

        Employee byEmployeeID = employeeRepo.findByEmployeeID(managerId);
        if (department != null){
            department.setManager(byEmployeeID);
            return departmentRepo.save(department);
        }
         throw new RuntimeException("Not Found");
    }
}
