package com.example.KekaActionService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long docId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID", nullable = false, referencedColumnName = "employeeID")
    private Employee employeeID;

    private String typeOfDoc;
    @Column(nullable = true)
    private String subTypeDoc;
    private String fileName;
    private String contentType;

    @Lob
    @Column(name = "data", columnDefinition = "BLOB")
    private byte[] data;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
}
