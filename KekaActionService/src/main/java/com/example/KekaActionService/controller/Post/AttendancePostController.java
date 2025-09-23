package com.example.KekaActionService.controller.Post;

import com.example.KekaActionService.dto.AttendanceRequestDto;
import com.example.KekaActionService.dto.AttendanceResponseDto;
import com.example.KekaActionService.entity.Attendance;
import com.example.KekaActionService.service.Post.AttendancePostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ActionPostAtteService")
public class AttendancePostController {
    private final AttendancePostService attendancePostService;

    public AttendancePostController(AttendancePostService attendancePostService){
        this.attendancePostService = attendancePostService;
    }

    // Add Attendance
    @PostMapping("/addAttendance")
    public ResponseEntity<Attendance> addAttendance(@RequestBody AttendanceRequestDto dto){
        Attendance attendance1 = attendancePostService.addAttendance(dto);
        return new ResponseEntity<>(attendance1, HttpStatus.OK);
    }
}
