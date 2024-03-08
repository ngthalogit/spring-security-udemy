package org.nashtech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {
    @GetMapping("/oauth")
    public ResponseEntity<String> oauth(OAuth2AuthenticationToken token) {
        System.out.println(token.getPrincipal());
        return ResponseEntity.ok("Successfully logged in using GitHub & OAuth2");
    }
}
