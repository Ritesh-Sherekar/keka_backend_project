package com.example.query_service.service;

import com.example.query_service.entity.Roles;
import com.example.query_service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public List<Roles> getAllRole(){
        return roleRepository.findAll();
    }
}
