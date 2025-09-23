package com.example.security_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Roles {

    @Id
    private long roleId;

    private String role;
}
