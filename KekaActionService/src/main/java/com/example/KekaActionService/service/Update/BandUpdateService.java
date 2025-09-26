package com.example.KekaActionService.service.Update;

import com.example.KekaActionService.dto.BandDto;
import com.example.KekaActionService.entity.Band;
import com.example.KekaActionService.exception.BandAlreadyExistsException;
import com.example.KekaActionService.exception.InvalidBandException;
import com.example.KekaActionService.repository.BandRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class BandUpdateService {

    @Autowired
    private BandRepo bandRepo;

    public Band updateBand(BandDto bandDto){

        log.info(String.valueOf(bandDto));
        Band band = bandRepo.findByBands(bandDto.getBands()).orElseThrow(() -> new InvalidBandException("Invalid Band"));
        band.setLeaves(bandDto.getLeaves());
        Band updatedBand = bandRepo.save(band);

        log.info(String.valueOf(updatedBand));
        return updatedBand;
    }
}
