package com.example.query_service.service;

import com.example.query_service.entity.Attendance;
import com.example.query_service.repository.AttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceFindService {
    @Autowired
    private AttendanceRepo attendanceRepo;
    public List<Attendance> findByEmployee_EmployeeID(Long employeeID){
        return attendanceRepo.findByEmployee_EmployeeID(employeeID);
    }
}
