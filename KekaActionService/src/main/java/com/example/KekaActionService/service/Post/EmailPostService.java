package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.PasswordResetDto;
import com.example.KekaActionService.entity.Attendance;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.repository.EmployeeRepo;
import com.example.KekaActionService.response.EmailRegularizationTemplate;
import com.example.KekaActionService.response.EmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailPostService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    JavaMailSender javaMailSender;

    EmailTemplate emailTemplate = new EmailTemplate();

    public String sendForgotPasswordMail(PasswordResetDto passwordResetDto) throws MessagingException {
        System.out.println("In email service");
        String resetLink =  "http://localhost:4580/auth/reset_password?passwordResetToken=" + passwordResetDto.getResetToken();
        String html = EmailTemplate.getForgotPasswordHtml(passwordResetDto.getUserName(), resetLink);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setSubject("\uD83D\uDD12 Reset Your Password");
        mimeMessageHelper.setTo(passwordResetDto.getEmail());
        mimeMessageHelper.setText(html,true);

        javaMailSender.send(mimeMessage);

        return "Recovery mail sent";
    }

    public void sendRegularizedMail(Employee employee, Attendance attendance) throws MessagingException {
        String html = EmailRegularizationTemplate.getLeaveRegularizationHtml(employee.getFirstName(), attendance.getAttendanceDate().toString(), attendance.getStatus().toString(), "Regularization Update");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setSubject("Your request is regularized successfully.");
        mimeMessageHelper.setTo(employee.getEmail());
        mimeMessageHelper.setText(html,true);

        javaMailSender.send(mimeMessage);
    }
}
