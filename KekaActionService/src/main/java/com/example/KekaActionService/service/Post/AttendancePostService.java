package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.attendanceDto.AttendanceClockInRequestDto;
import com.example.KekaActionService.dto.attendanceDto.AttendanceClockOutRequestDto;
import com.example.KekaActionService.dto.attendanceDto.AttendanceRegularizationRequestDto;
import com.example.KekaActionService.entity.Attendance;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.enums.Badge;
import com.example.KekaActionService.enums.Status;
import com.example.KekaActionService.exception.EmployeeIdNotFoundException;
import com.example.KekaActionService.repository.AttendanceRepo;
import com.example.KekaActionService.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

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
        Employee byEmployeeID = employeeRepo.findByEmployeeID(dto.getEmployeeID()).orElseThrow(() -> new RuntimeException("Employee does not exists"));

        if (byEmployeeID != null) {
            Attendance attendance = new Attendance();
            attendance.setEmployee(byEmployeeID);
            attendance.setAttendanceDate(dto.getAttendanceDate());
            attendance.setCheckInTime(dto.getCheckInTime());
            attendance.setCheckOutTime(dto.getCheckOutTime());
            attendance.setStatus(Status.PRESENT);
            attendance.setBadge(Badge.IN);

            return attendanceRepo.save(attendance);
        }
        throw new EmployeeIdNotFoundException("Employee With Given ID Not Found");
    }

    // Clock Out API
    public Attendance clockOut(AttendanceClockOutRequestDto dto){
        Employee byEmployeeID = employeeRepo.findByEmployeeID(dto.getEmployeeID()).orElseThrow(() -> new RuntimeException("Employee does not exists"));

        Attendance attendance = attendanceRepo.findById(Math.toIntExact(dto.getId())).orElseThrow();

        if (attendance.getEmployee().getEmployeeID().equals(dto.getEmployeeID())){
            if (attendance.getCheckInTime() != null && attendance.getBadge() == Badge.IN){
                if (attendance.getCheckInTime().isBefore(dto.getCheckOutTime())){
                    attendance.setCheckOutTime(dto.getCheckOutTime());
                    attendance.setBadge(Badge.OUT);
                    String grossHours = calculateGrossHours(attendance.getCheckInTime(), attendance.getCheckOutTime());
                    attendance.setGrossHours(grossHours);
                    return attendanceRepo.save(attendance);
                }
            }
        }
        throw new EmployeeIdNotFoundException("Employee With Given ID Not Found");
    }

    // Helper Method To Calculate Gross Hours
    private String calculateGrossHours(LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        if (checkInTime == null || checkOutTime == null){
            return "0h 0m";
        }
        Duration duration = Duration.between(checkInTime, checkOutTime);
        long hours = duration.toHours();
        long minute = duration.toMinutes() % 60;

        return hours + "h " + minute + "m";
    }

    // Regularization Api
    public List<Attendance> regularizationApi(AttendanceRegularizationRequestDto dto){
        List<Attendance> attendances = attendanceRepo.findByEmployee_EmployeeID(dto.getEmployeeID());

        List<Attendance> updatedAttendances = attendances.stream()
                .filter(attendance -> attendance.getAttendanceDate().isEqual(dto.getAttendanceDate()))
                .map(attendance -> {
                    attendance.setGrossHours(null);
                    attendance.setBadge(Badge.REGULARIZED);
                    return attendanceRepo.save(attendance);
                }).toList();

        return updatedAttendances;
    }
}
