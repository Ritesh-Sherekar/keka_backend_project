package com.example.KekaActionService.repository;

import com.example.KekaActionService.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByEmployee_EmployeeID(Long employeeID);
}

