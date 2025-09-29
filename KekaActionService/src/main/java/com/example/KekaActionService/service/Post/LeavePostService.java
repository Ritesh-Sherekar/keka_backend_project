package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.LeaveDto;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.entity.Leave;
import com.example.KekaActionService.exception.InvalidLeaveException;
import com.example.KekaActionService.repository.EmployeeRepo;
import com.example.KekaActionService.repository.LeaveRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class LeavePostService {

    @Autowired
    private LeaveRepo leaveRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    public Leave addLeave(LeaveDto leaveDto) {

        Employee employee = employeeRepo.findByEmployeeID(leaveDto.getEmployeeID())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Employee approver = employee.getDepartment().getManager();

        LocalDate startDate = leaveDto.getStartDate();
        LocalDate endDate = leaveDto.getEndDate();

        if (startDate.isAfter(endDate)){
            throw new InvalidLeaveException("End date should be after start date");
        }

        double daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setApprover(approver);
        leave.setStartDate(startDate);
        leave.setEndDate(endDate);
        leave.setLeaveDays(daysBetween);
        leave.setReason(leaveDto.getReason());
        leave.setLeaveType(leaveDto.getLeaveType());

        log.info("Saving leave: {}", leave);
        return leaveRepo.save(leave);
    }
}
