package com.example.query_service.repository;

import com.example.query_service.dto.BandDto;
import com.example.query_service.entity.Band;
import com.example.query_service.enums.Bands;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BandRepo extends JpaRepository<Band, Long> {

    Optional<Band> findByBands(Bands band);
}
