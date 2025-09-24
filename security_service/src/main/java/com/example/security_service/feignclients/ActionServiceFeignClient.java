package com.example.security_service.feignclients;

import com.example.security_service.dto.PasswordResetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "KekaActionService")
public interface ActionServiceFeignClient {

    @PostMapping("/email/resetPassword")
    ResponseEntity<String> sendResetPasswordMail(@RequestBody PasswordResetDto passwordResetDto);
}
