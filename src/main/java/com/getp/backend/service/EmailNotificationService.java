package com.getp.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.getp.backend.entity.ContactMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailNotificationService {

	@Value("${resend.api.key}")
	private String resendApiKey;

	@Value("${notification.email.to}")
	private String toEmail;

	public void sendNotification(ContactMessage msg) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(resendApiKey);

		Map<String, Object> body = new HashMap<>();
		body.put("from", "GETP Leads <onboarding@resend.dev>");
		body.put("to", List.of(toEmail));
		body.put("subject", msg.getSubject() != null ? msg.getSubject() : "Contact Form Submission");

		String text = String.format(
				"New contact form submission:\n\n"
						+ "Name: %s\nEmail: %s\nPhone: %s\nSubject: %s\n\nMessage:\n%s\n\nSubmitted at: %s",
				msg.getName(), msg.getEmail(), msg.getPhone(), msg.getSubject(), msg.getMessage(), msg.getCreatedAt());
		body.put("text", text);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<String> response = restTemplate.postForEntity("https://api.resend.com/emails", request,
				String.class);

		System.out.println("Resend response: " + response.getStatusCode());
		System.out.println("Resend body: " + response.getBody());
	}
}