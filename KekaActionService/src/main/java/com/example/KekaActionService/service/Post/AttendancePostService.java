package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.AttendanceClockInRequestDto;
import com.example.KekaActionService.dto.AttendanceClockOutRequestDto;
import com.example.KekaActionService.entity.Attendance;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.repository.AttendanceRepo;
import com.example.KekaActionService.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendancePostService {
    @Autowired
    private EmployeeRepo employeeRepo;

    private final AttendanceRepo attendanceRepo;

    public AttendancePostService(AttendanceRepo attendanceRepo){
        this.attendanceRepo = attendanceRepo;
    }

    // Clock In API
    public Attendance clockIn(AttendanceClockInRequestDto dto) {
        Employee byEmployeeID = employeeRepo.findByEmployeeID(Math.toIntExact(dto.getEmployeeID()));

        if (byEmployeeID != null) {
            Attendance attendance = new Attendance();
            attendance.setEmployee(byEmployeeID);
            attendance.setAttendanceDate(dto.getAttendanceDate());
            attendance.setCheckInTime(dto.getCheckInTime());
            attendance.setCheckOutTime(dto.getCheckOutTime());
            attendance.setStatus(dto.getStatus());
            attendance.setBadge(dto.getBadge());

            return attendanceRepo.save(attendance);
        }
        throw new RuntimeException("Not Found");

    }

    // Clock Out API
    public Attendance clockOut(AttendanceClockOutRequestDto dto){
        Employee byEmployeeID = employeeRepo.findByEmployeeID(Math.toIntExact(dto.getEmployeeID()));

        Attendance attendance = attendanceRepo.findById(Math.toIntExact(dto.getId())).orElseThrow();

        if (attendance.getEmployee().getEmployeeID().equals(dto.getEmployeeID())){
            System.out.println("In First if");
            if (attendance.getCheckInTime() != null && attendance.getBadge() == Attendance.Badge.IN){
                System.out.println("In Second if");

                System.out.println(attendance.getCheckInTime());
                System.out.println(dto.getCheckOutTime());
                if (attendance.getCheckInTime().isBefore(dto.getCheckOutTime())){
                    System.out.println("In Third if");
                    attendance.setCheckOutTime(dto.getCheckOutTime());
                    attendance.setBadge(dto.getBadge());
                    return attendanceRepo.save(attendance);
                }
            }
        }

        throw new RuntimeException("Error");
    }
}
