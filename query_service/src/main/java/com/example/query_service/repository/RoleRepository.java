package com.example.query_service.repository;

import com.example.query_service.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Set<Roles> findByRoleIn(Set<String> roles);
}
