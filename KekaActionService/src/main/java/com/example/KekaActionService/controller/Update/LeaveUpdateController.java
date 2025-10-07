package com.example.KekaActionService.controller.Update;

import com.example.KekaActionService.dto.LeaveDto;
import com.example.KekaActionService.entity.Leave;
import com.example.KekaActionService.enums.LeaveStatus;
import com.example.KekaActionService.exception.InvalidLeaveException;
import com.example.KekaActionService.repository.LeaveRepo;
import com.example.KekaActionService.service.Post.LeavePostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leave")
public class LeaveUpdateController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LeavePostService leavePostService;

    @PostMapping("/approve")
    public ResponseEntity<LeaveDto> approveLeave(long leaveId){

        LeaveDto leaveDto = leavePostService.approveLeave(leaveId);

        return new ResponseEntity<>(leaveDto, HttpStatus.OK);
    }

    @PostMapping("/reject")
    public ResponseEntity<LeaveDto> rejectLeave(long leaveId){

        LeaveDto leaveDto = leavePostService.rejectLeave(leaveId);

        return new ResponseEntity<>(leaveDto, HttpStatus.OK);
    }
}
