package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.EmailLeaveDto;
import com.example.KekaActionService.dto.LeaveDto;
import com.example.KekaActionService.dto.PasswordResetDto;
import com.example.KekaActionService.entity.Attendance;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.repository.EmployeeRepo;
import com.example.KekaActionService.response.EmailLateArrivalTemplate;
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

    // Sending Forgot Password Mail
    public String sendForgotPasswordMail(PasswordResetDto passwordResetDto) throws MessagingException {

        System.out.println("token in mail : " + passwordResetDto.getResetToken());
        String resetLink = "http://localhost:4580/reset-password-form?passwordResetToken=" + passwordResetDto.getResetToken();
        String html = EmailTemplate.getForgotPasswordHtml(passwordResetDto.getUserName(), resetLink);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setSubject("\uD83D\uDD12 Reset Your Password");
        mimeMessageHelper.setTo(passwordResetDto.getEmail());
        mimeMessageHelper.setText(html,true);

        javaMailSender.send(mimeMessage);

        return "Recovery mail sent";
    }

    // Sending Mail for Regularization Request
    public void sendRegularizedMail(Employee employee, Attendance attendance) throws MessagingException {
        String html = EmailRegularizationTemplate.getLeaveRegularizationHtml(employee.getFirstName(), attendance.getAttendanceDate().toString(), attendance.getStatus().toString(), "Regularization Update");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setSubject("\u2705 Your request is regularized successfully.");
        mimeMessageHelper.setTo(employee.getEmail());
        mimeMessageHelper.setText(html,true);

        javaMailSender.send(mimeMessage);
    }

    // Sending Mail For Late Arrival
    public void sendLateArrivalMail(Employee employee, Attendance attendance) throws MessagingException {
        String html = EmailLateArrivalTemplate.getLateArrivalHtml(employee.getFirstName(), attendance.getAttendanceDate().toString(), attendance.getCheckInTime().toString(), "Please ensure punctuality.");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setSubject("\u26A0\uFE0F Late Arrival: Clocked in more than 1 hour late");
        mimeMessageHelper.setTo(employee.getEmail());
        mimeMessageHelper.setText(html,true);

        javaMailSender.send(mimeMessage);
    }

    // Sending Mail For Leave status
    public void sendLeaveStatusMail(EmailLeaveDto emailLeaveDto) throws MessagingException {
        String html = EmailTemplate.getLeaveStatusHtml(emailLeaveDto);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setSubject("\u26A0\uFE0F Leave Request : " + emailLeaveDto.getLeaveStatus());

        mimeMessageHelper.setTo(emailLeaveDto.getEmail());
        mimeMessageHelper.setText(html,true);

        javaMailSender.send(mimeMessage);
    }
}
