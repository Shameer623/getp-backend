package com.getp.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.getp.backend.entity.ContactMessage;

@Service
public class GoogleSheetStorageService implements LeadStorageService {

    @Value("${google.sheet.url}")
    private String sheetUrl;

    @Override
    public void saveLead(ContactMessage contactMessage) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ContactMessage> request =
                new HttpEntity<>(contactMessage, headers);

        restTemplate.postForObject(sheetUrl, request, String.class);
    }
}