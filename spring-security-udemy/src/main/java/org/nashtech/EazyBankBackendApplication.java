package org.nashtech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
/* The following annotation show all spring security filters in debug mode */
//@EnableWebSecurity
public class EazyBankBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(EazyBankBackendApplication.class, args);
    }
}
