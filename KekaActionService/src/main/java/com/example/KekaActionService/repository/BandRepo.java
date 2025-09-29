package com.example.KekaActionService.repository;

import com.example.KekaActionService.entity.Band;
import com.example.KekaActionService.enums.Bands;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BandRepo extends JpaRepository<Band, Long> {

    Optional<Band> findByBands(Bands band);
}
