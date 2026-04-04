package com.getp.backend.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.getp.backend.entity.ContactMessage;
import com.getp.backend.repository.ContactRepository;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final EmailService emailService;

    public ContactService(ContactRepository contactRepository, EmailService emailService) {
        this.contactRepository = contactRepository;
        this.emailService = emailService;
    }

    public ContactMessage saveContact(ContactMessage contactMessage) {
        contactMessage.setCreatedAt(java.time.LocalDateTime.now());

        ContactMessage saved = contactRepository.save(contactMessage);

        // Send email after saving
        new Thread(() -> {
            try {
                emailService.sendContactNotification(
                    contactMessage.getName(),
                    contactMessage.getEmail(),
                    contactMessage.getSubject(),
                    contactMessage.getMessage()
                );
            } catch (Exception e) {
                System.out.println("Email failed: " + e.getMessage());
            }
        }).start();

        

        return saved;
    }
}