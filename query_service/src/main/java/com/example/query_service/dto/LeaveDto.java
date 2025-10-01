package com.example.query_service.dto;

import com.example.query_service.enums.LeaveType;
import lombok.Data;

import java.util.List;

@Data
public class LeaveDto {
    private long employeeID;
    private long approverID;
    private LeaveType leaveType;
    private String reason;
    private List<com.example.query_service.dto.LeaveDayDto> leaveDays;
    private float totalLeaveDays;
}
