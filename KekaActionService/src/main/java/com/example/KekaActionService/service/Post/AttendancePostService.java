package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.attendanceDto.AttendanceClockInRequestDto;
import com.example.KekaActionService.dto.attendanceDto.AttendanceClockOutRequestDto;
import com.example.KekaActionService.dto.attendanceDto.AttendanceRegularizationRequestDto;
import com.example.KekaActionService.entity.Attendance;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.entity.Shift;
import com.example.KekaActionService.enums.Badge;
import com.example.KekaActionService.enums.Status;
import com.example.KekaActionService.exception.EmployeeIdNotFoundException;
import com.example.KekaActionService.exception.NoAttendanceOnThisDayException;
import com.example.KekaActionService.repository.AttendanceRepo;
import com.example.KekaActionService.repository.EmployeeRepo;
import com.example.KekaActionService.response.EmailRegularizationTemplate;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendancePostService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private EmailPostService emailPostService;

    private final AttendanceRepo attendanceRepo;

    public AttendancePostService(AttendanceRepo attendanceRepo){
        this.attendanceRepo = attendanceRepo;
    }

    // Clock In API
    public Attendance clockIn(AttendanceClockInRequestDto dto) throws MessagingException {
        Employee byEmployeeID = employeeRepo.findByEmployeeID(dto.getEmployeeID())
                .orElseThrow(() -> new EmployeeIdNotFoundException("Employee does not exists"));

            Attendance attendance = new Attendance();
            attendance.setEmployee(byEmployeeID);
            attendance.setAttendanceDate(dto.getAttendanceDate());
            attendance.setCheckInTime(dto.getCheckInTime());
            attendance.setCheckOutTime(dto.getCheckOutTime());
            attendance.setStatus(Status.PRESENT);
            attendance.setBadge(Badge.IN);

        Attendance saveClockIn = attendanceRepo.save(attendance);

        Shift shift = byEmployeeID.getShift();
        if (shift != null && dto.getCheckInTime() != null){
            LocalTime startTime = shift.getStartTime();
            LocalTime checkInTime = dto.getCheckInTime().toLocalTime();

            if (checkInTime.isAfter(startTime.plusHours(1))){
                emailPostService.sendLateArrivalMail(byEmployeeID, saveClockIn);
            }
        }

        return saveClockIn;
    }

    // Clock Out API
    public Attendance clockOut(AttendanceClockOutRequestDto dto){
        Employee byEmployeeID = employeeRepo.findByEmployeeID(dto.getEmployeeID()).orElseThrow(() -> new EmployeeIdNotFoundException("Employee does not exists"));

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
    public Attendance regularizationApi(AttendanceRegularizationRequestDto dto) {
        List<Attendance> attendances = attendanceRepo.findByEmployee_EmployeeID(dto.getEmployeeID());

        boolean b = attendances.stream()
                .anyMatch(attendance -> attendance.getAttendanceDate().isEqual(dto.getAttendanceDate()));

        if (!b) {
            throw new NoAttendanceOnThisDayException("Attendance Not Present On This Day");
        }

        List<Attendance> updatedAttendances = attendances.stream()
                .filter(attendance -> attendance.getAttendanceDate().isEqual(dto.getAttendanceDate()))
                .map(attendance -> {
                    attendance.setGrossHours(null);
                    attendance.setBadge(Badge.REGULARIZED);
                    return attendanceRepo.save(attendance);
                }).toList();

        if (!updatedAttendances.isEmpty()) {
            employeeRepo.findByEmployeeID(dto.getEmployeeID())
                .ifPresent(emp -> {
                    Attendance updated = updatedAttendances.getFirst();
                    try {
                        emailPostService.sendRegularizedMail(emp, updated);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                });
        }

        return updatedAttendances.getFirst();
        }

}

