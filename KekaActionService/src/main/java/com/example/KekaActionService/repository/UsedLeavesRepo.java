package com.example.KekaActionService.repository;

import com.example.KekaActionService.entity.UsedLeaves;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsedLeavesRepo extends JpaRepository<UsedLeaves, Long> {

    Optional<UsedLeaves> findByEmployeeEmployeeID(long employeeId);
}