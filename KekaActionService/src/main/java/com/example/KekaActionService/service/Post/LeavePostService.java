package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.EmailLeaveDto;
import com.example.KekaActionService.dto.LeaveDto;
import com.example.KekaActionService.entity.*;
import com.example.KekaActionService.enums.LeaveStatus;
import com.example.KekaActionService.exception.InsufficientLeavesException;
import com.example.KekaActionService.exception.InsufficientPermissionsException;
import com.example.KekaActionService.exception.InvalidBandException;
import com.example.KekaActionService.exception.InvalidLeaveException;
import com.example.KekaActionService.mapper.LeaveMapper;
import com.example.KekaActionService.repository.UsedLeavesRepo;
import com.example.KekaActionService.repository.BandRepo;
import com.example.KekaActionService.repository.EmployeeRepo;
import com.example.KekaActionService.repository.LeaveRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private EmailPostService emailPostService;

    @Transactional
    public Leave addLeave(LeaveDto leaveDto) {

        Long employeeID = leaveDto.getEmployeeID();
        String employeeIdInContext = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!employeeIdInContext.equals(employeeID.toString())){
            throw new InsufficientPermissionsException("You cant apply leave for another employee");
        }

        System.out.println(employeeID);
        Employee employee = employeeRepo.findByEmployeeID(employeeID)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        System.out.println("applicant : " + employee);

        Employee approver = employee.getDepartment() != null
                ? employee.getDepartment().getManager()
                : null;

        System.out.println("approver : " + approver);
        if (leaveDto.getLeaveDays() == null || leaveDto.getLeaveDays().isEmpty()) {
            throw new InvalidLeaveException("At least one leave day is required");
        }

        System.out.println("after if");

        Band availableBandLeaves = bandRepo.findByBands(employee.getBand().getBands()).orElseThrow(() -> new InvalidBandException("Invalid Band"));

        System.out.println("Band : " + availableBandLeaves);

        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setApprover(approver);
        leave.setReason(leaveDto.getReason());
        leave.setLeaveType(leaveDto.getLeaveType());
        System.out.println("leaves1 : " + leave);

        List<LeaveDay> leaveDays = leaveDto.getLeaveDays().stream()
                .map(dtoDay -> {
                    LeaveDay day = new LeaveDay();
                    day.setLeave(leave);
                    day.setLeaveDate(dtoDay.getLeaveDate());
                    day.setHalf(dtoDay.getHalf());
                    return day;
                }).toList();

        float totalAppliedLeaveDays = (float) leaveDto.getLeaveDays().stream().mapToDouble(leaveDayDto -> leaveDayDto.getHalf().getValue()).sum();

        leave.setLeaveDays(leaveDays);
        leave.setLeaveDaysCount(totalAppliedLeaveDays);

        System.out.println("leaves2 : " + leave);
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

        System.out.println("used : " + usedLeaves);

        switch (leaveDto.getLeaveType()) {
            case SICK:
                applyLeave(
                        availableBandLeaves.getSickLeaves(),
                        usedLeaves.getUsedSickLeaves(),
                        totalAppliedLeaveDays,
                        usedLeaves::setUsedSickLeaves,
                        usedLeaves,
                        "sick"
                );
                break;

            case CASUAL:
                applyLeave(
                        availableBandLeaves.getCasualLeaves(),
                        usedLeaves.getUsedCasualLeaves(),
                        totalAppliedLeaveDays,
                        usedLeaves::setUsedCasualLeaves,
                        usedLeaves,
                        "casual"
                );
                break;

            case PAID:
                applyLeave(
                        availableBandLeaves.getPaidLeaves(),
                        usedLeaves.getUsedPaidLeaves(),
                        totalAppliedLeaveDays,
                        usedLeaves::setUsedPaidLeaves,
                        usedLeaves,
                        "paid"
                );
                break;

            case UNPAID:
                usedLeaves.setUsedUnpaidLeaves(
                        usedLeaves.getUsedUnpaidLeaves() + totalAppliedLeaveDays
                );
                break;

            case MATERNITY:
            case PATERNITY:
                applyLeave(
                        availableBandLeaves.getParentalLeaves(),
                        usedLeaves.getUsedParentalLeaves(),
                        totalAppliedLeaveDays,
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
        Leave savedLeave = leaveRepo.save(leave);

        EmailLeaveDto emailLeaveDto = leaveMapper.toEmailLeaveDto(savedLeave);
        System.out.println("Email leave dto : "+ emailLeaveDto);

        try {
            emailPostService.sendLeaveStatusMail(emailLeaveDto);
        } catch (MessagingException e) {
            throw new RuntimeException("Exception in sending mail");
        }
        return savedLeave;
    }

    private void applyLeave(
            float available,
            float used,
            float totalAppliedLeaves,
            Consumer<Float> setUsedLeave,
            UsedLeaves usedLeaves,
            String leaveTypeName
    ) {
        if (used <= available) {
            if (used + totalAppliedLeaves >= available) {
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

        String currentEmployeesEmployeeID = SecurityContextHolder.getContext().getAuthentication().getName();

        Leave leave = leaveRepo.findById(leaveId).orElseThrow(() -> new InvalidLeaveException("Leave id not valid"));
        Long managerEmployeeID = leave.getEmployee().getDepartment().getManager().getEmployeeID();

        Leave savedLeave;
        if (currentEmployeesEmployeeID.equals(managerEmployeeID.toString())) {

            LeaveStatus status = leave.getStatus();
            leave.setStatus(LeaveStatus.APPROVED);
            savedLeave = leaveRepo.save(leave);
        }else {
            throw new InsufficientPermissionsException("You don't have authority to approve leaves of this employee");
        }

        EmailLeaveDto emailLeaveDto = leaveMapper.toEmailLeaveDto(savedLeave);
        System.out.println("Email leave dto : "+ emailLeaveDto);

        try {
            emailPostService.sendLeaveStatusMail(emailLeaveDto);
        } catch (MessagingException e) {
            throw new RuntimeException("Exception in sending mail");
        }
        return leaveMapper.toLeaveDto(savedLeave);
    }

    public LeaveDto rejectLeave(long leaveId) {

        String currentEmployeesEmployeeID = SecurityContextHolder.getContext().getAuthentication().getName();

        Leave leave = leaveRepo.findById(leaveId).orElseThrow(() -> new InvalidLeaveException("Leave id not valid"));
        Long managerEmployeeID = leave.getEmployee().getDepartment().getManager().getEmployeeID();

        Leave savedLeave;
        if (currentEmployeesEmployeeID.equals(managerEmployeeID.toString())) {

            LeaveStatus status = leave.getStatus();
            leave.setStatus(LeaveStatus.REJECTED);
            savedLeave = leaveRepo.save(leave);
        }else {
            throw new InsufficientPermissionsException("You don't have authority to reject leaves of this employee");
        }

        EmailLeaveDto emailLeaveDto = leaveMapper.toEmailLeaveDto(savedLeave);
        System.out.println("Email leave dto : "+ emailLeaveDto);

        try {
            emailPostService.sendLeaveStatusMail(emailLeaveDto);
        } catch (MessagingException e) {
            throw new RuntimeException("Exception in sending mail");
        }
        return leaveMapper.toLeaveDto(savedLeave);
    }
}
