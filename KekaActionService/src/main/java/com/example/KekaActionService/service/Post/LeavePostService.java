package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.LeaveDayDto;
import com.example.KekaActionService.dto.LeaveDto;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.entity.Leave;
import com.example.KekaActionService.entity.LeaveDay;
import com.example.KekaActionService.exception.InvalidLeaveException;
import com.example.KekaActionService.repository.EmployeeRepo;
import com.example.KekaActionService.repository.LeaveRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        Employee approver = employee.getDepartment() != null
                ? employee.getDepartment().getManager()
                : null;

        if (leaveDto.getLeaveDays() == null || leaveDto.getLeaveDays().isEmpty()) {
            throw new InvalidLeaveException("At least one leave day is required");
        }

        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setApprover(approver);
        leave.setReason(leaveDto.getReason());
        leave.setLeaveType(leaveDto.getLeaveType());

        List<LeaveDay> leaveDays = leaveDto.getLeaveDays().stream()
                .map(dtoDay -> {
                    LeaveDay day = new LeaveDay();
                    day.setLeave(leave);
                    day.setLeaveDate(dtoDay.getLeaveDate());
                    day.setHalf(dtoDay.getHalf());
                    return day;
                }).toList();

        leave.setLeaveDays(leaveDays);

        log.info("Saving leave: {}", leave);
        return leaveRepo.save(leave);
    }
}
