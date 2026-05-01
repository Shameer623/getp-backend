package com.getp.backend.service;

import com.getp.backend.entity.ContactMessage;

public interface LeadStorageService {
    void saveLead(ContactMessage contactMessage);
}