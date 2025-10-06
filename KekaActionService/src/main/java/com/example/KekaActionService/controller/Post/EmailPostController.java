package com.example.KekaActionService.controller.Post;

import com.example.KekaActionService.dto.PasswordResetDto;
import com.example.KekaActionService.service.Post.EmailPostService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailPostController {

    @Autowired
    private EmailPostService emailService;

    @PostMapping("/resetPassword")
    public ResponseEntity<String> sendResetPasswordMail(@RequestBody PasswordResetDto passwordResetDto){

        String responceString = null;
        try {
            responceString = emailService.sendForgotPasswordMail(passwordResetDto);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(responceString, HttpStatus.OK);
    }
}
