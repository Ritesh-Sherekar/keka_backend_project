package com.example.KekaActionService.repository;

import com.example.KekaActionService.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepo extends JpaRepository<Leave, Long> {
}
