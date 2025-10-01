package com.example.query_service.service;

import com.example.query_service.entity.Attendance;
import com.example.query_service.repository.AttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepo;

    public List<Attendance> getAttendanceByEmployeeID(Long employeeID){
        return attendanceRepo.findByEmployee_EmployeeID(employeeID);
    }
    public List<Attendance> getAttendanceByEmployeeIDAndDate(Long employeeID, LocalDate startDate, LocalDate endDate){
        return attendanceRepo.findByEmployee_EmployeeIDAndAttendanceDateBetween(employeeID, startDate, endDate);
    }
}
