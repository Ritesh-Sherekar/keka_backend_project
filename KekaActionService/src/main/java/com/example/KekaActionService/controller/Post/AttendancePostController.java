package com.example.KekaActionService.controller.Post;

import com.example.KekaActionService.dto.attendanceDto.AttendanceClockInRequestDto;
import com.example.KekaActionService.dto.attendanceDto.AttendanceClockOutRequestDto;
import com.example.KekaActionService.dto.attendanceDto.AttendanceRegularizationRequestDto;
import com.example.KekaActionService.entity.Attendance;
import com.example.KekaActionService.service.Post.AttendancePostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ActionPostAtteService")
public class AttendancePostController {
    private final AttendancePostService attendancePostService;

    public AttendancePostController(AttendancePostService attendancePostService){
        this.attendancePostService = attendancePostService;
    }

    // Clock In Api
    @PostMapping("/clockIN")
    public ResponseEntity<Attendance> clockInApi(@RequestBody AttendanceClockInRequestDto dto){
        Attendance attendance1 = attendancePostService.clockIn(dto);
        return new ResponseEntity<>(attendance1, HttpStatus.OK);
    }

    // Clock Out Api
    @PostMapping("/clockOut")
    public ResponseEntity<Attendance> clockOutApi(@RequestBody AttendanceClockOutRequestDto dto){
        Attendance attendance = attendancePostService.clockOut(dto);
        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }

    // Regularization Api
    @PostMapping("/regularization")
    public ResponseEntity<List<Attendance>> regularizationApi(@RequestBody AttendanceRegularizationRequestDto dto){
        List<Attendance> attendances = attendancePostService.regularizationApi(dto);
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }
}
