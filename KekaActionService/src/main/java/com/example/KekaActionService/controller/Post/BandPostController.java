package com.example.KekaActionService.controller.Post;

import com.example.KekaActionService.dto.BandDto;
import com.example.KekaActionService.entity.Band;
import com.example.KekaActionService.service.Post.BandPostService;
import com.example.KekaActionService.service.Update.BandUpdateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/band")
public class BandPostController {

    @Autowired
    private BandPostService bandPostService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/")
    public ResponseEntity<BandDto> postBand(@RequestBody BandDto bandDto) {
        log.info("in band post controller");
        Band band = bandPostService.addBand(bandDto);
        log.info("band : {}", String.valueOf(band));
        BandDto addedBand = objectMapper.convertValue(band, BandDto.class);
        log.info("Added band dto : {}", String.valueOf(addedBand));
        return new ResponseEntity<>(addedBand, HttpStatus.OK);
    }
}