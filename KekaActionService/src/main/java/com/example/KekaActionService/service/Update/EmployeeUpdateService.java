package com.example.KekaActionService.service.Update;

import com.example.KekaActionService.entity.Band;
import com.example.KekaActionService.entity.Department;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.entity.Shift;
import com.example.KekaActionService.enums.Bands;
import com.example.KekaActionService.exception.BandNotFoundException;
import com.example.KekaActionService.exception.DepartmentNotFoundException;
import com.example.KekaActionService.exception.ShiftNotPresentException;
import com.example.KekaActionService.repository.BandRepo;
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

    @Autowired
    private BandRepo bandRepo;

    private final EmployeeRepo employeeRepo;

    public EmployeeUpdateService(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    // Update employee details
    public Employee updateEmployee(long empId , Map<String, Object> updates){
        Optional<Employee> byEmployeeID = employeeRepo.findByEmployeeID(empId);
        Employee employee = byEmployeeID.get();

        Department department;
        Shift shift;
        Band band;

        if (updates.containsKey("department")){
            department = departmentRepo.findByDepartmentName((String) updates.get("department")).orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
        } else {
            department = null;
        }

        if (updates.containsKey("shift")) {
            shift =  shiftRepo.findById((Integer) updates.get("shift")).orElseThrow(()-> new ShiftNotPresentException("Shift Not Found"));
        } else {
            shift = null;
        }

        if (updates.containsKey("band")) {
            String bandValue = updates.get("band").toString().toUpperCase();

            Bands bandEnum = Bands.valueOf(bandValue);
            band = bandRepo.findByBands(bandEnum)
                    .orElseThrow(() -> new BandNotFoundException("Band not found in database"));
        } else {
            band = null;
        }

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    employee.setFirstName((String) value);
                    break;
                case "lastName":
                    employee.setLastName((String) value);
                    break;
                case "email":
                    employee.setEmail((String) value);
                    break;
                case "phone":
                    employee.setPhone((String) value);
                    break;
                case "designation":
                    employee.setDesignation((String) value);
                    break;
                case "department":
                    employee.setDepartment(department);
                    break;
                case "joinDate":
                    employee.setJoinDate((LocalDate) value);
                    break;
                case "active":
                    employee.setActive((Boolean) value);
                    break;
                case "easyDelete":
                    employee.setIsDeleted((Boolean) value);
                    break;
                case "shift":
                    employee.setShift(shift);
                    break;
                case "band":
                    employee.setBand(band);
                    break;
            }
        });
        employee.setUpdatedAt(LocalDateTime.now());
        return employeeRepo.save(employee);
    }
}
