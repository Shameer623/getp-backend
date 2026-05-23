package com.getp.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KeepAliveService {

    @Value("${app.self.url:http://localhost:8080}")
    private String selfUrl;

    // Pings every 10 minutes to prevent Render cold start
    @Scheduled(fixedDelay = 600000)
    public void keepAlive() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject(selfUrl + "/api/health", String.class);
            System.out.println("Keep-alive ping sent");
        } catch (Exception e) {
            System.out.println("Keep-alive failed: " + e.getMessage());
        }
    }
}