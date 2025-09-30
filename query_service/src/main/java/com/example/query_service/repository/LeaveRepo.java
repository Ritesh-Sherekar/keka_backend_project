package com.example.query_service.repository;

import com.example.query_service.entity.Attendance;
import com.example.query_service.entity.Leave;
import com.example.query_service.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepo extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployee_EmployeeID(Long employeeID);
    List<Leave> findByStatus(LeaveStatus status);
}
