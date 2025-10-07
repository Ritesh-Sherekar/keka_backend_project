package com.example.query_service.service;

import com.example.query_service.dto.LeaveDto;
import com.example.query_service.entity.*;
import com.example.query_service.entity.Leave;
import com.example.query_service.entity.LeaveDay;
import com.example.query_service.entity.UsedLeaves;
import com.example.query_service.enums.LeaveStatus;
import com.example.query_service.exception.InsufficientLeavesException;
import com.example.query_service.exception.InvalidBandException;
import com.example.query_service.exception.InvalidLeaveException;
import com.example.query_service.repository.UsedLeavesRepo;
import com.example.query_service.repository.BandRepo;
import com.example.query_service.repository.EmployeeRepo;
import com.example.query_service.repository.LeaveRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepo leaveRepo;

    @Autowired
    private UsedLeavesRepo usedLeavesRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private BandRepo bandRepo;

    public List<LeaveDto> getAllLeave() {
        return leaveRepo.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<LeaveDto> getLeaveByEmployeeID(Long employeeID) {
        return leaveRepo.findByEmployee_EmployeeID(employeeID)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
public List<LeaveDto> getPendingLeave() {
    return leaveRepo.findByStatus(LeaveStatus.PENDING)
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
}
    public List<LeaveDto> getApprovedLeave() {
        return leaveRepo.findByStatus(LeaveStatus.APPROVED)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<LeaveDto> getRejectedLeave() {
        return leaveRepo.findByStatus(LeaveStatus.REJECTED)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private LeaveDto convertToDto(Leave leave) {
        LeaveDto dto = new LeaveDto();
        dto.setEmployeeID(leave.getEmployee().getEmployeeID());
        dto.setApproverID(leave.getApprover() != null ? leave.getApprover().getEmployeeID() : 0);
        dto.setLeaveType(leave.getLeaveType());
        dto.setReason(leave.getReason());
        dto.setTotalLeaveDays(leave.getLeaveDays() != null ? leave.getLeaveDays().size() : 0);
        return dto;
    }
}