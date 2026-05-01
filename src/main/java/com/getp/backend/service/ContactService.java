package com.getp.backend.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.getp.backend.entity.ContactMessage;

@Service
public class ContactService {

    private final LeadStorageService leadStorageService;

    public ContactService(LeadStorageService leadStorageService) {
        this.leadStorageService = leadStorageService;
    }

    public void saveContact(ContactMessage contactMessage) {

        contactMessage.setCreatedAt(LocalDateTime.now());

        // async fire and forget
        new Thread(() -> {
            try {
                leadStorageService.saveLead(contactMessage);
            } catch (Exception e) {
                System.out.println("Lead save failed: " + e.getMessage());
            }
        }).start();
    }
}