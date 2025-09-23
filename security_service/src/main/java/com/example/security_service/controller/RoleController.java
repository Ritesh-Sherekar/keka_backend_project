package com.example.security_service.controller;

import com.example.security_service.dto.RolesDto;
import com.example.security_service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolesDto> addRole(@RequestBody RolesDto rolesDto){

        RolesDto addedRole = roleService.addRole(rolesDto);
        return new ResponseEntity<>(addedRole, HttpStatus.OK);
    }
}