package org.nashtech.controller;

import org.nashtech.model.RegisterCustomer;
import org.nashtech.model.Something;
import org.nashtech.service.CustomerService;
import org.nashtech.service.CustomerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("public")
public class PublicApiController {
    private final CustomerService customerService;
    private final Map<Integer, String> sensitives = new HashMap<>() {
        {
            put(1, "The first sensitive data from public api");
            put(2, "The second sensitive data public api");
            put(3, "The third sensitive data public api");
        }
    };

    public PublicApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody RegisterCustomer registerCustomer) {
        String email = registerCustomer.getEmail();
        if (!customerService.findByEmail(email)) {
            return new ResponseEntity<>("email address existed, please try again !", HttpStatus.BAD_REQUEST);
        }
        customerService.saveCustomer(CustomerServiceImpl.customerWithRegister(registerCustomer));
        return ResponseEntity.ok("create user successfully !!");
    }

    @PostMapping("/postSomething")
//    @PreFilter("filterObject.head != 'The story of silk'")
    @PostFilter("filterObject.head != 'The story of silk'")
    public ResponseEntity<List<String>> postSomething(@RequestBody List<Something> somethings) {
        List<String> upperCaseBody = somethings.stream().map(Something::getBody).map(String::toUpperCase).toList();
        return ResponseEntity.ok(upperCaseBody);
    }

    @GetMapping("/home")
    public ResponseEntity<String> getNotices() {
        return ResponseEntity.ok("Public homepage accessed without any security");
    }

    @PostMapping("/sensitive/message")
    public ResponseEntity<String> getSensitiveMessage(@RequestParam Integer key) {
        return ResponseEntity.ok(Optional.ofNullable(this.sensitives.get(key)).orElse("Could not found data with key = " + key));
    }

    /* This request is not allowed as it has not been configured to satisfy csrf in web security configuration */
    @PostMapping("/sensitive/code")
    public ResponseEntity<String> getSensitiveNumbers() {
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }
}
