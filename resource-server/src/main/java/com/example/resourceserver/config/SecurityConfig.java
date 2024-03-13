package com.example.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter tokenAuthenticationConverter = new JwtAuthenticationConverter();
        tokenAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());

        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (authz) -> authz
                                .requestMatchers("/api/message").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/api/report").hasRole("AUTHOR")
                                .requestMatchers("/api/**")
                                .authenticated()
                                .requestMatchers("/internal/**")
                                .permitAll()
                )
                .oauth2ResourceServer(
                        (resourceServerConfigurer) -> resourceServerConfigurer
                                .jwt(
                                        (jwtConfigurer) -> jwtConfigurer
                                                .jwtAuthenticationConverter(tokenAuthenticationConverter)
                                )
                )
        ;

        return http.build();
    }


}
