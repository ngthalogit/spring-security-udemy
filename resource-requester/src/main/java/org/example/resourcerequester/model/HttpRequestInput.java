package org.example.resourcerequester.model;

import lombok.*;
import org.springframework.http.HttpMethod;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpRequestInput {
    private URI uri;
    private HttpMethod method;
    private String[] headers;
    private Duration timeout;
    private HttpRequest.BodyPublisher bodyPublisher;
}
