package com.example.KekaActionService.mapper;

import com.example.KekaActionService.dto.EmailLeaveDto;
import com.example.KekaActionService.dto.LeaveDto;
import com.example.KekaActionService.entity.Leave;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LeaveMapper {

    // --- Mapping for Leave → LeaveDto ---
    @Mappings({
            @Mapping(source = "employee.employeeID", target = "employeeID"),
            @Mapping(source = "approver.employeeID", target = "approverID"),
            @Mapping(source = "leaveType", target = "leaveType"),
            @Mapping(source = "reason", target = "reason"),
            @Mapping(source = "leaveDays", target = "leaveDays"),
            @Mapping(target = "totalLeaveDays", ignore = true)
    })
    LeaveDto toLeaveDto(Leave leave);

    // --- Mapping for LeaveDto → Leave ---
    @Mappings({
            @Mapping(target = "employee", ignore = true),
            @Mapping(target = "approver", ignore = true),
            @Mapping(target = "leaveDays", ignore = true),
            @Mapping(target = "leaveId", ignore = true),
            @Mapping(target = "appliedAt", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "isDelete", ignore = true)
    })
    Leave toEntity(LeaveDto dto);

    // --- Mapping for Leave → EmailLeaveDto ---
    @Mappings({
            @Mapping(source = "employee.employeeID", target = "employeeID"),
            @Mapping(source = "approver.employeeID", target = "approverID"),
            @Mapping(source = "employee.firstName", target = "employeeName"),
            @Mapping(source = "approver.firstName", target = "approverName"),
            @Mapping(source = "leaveType", target = "leaveType"),
            @Mapping(source = "status", target = "leaveStatus"),
            @Mapping(source = "reason", target = "reason"),
            @Mapping(source = "leaveDays", target = "leaveDays"),
            @Mapping(source = "leaveDaysCount", target = "leaveDaysCount"),
            @Mapping(source = "employee.email", target = "email"),
    })
    EmailLeaveDto toEmailLeaveDto(Leave leave);

    // --- Mapping for EmailLeaveDto → Leave ---
    @Mappings({
            @Mapping(target = "employee", ignore = true),
            @Mapping(target = "approver", ignore = true),
            @Mapping(target = "leaveDays", ignore = true),
            @Mapping(target = "leaveId", ignore = true),
            @Mapping(target = "appliedAt", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "isDelete", ignore = true)
    })
    Leave toEntity(EmailLeaveDto dto);
}
