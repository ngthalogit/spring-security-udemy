package org.example.resourcerequester.service;

import org.example.resourcerequester.model.HttpRequestInput;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;

@Service
public class EazyBankHttpRequestServiceImpl implements EazyBankHttpRequestService {
    private HttpClient httpClient;

    @Override
    public HttpResponse<?> send(HttpRequest httpRequest) throws IOException, InterruptedException {
        return this.httpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public HttpRequest build(HttpRequestInput httpRequestInput) {

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(httpRequestInput.getUri())
                .method(httpRequestInput.getMethod().toString(),
                        Optional
                                .ofNullable(httpRequestInput.getBodyPublisher())
                                .orElse(HttpRequest.BodyPublishers.noBody())
                );
        Optional.ofNullable(httpRequestInput.getHeaders()).ifPresent(requestBuilder::headers);

        return requestBuilder.build();
    }
}
