package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.LeaveDto;
import com.example.KekaActionService.entity.*;
import com.example.KekaActionService.enums.LeaveStatus;
import com.example.KekaActionService.exception.InsufficientLeavesException;
import com.example.KekaActionService.exception.InsufficientPermissionsException;
import com.example.KekaActionService.exception.InvalidBandException;
import com.example.KekaActionService.exception.InvalidLeaveException;
import com.example.KekaActionService.repository.UsedLeavesRepo;
import com.example.KekaActionService.repository.BandRepo;
import com.example.KekaActionService.repository.EmployeeRepo;
import com.example.KekaActionService.repository.LeaveRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
public class LeavePostService {

    @Autowired
    private LeaveRepo leaveRepo;

    @Autowired
    private UsedLeavesRepo usedLeavesRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private BandRepo bandRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public Leave addLeave(LeaveDto leaveDto) {

        long employeeID = leaveDto.getEmployeeID();
        System.out.println(employeeID);
        Employee employee = employeeRepo.findByEmployeeID(employeeID)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Employee approver = employee.getDepartment() != null
                ? employee.getDepartment().getManager()
                : null;

        if (leaveDto.getLeaveDays() == null || leaveDto.getLeaveDays().isEmpty()) {
            throw new InvalidLeaveException("At least one leave day is required");
        }

        Band availableBandLeaves = bandRepo.findByBands(employee.getBand().getBands()).orElseThrow(() -> new InvalidBandException("Invalid Band"));

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

        float totalAppliedLeaves = (float) leaveDto.getLeaveDays().stream().mapToDouble(leaveDayDto -> leaveDayDto.getHalf().getValue()).sum();

        leave.setLeaveDays(leaveDays);

        UsedLeaves usedLeaves = usedLeavesRepo.findByEmployeeEmployeeID(employee.getEmployeeID()).orElseGet(() -> {
            UsedLeaves ul = new UsedLeaves();
            ul.setEmployee(employee);
            ul.setUsedPaidLeaves(0f);
            ul.setUsedSickLeaves(0f);
            ul.setUsedCasualLeaves(0f);
            ul.setUsedUnpaidLeaves(0f);
            ul.setUsedParentalLeaves(0f);
            return usedLeavesRepo.save(ul);
        });

        switch (leaveDto.getLeaveType()) {
            case SICK:
                applyLeave(
                        availableBandLeaves.getSickLeaves(),
                        usedLeaves.getUsedSickLeaves(),
                        totalAppliedLeaves,
                        usedLeaves::setUsedSickLeaves,
                        usedLeaves,
                        "sick"
                );
                break;

            case CASUAL:
                applyLeave(
                        availableBandLeaves.getCasualLeaves(),
                        usedLeaves.getUsedCasualLeaves(),
                        totalAppliedLeaves,
                        usedLeaves::setUsedCasualLeaves,
                        usedLeaves,
                        "casual"
                );
                break;

            case PAID:
                applyLeave(
                        availableBandLeaves.getPaidLeaves(),
                        usedLeaves.getUsedPaidLeaves(),
                        totalAppliedLeaves,
                        usedLeaves::setUsedPaidLeaves,
                        usedLeaves,
                        "paid"
                );
                break;

            case UNPAID:
                usedLeaves.setUsedUnpaidLeaves(
                        usedLeaves.getUsedUnpaidLeaves() + totalAppliedLeaves
                );
                break;

            case MATERNITY:
            case PATERNITY:
                applyLeave(
                        availableBandLeaves.getParentalLeaves(),
                        usedLeaves.getUsedParentalLeaves(),
                        totalAppliedLeaves,
                        usedLeaves::setUsedParentalLeaves,
                        usedLeaves,
                        leaveDto.getLeaveType().name().toLowerCase()
                );
                break;

            default:
                throw new InvalidLeaveException("Unknown leave type: " + leaveDto.getLeaveType());
        }

//        usedLeavesRepo.save(usedLeaves);

        log.info("Saving leave: {}", leave);
        return leaveRepo.save(leave);
    }

    private void applyLeave(
            float available,
            float used,
            float totalAppliedLeaves,
            Consumer<Float> setUsedLeave,
            UsedLeaves usedLeaves,
            String leaveTypeName
    ) {
        if (used < available) {
            if (used + totalAppliedLeaves > available) {
                float excess = (used + totalAppliedLeaves) - available;
                used = available;
                usedLeaves.setUsedUnpaidLeaves(usedLeaves.getUsedUnpaidLeaves() + excess);
            } else {
                used += totalAppliedLeaves;
            }
            setUsedLeave.accept(used);
        } else {
            throw new InsufficientLeavesException("Not enough " + leaveTypeName + " leaves available");
        }
    }

    public LeaveDto approveLeave(long leaveId) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Long currentEmployeesEmployeeID = employeeRepo.findByEmployeeEmail(userName).orElseThrow(() -> new UsernameNotFoundException(userName + "Is not registered with us")).getEmployeeID();

        Leave leave = leaveRepo.findById(leaveId).orElseThrow(() -> new InvalidLeaveException("Leave id not valid"));
        Long managerEmployeeID = leave.getEmployee().getDepartment().getManager().getEmployeeID();

        Leave savedLeave;
        if (currentEmployeesEmployeeID.equals(managerEmployeeID)) {

            LeaveStatus status = leave.getStatus();
            leave.setStatus(LeaveStatus.APPROVED);
            savedLeave = leaveRepo.save(leave);
        }else {
            throw new InsufficientPermissionsException("You don't have authority to approve leaves of this employee");
        }

        return objectMapper.convertValue(savedLeave, LeaveDto.class);
    }

    public LeaveDto rejectLeave(long leaveId) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Long currentEmployeesEmployeeID = employeeRepo.findByEmployeeEmail(userName).orElseThrow(() -> new UsernameNotFoundException(userName + "Is not registered with us")).getEmployeeID();

        Leave leave = leaveRepo.findById(leaveId).orElseThrow(() -> new InvalidLeaveException("Leave id not valid"));
        Long managerEmployeeID = leave.getEmployee().getDepartment().getManager().getEmployeeID();

        Leave savedLeave;
        if (currentEmployeesEmployeeID.equals(managerEmployeeID)) {

            LeaveStatus status = leave.getStatus();
            leave.setStatus(LeaveStatus.REJECTED);
            savedLeave = leaveRepo.save(leave);
        }else {
            throw new InsufficientPermissionsException("You don't have authority to reject leaves of this employee");
        }

        return objectMapper.convertValue(savedLeave, LeaveDto.class);
    }
}
