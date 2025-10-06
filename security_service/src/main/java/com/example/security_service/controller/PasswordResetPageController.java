package com.example.security_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetPageController {

    @GetMapping("/reset-password-form")
    public String showResetPasswordForm(@RequestParam("passwordResetToken") String token, Model model) {
        model.addAttribute("token", token);
        return "reset_password_form";  // resolves to templates/reset_password_form.html
    }
}
