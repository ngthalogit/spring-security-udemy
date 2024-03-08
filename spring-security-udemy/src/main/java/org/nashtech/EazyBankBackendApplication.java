package org.nashtech;

import org.nashtech.config.OAuth2GitHubConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
/* The following annotation show all spring security filters in debug mode */
@EnableWebSecurity
/* Enable method level security, prePostEnabled default value is True -> no need to set*/
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
//@EnableConfigurationProperties(OAuth2GitHubConfig.class)
public class EazyBankBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(EazyBankBackendApplication.class, args);
    }
}
