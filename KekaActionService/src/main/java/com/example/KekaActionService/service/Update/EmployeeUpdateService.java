package com.example.KekaActionService.service.Update;

import com.example.KekaActionService.entity.Department;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.entity.Shift;
import com.example.KekaActionService.exception.DepartmentNotFoundException;
import com.example.KekaActionService.exception.ShiftNotPresentException;
import com.example.KekaActionService.repository.DepartmentRepo;
import com.example.KekaActionService.repository.EmployeeRepo;
import com.example.KekaActionService.repository.ShiftRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeUpdateService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private ShiftRepo shiftRepo;

    private final EmployeeRepo employeeRepo;

    public EmployeeUpdateService(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    // Update employee details
    public Employee updateEmployee(long empId, Map<String, Object> updates) {
        Employee employee = employeeRepo.findByEmployeeID(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        updates.forEach((key, value) -> {
            if (value == null) return;

            switch (key) {
                case "firstName" -> employee.setFirstName((String) value);
                case "lastName" -> employee.setLastName((String) value);
                case "email" -> employee.setEmail((String) value);
                case "phone" -> employee.setPhone((String) value);
                case "designation" -> employee.setDesignation((String) value);

                case "department" -> {
                    Department department = departmentRepo.findByDepartmentName((String) value)
                            .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
                    employee.setDepartment(department);
                }

                case "joinDate" -> {
                    if (value instanceof String s) {
                        employee.setJoinDate(LocalDate.parse(s));
                    } else if (value instanceof LocalDate d) {
                        employee.setJoinDate(d);
                    }
                }

                case "active" -> employee.setActive((Boolean) value);
                case "isDeleted" -> employee.setIsDeleted((Boolean) value);

                case "shift" -> {
                    Shift shift = shiftRepo.findById((Integer) value)
                            .orElseThrow(() -> new ShiftNotPresentException("Shift Not Found"));
                    employee.setShift(shift);
                }
            }
        });

        employee.setUpdatedAt(LocalDateTime.now());
        return employeeRepo.save(employee);
    }

}
