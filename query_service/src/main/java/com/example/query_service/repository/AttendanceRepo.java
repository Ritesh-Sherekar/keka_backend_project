package com.example.query_service.repository;

import com.example.query_service.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {

    List<Attendance> findByEmployee_EmployeeID(Long employeeID);
    List<Attendance> findByEmployee_EmployeeIDAndAttendanceDateBetween(
            Long employeeID, LocalDate startDate, LocalDate endDate);}

