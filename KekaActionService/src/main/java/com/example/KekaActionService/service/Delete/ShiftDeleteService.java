package com.example.KekaActionService.service.Delete;

import com.example.KekaActionService.dto.shiftDto.ShiftResponseDto;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.entity.Shift;
import com.example.KekaActionService.exception.ShiftAssignToEmployeeException;
import com.example.KekaActionService.exception.ShiftNotPresentException;
import com.example.KekaActionService.repository.EmployeeRepo;
import com.example.KekaActionService.repository.ShiftRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ShiftDeleteService {
    @Autowired
    private ShiftRepo shiftRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    // Delete Shift By Shift ID
    public ShiftResponseDto deleteShift(long shiftID){
        Shift shift = shiftRepo.findById((int) shiftID).orElseThrow(() -> new ShiftNotPresentException("Invalid Shift"));

        Optional<Employee> employeeByShiftShiftId = employeeRepo.findEmployeeByShift_ShiftId(shiftID);

        if (employeeByShiftShiftId.isEmpty()){
            shiftRepo.deleteById((int) shiftID);
            return new ShiftResponseDto("Shift Deleted Successfully", shift);
        }else {
            throw new ShiftAssignToEmployeeException("Cannot delete shift. Employees are assigned to it.");
        }
    }
}
