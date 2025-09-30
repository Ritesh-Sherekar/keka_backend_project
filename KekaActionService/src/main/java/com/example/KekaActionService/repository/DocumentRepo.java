package com.example.KekaActionService.repository;

import com.example.KekaActionService.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepo extends JpaRepository<Document, Integer> {
    Optional<Document> findByDocId(Long docId);
}
