package com.example.KekaActionService.repository;

import com.example.KekaActionService.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {
}
