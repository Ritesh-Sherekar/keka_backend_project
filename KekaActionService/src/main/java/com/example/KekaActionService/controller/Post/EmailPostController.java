package com.example.KekaActionService.controller.Post;

import com.example.KekaActionService.dto.PasswordResetDto;
import com.example.KekaActionService.service.Post.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailPostController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/resetPassword")
    public ResponseEntity<String> sendResetPasswordMail(@RequestBody PasswordResetDto passwordResetDto){

        System.out.println("in email controller");
        String responceString = null;
        try {
            responceString = emailService.sendForgotPasswordMail(passwordResetDto);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(responceString, HttpStatus.OK);
    }
}
