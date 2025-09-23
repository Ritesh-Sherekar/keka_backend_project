package com.example.security_service.service;

import com.example.security_service.dto.RolesDto;
import com.example.security_service.entity.Roles;
import com.example.security_service.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

@Autowired
private RoleRepository roleRepository;

    public RolesDto addRole(RolesDto rolesDto) {

        Roles roles = new Roles();
        roles.setRoleId(rolesDto.getRoleId());
        roles.setRole(rolesDto.getRole());

        Roles savedRole = roleRepository.save(roles);
        return new RolesDto(savedRole.getRoleId(), savedRole.getRole());
    }
}
