package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.LeaveDto;
import com.example.KekaActionService.entity.*;
import com.example.KekaActionService.exception.EmployeeIdNotFoundException;
import com.example.KekaActionService.exception.InsufficientLeavesException;
import com.example.KekaActionService.exception.InvalidLeaveException;
import com.example.KekaActionService.repository.AvailableLeavesRepo;
import com.example.KekaActionService.repository.BandRepo;
import com.example.KekaActionService.repository.EmployeeRepo;
import com.example.KekaActionService.repository.LeaveRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LeavePostService {

    @Autowired
    private LeaveRepo leaveRepo;

    @Autowired
    private AvailableLeavesRepo availableLeavesRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private BandRepo bandRepo;

    public Leave addLeave(LeaveDto leaveDto) {

        Employee employee = employeeRepo.findByEmployeeID(leaveDto.getEmployeeID())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Employee approver = employee.getDepartment() != null
                ? employee.getDepartment().getManager()
                : null;

        if (leaveDto.getLeaveDays() == null || leaveDto.getLeaveDays().isEmpty()) {
            throw new InvalidLeaveException("At least one leave day is required");
        }

        Band availableBandLeaves = bandRepo.findByBands(employee.getBand().getBands()).orElseThrow(() -> new InvalidBandException("Invalid Band"));

        float totalLeaves = 0;

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

        totalLeaves =(float) leaveDto.getLeaveDays().stream().mapToDouble(leaveDayDto -> leaveDayDto.getHalf().getValue()).sum();

        leave.setLeaveDays(leaveDays);

        AvailableLeaves availableLeaves = availableLeavesRepo.findByEmployeeEmployeeID(employee.getEmployeeID()).orElseThrow(() -> new EmployeeIdNotFoundException("Invalid EmployeeId"));

        switch (leaveDto.getLeaveType()) {
            case SICK:
                if (availableLeaves.getAvailableSickLeaves() < leaveDto.getTotalLeaveDays()) {
                    throw new InsufficientLeavesException("Not enough sick leaves available");
                }
                availableLeaves.setAvailableSickLeaves(availableLeaves.getAvailableSickLeaves() - leaveDto.getTotalLeaveDays());
                break;

            case CASUAL:
                if (availableLeaves.getAvailableCasualLeaves() < leaveDto.getTotalLeaveDays()) {
                    throw new InsufficientLeavesException("Not enough casual leaves available");
                }
                availableLeaves.setAvailableCasualLeaves(availableLeaves.getAvailableCasualLeaves() - totalLeaves);
                break;

            case PAID:
                if (availableLeaves.getAvailablePaidLeaves() < leaveDto.getTotalLeaveDays()) {
                    throw new InsufficientLeavesException("Not enough paid leaves available");
                }
                availableLeaves.setAvailablePaidLeaves(availableLeaves.getAvailablePaidLeaves() - leaveDto.getTotalLeaveDays());
                break;

            case UNPAID:
                break;

            case MATERNITY:
                if (availableLeaves.getAvailableParentalLeaves() < leaveDto.getTotalLeaveDays()) {
                    throw new InsufficientLeavesException("Not enough maternity leaves available");
                }
                availableLeaves.setAvailableParentalLeaves(availableLeaves.getAvailableParentalLeaves() - leaveDto.getTotalLeaveDays());
                break;

            case PATERNITY:
                if (availableLeaves.getAvailableParentalLeaves() < leaveDto.getTotalLeaveDays()) {
                    throw new InsufficientLeavesException("Not enough paternity leaves available");
                }
                availableLeaves.setAvailableParentalLeaves(availableLeaves.getAvailableParentalLeaves() - leaveDto.getTotalLeaveDays());
                break;

            default:
                throw new InvalidLeaveException("Unknown leave type: " + leaveDto.getLeaveType());
        }

        log.info("Saving leave: {}", leave);
        return leaveRepo.save(leave);
    }
}
