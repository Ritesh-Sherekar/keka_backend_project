package com.example.KekaActionService.repository;

import com.example.KekaActionService.entity.AvailableLeaves;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvailableLeavesRepo extends JpaRepository<AvailableLeaves, Long> {

    Optional<AvailableLeaves> findByEmployeeEmployeeID(long employeeId);
}