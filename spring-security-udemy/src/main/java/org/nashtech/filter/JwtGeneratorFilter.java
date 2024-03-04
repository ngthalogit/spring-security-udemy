package org.nashtech.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.nashtech.constant.SecurityConstant.JWT_KEY;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authn = SecurityContextHolder.getContext().getAuthentication();
        Optional.ofNullable(authn).ifPresent((auth) -> {
            Date now = new Date();
            Date expired = new Date(now.getTime() + 300000);
            SecretKey key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .setIssuer("Nashtech")
                    .setSubject("Jwt")
                    .claim("username", auth.getPrincipal())
                    .claim("authorities", getAuthorities(auth.getAuthorities()))
                    .setIssuedAt(now)
                    .setIssuedAt(expired)
                    .signWith(key)
                    .compact();
            response.setHeader(AUTHORIZATION, jwt);
        });
    }
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }

    private String getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> returnedAuthorities = new HashSet<>();
        authorities.stream().forEach((role) -> returnedAuthorities.add(role.getAuthority()));
        return String.join(",", returnedAuthorities);
    }
}
