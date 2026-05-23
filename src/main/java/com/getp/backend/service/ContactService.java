package com.getp.backend.service;

import java.time.LocalDateTime;
import java.util.concurrent.*;

import org.springframework.stereotype.Service;
import com.getp.backend.entity.ContactMessage;

@Service
public class ContactService {

    private final LeadStorageService leadStorageService;
    private final EmailNotificationService emailNotificationService;
    // Fixed thread pool — much better than raw new Thread()
    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    public ContactService(LeadStorageService leadStorageService,
                          EmailNotificationService emailNotificationService) {
        this.leadStorageService = leadStorageService;
        this.emailNotificationService = emailNotificationService;
    }

    public void saveContact(ContactMessage contactMessage) {
        contactMessage.setCreatedAt(LocalDateTime.now());

        // Save to Google Sheet async
        executor.submit(() -> {
            try {
                leadStorageService.saveLead(contactMessage);
            } catch (Exception e) {
                System.out.println("Sheet save failed: " + e.getMessage());
            }
        });

        // Send email notification async
        executor.submit(() -> {
            try {
                emailNotificationService.sendNotification(contactMessage);
            } catch (Exception e) {
                System.out.println("Email send failed: " + e.getMessage());
            }
        });
    }
}