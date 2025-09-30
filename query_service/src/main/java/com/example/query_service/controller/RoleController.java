package com.example.query_service.controller;

import com.example.query_service.entity.Roles;
import com.example.query_service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/getAllRole")
    public ResponseEntity<List<Roles>> getAllRole(){
        List<Roles> role = roleService.getAllRole();
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
