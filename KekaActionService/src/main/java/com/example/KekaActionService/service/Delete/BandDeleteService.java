package com.example.KekaActionService.service.Delete;

import com.example.KekaActionService.dto.BandDto;
import com.example.KekaActionService.entity.Band;
import com.example.KekaActionService.exception.InvalidBandException;
import com.example.KekaActionService.repository.BandRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BandDeleteService {

    @Autowired
    private BandRepo bandRepo;

    public String deleteBand(BandDto bandDto){

        log.info(String.valueOf(bandDto));
        Band band = bandRepo.findByBands(bandDto.getBands()).orElseThrow(() -> new InvalidBandException("Invalid Band"));
        bandRepo.delete(band);

        log.info("Band deleted : {}", bandDto);
        return "Band Deleted successfully";
    }
}
