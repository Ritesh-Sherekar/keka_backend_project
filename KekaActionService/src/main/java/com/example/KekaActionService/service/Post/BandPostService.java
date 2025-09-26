package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.BandDto;
import com.example.KekaActionService.entity.Band;
import com.example.KekaActionService.exception.BandAlreadyExistsException;
import com.example.KekaActionService.repository.BandRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class BandPostService {

    @Autowired
    private BandRepo bandRepo;

    public Band addBand(BandDto bandDto){

        System.out.println("In add band service");
        log.info(String.valueOf(bandDto));
        Optional<Band> band = bandRepo.findByBands(bandDto.getBands());
        if (band.isPresent()){
            throw new BandAlreadyExistsException("Band already exists");
        }

        Band bandToSave = new Band();
        bandToSave.setBands(bandDto.getBands());
        bandToSave.setLeaves(bandDto.getLeaves());

        Band savedBand = bandRepo.save(bandToSave);

        log.info(String.valueOf(savedBand));
        return savedBand;
    }
}
