package com.example.query_service.controller;

import com.example.query_service.dto.LeaveDto;
import com.example.query_service.entity.Leave;
import com.example.query_service.service.LeaveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/getAllLeave")
    public ResponseEntity<List<Leave>> getAllLeave() {
        List<Leave> leave = leaveService.getAllLeave();
        return new ResponseEntity<>(leave, HttpStatus.OK);
    }
    @GetMapping("/getLeaveByEmployeeID/{employeeID}")
    public ResponseEntity<List<Leave>> getLeaveByEmployeeID(@PathVariable Long employeeID){
        List<Leave> leave = leaveService.getLeaveByEmployeeID(employeeID);
        return new ResponseEntity<>(leave, HttpStatus.OK);
    }
    @GetMapping("/getPendingLeave")
    public ResponseEntity<List<Leave>> getPendingLeave() {
        List<Leave> leave = leaveService.getPendingLeave();
        return new ResponseEntity<>(leave, HttpStatus.OK);
    }
    @GetMapping("/getApprovedLeave")
    public ResponseEntity<List<Leave>> getApprovedLeave() {
        List<Leave> leave = leaveService.getApprovedLeave();
        return new ResponseEntity<>(leave, HttpStatus.OK);
    }
    @GetMapping("/getRejectedLeave")
    public ResponseEntity<List<Leave>> getRejectedLeave(){
        List<Leave> leave = leaveService.getRejectedLeave();
        return new ResponseEntity<>(leave, HttpStatus.OK);
    }
}
