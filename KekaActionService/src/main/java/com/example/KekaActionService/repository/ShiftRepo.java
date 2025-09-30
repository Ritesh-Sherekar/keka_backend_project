package com.example.KekaActionService.repository;

import com.example.KekaActionService.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepo extends JpaRepository<Shift, Integer> {
}
