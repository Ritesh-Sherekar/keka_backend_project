package com.example.query_service.controller;

import com.example.query_service.entity.Attendance;
import com.example.query_service.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    @GetMapping("/getAttendanceByEmployeeID/{employeeID}")
    public ResponseEntity<List<Attendance>> getAttendanceByEmployeeID(@PathVariable Long employeeID){
        List<Attendance> attendance = attendanceService.getAttendanceByEmployeeID(employeeID);
        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }
    @GetMapping("/getAttendanceByEmployeeIDAndDate")
    public ResponseEntity<List<Attendance>> getAttendanceByEmployeeIDAndDate(@RequestParam Long employeeID, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        List<Attendance> attendance = attendanceService.getAttendanceByEmployeeIDAndDate(employeeID, startDate, endDate);
        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }

}
