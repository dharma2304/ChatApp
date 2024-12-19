package com.chat.MyChatApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private  JavaMailSender mailSender;


    public void emailResetToken(String resetToken, String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("makinenidharmendra123@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("Password Reset Request Token");
        mailMessage.setText("Use this token to reset your password: "+ resetToken);
        mailSender.send(mailMessage);
    }
}
