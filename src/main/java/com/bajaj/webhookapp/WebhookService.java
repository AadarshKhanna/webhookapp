package com.bajaj.webhookapp;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void executeWorkflow() {
        String generateUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payload = Map.of(
                "name", "Aadarsh Khanna",
                "regNo", "REG12347",
                "email", "your@email.com"
        );

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(generateUrl, entity, JsonNode.class);

        String webhookUrl = response.getBody().get("webhook").asText();
        String accessToken = response.getBody().get("accessToken").asText();

        String finalQuery = "SELECT p.amount AS SALARY, " +
                "CONCAT(e.first_name, ' ', e.last_name) AS NAME, " +
                "TIMESTAMPDIFF(YEAR, e.dob, CURDATE()) AS AGE, " +
                "d.department_name AS DEPARTMENT_NAME " +
                "FROM PAYMENTS p " +
                "JOIN EMPLOYEE e ON p.emp_id = e.emp_id " +
                "JOIN DEPARTMENT d ON e.department = d.department_id " +
                "WHERE DAY(p.payment_time) != 1 " +
                "ORDER BY p.amount DESC " +
                "LIMIT 1;";

        HttpHeaders submitHeaders = new HttpHeaders();
        submitHeaders.setBearerAuth(accessToken);
        submitHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> submissionBody = Map.of("finalQuery", finalQuery);
        HttpEntity<Map<String, String>> submission = new HttpEntity<>(submissionBody, submitHeaders);

        restTemplate.postForEntity(webhookUrl, submission, String.class);
    }
}
