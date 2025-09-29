package com.example.query_service.repository;

import com.example.query_service.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeEmployeeId(Long employeeId);
}

