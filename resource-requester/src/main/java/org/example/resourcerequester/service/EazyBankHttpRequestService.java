package org.example.resourcerequester.service;

import org.example.resourcerequester.model.HttpRequestInput;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface EazyBankHttpRequestService {
    HttpResponse<?> send(HttpRequest httpRequest) throws IOException, InterruptedException;
    HttpRequest build(HttpRequestInput httpRequestInput);
}
