package com.example.KekaActionService.service.Update;

import com.example.KekaActionService.dto.BandDto;
import com.example.KekaActionService.entity.Band;
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

    public Band updateBand(BandDto bandDto) {

        log.info("Updating band: {}", bandDto);

        Band band = bandRepo.findByBands(bandDto.getBands())
                .orElseThrow(() -> new InvalidBandException("Invalid Band"));

        if (bandDto.getPaidLeaves() != null) band.setPaidLeaves(bandDto.getPaidLeaves());
        if (bandDto.getSickLeaves() != null) band.setSickLeaves(bandDto.getSickLeaves());
        if (bandDto.getCasualLeaves() != null) band.setCasualLeaves(bandDto.getCasualLeaves());
        if (bandDto.getUnpaidLeaves() != null) band.setUnpaidLeaves(bandDto.getUnpaidLeaves());
        if (bandDto.getParentalLeaves() != null) band.setParentalLeaves(bandDto.getParentalLeaves());

        Band updatedBand = bandRepo.save(band);

        log.info("Updated band: {}", updatedBand);
        return updatedBand;
    }

}
