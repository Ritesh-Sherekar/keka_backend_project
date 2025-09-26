package com.example.KekaActionService.controller.Update;

import com.example.KekaActionService.dto.BandDto;
import com.example.KekaActionService.entity.Band;
import com.example.KekaActionService.service.Update.BandUpdateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/band")
public class BandUpdateController {

    @Autowired
    private BandUpdateService bandUpdateService;

    @Autowired
    private ObjectMapper objectMapper;

    @PutMapping("/")
    public ResponseEntity<BandDto> updateBand(@RequestBody BandDto bandDto){

        Band band = bandUpdateService.updateBand(bandDto);
        BandDto updatedBandDto = objectMapper.convertValue(band, BandDto.class);
        return new ResponseEntity<>(updatedBandDto, HttpStatus.OK);
    }
}
