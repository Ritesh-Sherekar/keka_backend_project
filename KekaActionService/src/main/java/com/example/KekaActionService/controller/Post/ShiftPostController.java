package com.example.KekaActionService.controller.Post;

import com.example.KekaActionService.dto.shiftDto.ShiftRequestDto;
import com.example.KekaActionService.entity.Shift;
import com.example.KekaActionService.service.Post.ShiftPostService;
import jakarta.ws.rs.POST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ActionPostShiftService")
public class ShiftPostController {
    @Autowired
    private ShiftPostService shiftPostService;

    // Add Shift
    @PostMapping("/addShift")
    public ResponseEntity<Shift> addShift(@RequestBody ShiftRequestDto shift){
        Shift addedShift = shiftPostService.addShift(shift);
        return new ResponseEntity<>(addedShift, HttpStatus.OK);
    }

    // Add List OF Shift
    @PostMapping("/addListOfShift")
    public ResponseEntity<List<Shift>> addListOfShift(@RequestBody List<Shift> shiftList){
        List<Shift> shifts = shiftPostService.addListOfShift(shiftList);
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }
}
