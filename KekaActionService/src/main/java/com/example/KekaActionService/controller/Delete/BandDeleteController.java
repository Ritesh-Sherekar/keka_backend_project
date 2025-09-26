package com.example.KekaActionService.controller.Delete;


import com.example.KekaActionService.dto.BandDto;
import com.example.KekaActionService.entity.Band;
import com.example.KekaActionService.service.Delete.BandDeleteService;
import com.example.KekaActionService.service.Update.BandUpdateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/band")
public class BandDeleteController {

    @Autowired
    private BandDeleteService bandDeleteService;

    @Autowired
    private ObjectMapper objectMapper;

    @DeleteMapping("/")
    public ResponseEntity<String> updateBand(@RequestBody BandDto bandDto){

        String message = bandDeleteService.deleteBand(bandDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}