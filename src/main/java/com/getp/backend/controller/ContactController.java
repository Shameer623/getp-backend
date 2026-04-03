package com.getp.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.getp.backend.entity.ContactMessage;
import com.getp.backend.service.ContactService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<?> submitContact(@Valid @RequestBody ContactMessage contactMessage) {
        contactService.saveContact(contactMessage);
        return ResponseEntity.ok("Message submitted successfully");
    }
}