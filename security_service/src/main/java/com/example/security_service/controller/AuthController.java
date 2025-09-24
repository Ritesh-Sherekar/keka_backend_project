package com.example.security_service.controller;

import com.example.security_service.dto.UserDto;
import com.example.security_service.model.LogIdPassword;
import com.example.security_service.response.JwtResponse;
import com.example.security_service.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto){

        UserDto savedUserDto = authService.registerNewUser(userDto);
        return new ResponseEntity<>(savedUserDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> logIn(@RequestBody LogIdPassword logIdPassword){

        log.info("/auth/login controller");
        JwtResponse jetRespnse = authService.login(logIdPassword);
        return new ResponseEntity<>(jetRespnse, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestParam String refreshToken){

        log.info("/auth/refresh controller");
        JwtResponse jetResponse = authService.refreshToken(refreshToken);
        return new ResponseEntity<>(jetResponse, HttpStatus.OK);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<String> forgotPassword(@RequestParam String userName){

        log.info("/auth/forgot_password");
        String message = authService.forgotPassword(userName);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestParam String passwordResetToken, @RequestParam String newPassword){

        log.info("/auth/reset_password");
        String message = authService.resetPassword(passwordResetToken, newPassword);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
