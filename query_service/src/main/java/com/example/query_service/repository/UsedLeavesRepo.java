package com.example.query_service.repository;

import com.example.query_service.entity.UsedLeaves;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsedLeavesRepo extends JpaRepository<UsedLeaves, Long> {

    Optional<UsedLeaves> findByEmployeeEmployeeID(long employeeId);
}