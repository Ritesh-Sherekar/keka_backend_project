package com.example.query_service.service;

import com.example.query_service.entity.Attendance;
import com.example.query_service.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceFindService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> findAttendanceByEmployeeId(Long employeeId){
        return attendanceRepository.findByEmployeeEmployeeId(employeeId);
    }

}
