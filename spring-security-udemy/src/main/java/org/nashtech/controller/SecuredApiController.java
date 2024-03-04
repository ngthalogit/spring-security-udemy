package org.nashtech.controller;

import org.nashtech.model.Customer;
import org.nashtech.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("secured")
public class SecuredApiController {
    private final CustomerService customerService;
    private final Map<Integer, String> sensitives = new HashMap<>() {
        {
            put(1, "The first sensitive data from secured api");
            put(2, "The second sensitive data secured api");
            put(3, "The third sensitive data secured api");
        }
    };

    public SecuredApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/home")
    public ResponseEntity<String> getAccountDetail() {
        return ResponseEntity.ok("Secured homepage accessed successfully after logging");
    }

    @PostMapping("/sensitive/message")
    public ResponseEntity<String> getSensitiveMessage(@RequestParam Integer key) {
        return ResponseEntity.ok(
                Optional
                        .ofNullable(this.sensitives.get(key))
                        .orElse("Could not found data with key = " + key)
        );
    }

    @GetMapping("/view/data1")
    public ResponseEntity<String> getViewData1() {
        return ResponseEntity.ok("This is data 1");
    }

    @GetMapping("/view/data2")
    public ResponseEntity<String> getViewData2() {
        return ResponseEntity.ok("This is data 2");
    }

    @GetMapping("/view/data3")
    public ResponseEntity<String> getViewData3() {
        return ResponseEntity.ok("This is data 3");
    }

    @GetMapping("/view/data4")
    public ResponseEntity<String> getViewData4() {
        return ResponseEntity.ok("This is data 4");
    }

    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> getAllCustomerDetails() {
        return ResponseEntity.ok(customerService.getAll());
    }
}
