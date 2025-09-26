package com.example.query_service.controller;

import com.example.query_service.entity.Attendance;
import com.example.query_service.service.AttendanceFindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceFindController {
    @Autowired
    private AttendanceFindService attendanceFindService;
    @GetMapping("/findByEmployeeId/{employeeID}")
    public ResponseEntity<List<Attendance>> findByEmployee_EmployeeID(@PathVariable Long employeeID){
        List<Attendance> attendance = attendanceFindService.findByEmployee_EmployeeID(employeeID);
        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }
}
