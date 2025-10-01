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

    public List<Leave> getAllLeave() {
    return leaveRepo.findAll();
    }
    public List<Leave> getLeaveByEmployeeID(Long employeeID){
        return leaveRepo.findByEmployee_EmployeeID(employeeID);
    }
    public List<Leave> getPendingLeave() {
        return leaveRepo.findByStatus(LeaveStatus.PENDING);
    }
    public List<Leave> getApprovedLeave() {
        return leaveRepo.findByStatus(LeaveStatus.APPROVED);
    }
    public List<Leave> getRejectedLeave(){
        return leaveRepo.findByStatus(LeaveStatus.REJECTED);
    }
}