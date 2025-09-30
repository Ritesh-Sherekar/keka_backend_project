package com.example.query_service.enums;

import lombok.Getter;

@Getter
public enum Half {

    FIRST_HALF(0.5),
    SECOND_HALF(0.5),
    FULL_DAY(1.0);

    private final double value;

    Half(double value) {
        this.value = value;
    }
}

