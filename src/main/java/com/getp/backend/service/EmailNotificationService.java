package com.getp.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.getp.backend.entity.ContactMessage;

@Service
public class EmailNotificationService {

    private final JavaMailSender mailSender;

    @Value("${notification.email.from}")
    private String fromEmail;

    @Value("${notification.email.to}")
    private String toEmail;

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotification(ContactMessage msg) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(fromEmail);
        email.setTo(toEmail);
        email.setSubject("New Lead: " + (msg.getSubject() != null ? msg.getSubject() : "Contact Form Submission"));
        email.setText(
            "New contact form submission:\n\n" +
            "Name:    " + msg.getName() + "\n" +
            "Email:   " + msg.getEmail() + "\n" +
            "Phone:   " + msg.getPhone() + "\n" +
            "Subject: " + msg.getSubject() + "\n\n" +
            "Message:\n" + msg.getMessage() + "\n\n" +
            "Submitted at: " + msg.getCreatedAt()
        );
        mailSender.send(email);
        System.out.println("Notification sent for: " + msg.getEmail());
    }
}