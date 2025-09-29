package com.example.KekaActionService.dto;

import com.example.KekaActionService.enums.LeaveType;
import lombok.Data;

import java.util.List;

@Data
public class LeaveDto {
    private long employeeID;
    private long approverID;
    private LeaveType leaveType;
    private String reason;
    private List<LeaveDayDto> leaveDays;
}
