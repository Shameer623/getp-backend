package com.getp.backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Value("${app.notification.email}")
    private String notificationEmail;

    public void sendContactNotification(String name, String email, String subject, String message) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(notificationEmail); 
        mail.setFrom(notificationEmail);
        mail.setSubject("New Contact Form Submission");

        mail.setText(
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Subject: " + subject + "\n\n" +
                "Message:\n" + message
        );

        mailSender.send(mail);
    }
}