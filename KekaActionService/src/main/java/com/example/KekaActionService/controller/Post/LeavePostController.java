package com.example.KekaActionService.controller.Post;

import com.example.KekaActionService.dto.LeaveDto;
import com.example.KekaActionService.entity.Leave;
import com.example.KekaActionService.service.Post.LeavePostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/leave")
public class LeavePostController {

    @Autowired
    private LeavePostService leavePostService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/")
    public ResponseEntity<LeaveDto> postLeave(@RequestBody LeaveDto leaveDto) {
        log.info("In LeavePostController - Adding Leave");
        log.info("leavedto : {}", leaveDto);
        Leave leave = leavePostService.addLeave(leaveDto);
        log.info("Leave saved: {}", leave);

        LeaveDto addedLeave = objectMapper.convertValue(leave, LeaveDto.class);
        log.info("Added Leave DTO: {}", addedLeave);

        return new ResponseEntity<>(addedLeave, HttpStatus.OK);
    }
}
