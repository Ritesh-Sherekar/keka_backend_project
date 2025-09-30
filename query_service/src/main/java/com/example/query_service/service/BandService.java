package com.example.query_service.service;

import com.example.query_service.entity.Band;
import com.example.query_service.enums.Bands;
import com.example.query_service.repository.BandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BandService {

    @Autowired
    private BandRepo bandRepo;

    public List<Band> getAllBand(){
        return bandRepo.findAll();
    }
    public Optional<Band> getBandByBandName(Bands bandName){
        return bandRepo.findByBands(bandName);
    }
}