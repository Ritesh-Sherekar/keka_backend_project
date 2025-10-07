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
    public Employee updateEmployee(long empId, Map<String, Object> updates) {
        Employee employee = employeeRepo.findByEmployeeID(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (updates.containsKey("band")) {
            String bandValue = updates.get("band").toString().toUpperCase();

            Bands bandEnum = Bands.valueOf(bandValue);
            band = bandRepo.findByBands(bandEnum)
                    .orElseThrow(() -> new BandNotFoundException("Band not found in database"));
        } else {
            band = null;
        }

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
                    break;
                case "band":
                    employee.setBand(band);
                    break;
                }
            }
        });

        employee.setUpdatedAt(LocalDateTime.now());
        return employeeRepo.save(employee);
    }

}
