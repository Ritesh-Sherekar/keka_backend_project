package com.example.KekaActionService.controller.Delete;

import com.example.KekaActionService.dto.shiftDto.ShiftResponseDto;
import com.example.KekaActionService.service.Delete.ShiftDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ActionDeleteShiftService")
public class ShiftDeleteController {
    @Autowired
    private ShiftDeleteService shiftDeleteService;

    // Delete Shift By Shift ID
    @DeleteMapping("/deleteShiftByID/{shiftID}")
    public ResponseEntity<ShiftResponseDto> deleteShift(@PathVariable long shiftID){
        ShiftResponseDto responseDto = shiftDeleteService.deleteShift(shiftID);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
