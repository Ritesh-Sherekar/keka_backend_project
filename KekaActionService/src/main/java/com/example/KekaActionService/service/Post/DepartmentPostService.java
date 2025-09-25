package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.departmentDto.DepartmentRequestDto;
import com.example.KekaActionService.entity.Department;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.repository.DepartmentRepo;
import com.example.KekaActionService.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentPostService {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    // Add Department
    public Department addDepartment(DepartmentRequestDto departmentDto) {
        Department department = new Department();
        department.setDepartmentName(departmentDto.getDepartmentName());

        if (departmentDto.getEmployeeID() != null) {
            // Only set manager if employeeID is provided
            Employee manager = employeeRepo.findByEmployeeID(Math.toIntExact(departmentDto.getEmployeeID()));
            department.setManager(manager);
        } else {
            department.setManager(null);
        }
        return departmentRepo.save(department);
    }

    // Add List OF Department
    public List<Department> addListOfDepartment(List<DepartmentRequestDto> dto){
        List<Department> departments = new ArrayList<>();

        for (DepartmentRequestDto requestDto : dto){
            Department department = new Department();
            department.setDepartmentName(requestDto.getDepartmentName());

            if (requestDto.getEmployeeID() != null){
                Employee manager = employeeRepo.findByEmployeeID(Math.toIntExact(requestDto.getEmployeeID()));
                department.setManager(manager);
            }else {
                department.setManager(null);
            }
            departments.add(department);
        }
        return departmentRepo.saveAll(departments);
    }

}
