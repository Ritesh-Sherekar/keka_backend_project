package com.example.KekaActionService.repository;

import com.example.KekaActionService.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepo extends JpaRepository<Document, Integer> {
}
