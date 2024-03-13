package com.example.resourceserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class SecuredController {
    @GetMapping("message")
    public ResponseEntity<String> getMessage() {
        return ResponseEntity.ok("Successfully request to resource server");
    }


    @GetMapping("report")
    public ResponseEntity<String> getReport() {
        return ResponseEntity.ok("""
                One of the most captivating natural
                events of the year in many areas throughout North America is
                the turning of the leaves in the fall.The colours are magnificent,
                but the question of exactly why some trees turn yellow or orange,
                and others red or purple, is something which has long puzzled scientists.
                """);
    }

}
