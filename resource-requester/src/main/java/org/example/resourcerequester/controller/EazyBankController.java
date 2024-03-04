package org.example.resourcerequester.controller;

import org.example.resourcerequester.model.HttpRequestInput;
import org.example.resourcerequester.service.EazyBankHttpRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class EazyBankController {
    private final Logger LOGGER = LoggerFactory.getLogger(EazyBankController.class);
    private final EazyBankHttpRequestService eazyBankHttpRequestService;

    public EazyBankController(EazyBankHttpRequestService eazyBankHttpRequestService) {
        this.eazyBankHttpRequestService = eazyBankHttpRequestService;
    }
    /* This api does not violate cors as it is configurated by server */
    @GetMapping("/client/notices")
    public ResponseEntity<String> getNotices() {
        String[] headers = {"Content-Type", "application/json"};
        HttpRequestInput httpRequestInput = HttpRequestInput.builder()
                .uri(URI.create("http://localhost:8080/notices"))
                .method(HttpMethod.GET)
                .headers(headers)
                .build();
        try {
            HttpRequest request = eazyBankHttpRequestService.build(httpRequestInput);
            HttpResponse<String> response = (HttpResponse<String>) eazyBankHttpRequestService.send(request);
            return ResponseEntity.ok(response.body());
        } catch (Exception e) {
            LOGGER.info("Exception occurred when request to http://localhost:8080/notices, :" + e.getMessage());
        }
        return null;
    }
}
