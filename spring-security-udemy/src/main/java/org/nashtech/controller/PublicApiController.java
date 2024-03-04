package org.nashtech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("public")
public class PublicApiController {
    private final Map<Integer, String> sensitives = new HashMap<>() {
        {
            put(1, "The first sensitive data from public api");
            put(2, "The second sensitive data public api");
            put(3, "The third sensitive data public api");
        }
    };

    @GetMapping("/home")
    public ResponseEntity<String> getNotices() {
        return ResponseEntity.ok("Public homepage accessed without any security");
    }

    @PostMapping("/sensitive/message")
    public ResponseEntity<String> getSensitiveMessage(@RequestParam Integer key) {
        return ResponseEntity.ok(Optional.ofNullable(this.sensitives.get(key)).orElse("Could not found data with key = " + key));
    }

    /* This request is not allowed as it has not been configurated to satisfy csrf in web security configuration */
    @PostMapping("/sensitive/code")
    public ResponseEntity<String> getSensitiveNumbers() {
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }
}
