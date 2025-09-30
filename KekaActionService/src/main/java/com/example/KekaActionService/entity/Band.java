package com.example.KekaActionService.entity;

import com.example.KekaActionService.enums.Bands;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bandId;

    @Enumerated(EnumType.STRING)
    private Bands bands;

    private float paidLeaves;
    private float sickLeaves;
    private float casualLeaves;
    private float unpaidLeaves;
    private float parentalLeaves;
}
