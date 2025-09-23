package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.AttendanceRequestDto;
import com.example.KekaActionService.entity.Attendance;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.repository.AttendanceRepo;
import com.example.KekaActionService.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttendancePostService {
    @Autowired
    private EmployeeRepo employeeRepo;

    private final AttendanceRepo attendanceRepo;

    public AttendancePostService(AttendanceRepo attendanceRepo){
        this.attendanceRepo = attendanceRepo;
    }

    // Add Attendance
    public Attendance addAttendance(AttendanceRequestDto dto) {
        //Employee employee = employeeRepo.findById(Math.toIntExact(dto.getEmployeeID())).orElseThrow();
        System.out.println("in add attendance");
        Employee byEmployeeID = employeeRepo.findByEmployeeID(Math.toIntExact(dto.getEmployeeID()));

        System.out.println(byEmployeeID);
        if (byEmployeeID != null) {
            Attendance attendance = new Attendance();
            attendance.setEmployee(byEmployeeID);
            attendance.setAttendanceDate(dto.getAttendanceDate());
            attendance.setCheckInTime(dto.getCheckInTime());
            attendance.setCheckOutTime(dto.getCheckOutTime());
            attendance.setStatus(dto.getStatus());
            System.out.println("attendance : " + attendance);
            Attendance save = attendanceRepo.save(attendance);

            return save;
        }
        throw new RuntimeException("Not Found");

    }
}
