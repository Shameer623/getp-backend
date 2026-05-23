package com.getp.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.getp.backend.entity.ContactMessage;

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

		String body = String.format(
				"""
						{
						    "from": "GETP Leads <onboarding@resend.dev>",
						    "to": ["%s"],
						    "subject": "New Lead: %s",
						    "text": "New contact form submission:\\n\\nName: %s\\nEmail: %s\\nPhone: %s\\nSubject: %s\\n\\nMessage:\\n%s\\n\\nSubmitted at: %s"
						}
						""",
				toEmail, msg.getSubject() != null ? msg.getSubject() : "Contact Form Submission", msg.getName(),
				msg.getEmail(), msg.getPhone(), msg.getSubject(), msg.getMessage(), msg.getCreatedAt());

		HttpEntity<String> request = new HttpEntity<>(body, headers);

		ResponseEntity<String> response = restTemplate.postForEntity("https://api.resend.com/emails", request,
				String.class);

		System.out.println("Resend response: " + response.getStatusCode());
	}
}