package com.rikerik.BookWave.Service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rikerik.BookWave.Exception.HttpRequestException;
import com.rikerik.BookWave.Exception.JsonConversionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SummaryService {
    private final RestTemplate restTemplate;
    private final String aiModelUrl;

    public SummaryService(RestTemplate restTemplate, @Value("${ai.model.url}") String aiModelUrl) {
        this.restTemplate = restTemplate;
        this.aiModelUrl = aiModelUrl;
    }

    public String extractText(String text) {
        if (text == null) {
            throw new IllegalArgumentException(
                    "Both text and file are null. Please provide either text or upload a file.");
        }

        log.info("Using provided text for summarization: " + text);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        // set the header to accept json

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;

        try {
            // Convert text into JSON format
            requestBody = objectMapper.writeValueAsString(Map.of("text", text));
        } catch (JsonProcessingException e) {
            log.error("Failed to convert text to JSON", e.getMessage());
            throw new JsonConversionException("Failed to convert text to JSON", e);
        }

        log.info("Sending request to URL: " + aiModelUrl);
        log.debug("Request Body: " + requestBody);

        // HTTP request entity with the json formatted request body and header
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, header);

        // post request to the python script, using RestTemplate
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(aiModelUrl, HttpMethod.POST, requestEntity, String.class);
        } catch (RestClientException e) {
            log.error("Error during HTTP request", e.getMessage());
            throw new HttpRequestException("Error during HTTP request", e);
        }

        log.debug("Response Status Code: " + responseEntity.getStatusCode());
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            log.info("Text successfully summarized");
            return responseEntity.getBody();
        } else {
            log.error("Failed to get summarized text! Status code: " + responseEntity.getStatusCode());
            throw new HttpRequestException(
                    "Failed to get summarized text! Status code: " + responseEntity.getStatusCode(), null);
        }
    }
}
