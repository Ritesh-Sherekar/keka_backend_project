package com.example.query_service.controller;

import com.example.query_service.entity.Band;
import com.example.query_service.enums.Bands;
import com.example.query_service.service.BandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/band")
public class BandController {

    @Autowired
    private BandService bandService;
    @GetMapping("/getAllBand")
    public ResponseEntity<List<Band>> getAllBand(){
        List<Band> band = bandService.getAllBand();
        return new ResponseEntity<>(band, HttpStatus.OK);
    }
    @GetMapping("/getBandByBandName/{bandName}")
    public ResponseEntity<Optional<Band>> getBandByBandName(@PathVariable Bands bandName){
        Optional<Band> band = bandService.getBandByBandName(bandName);
        return new ResponseEntity<>(band, HttpStatus.OK);
    }
}