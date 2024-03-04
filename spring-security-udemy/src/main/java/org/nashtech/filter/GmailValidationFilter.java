package org.nashtech.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


public class GmailValidationFilter implements Filter {
    private final String GMAIL_SUFFIX = "@gmail.com";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String email = request.getParameter("username");
        Optional.ofNullable(email).ifPresent(e -> {
            if (!email.endsWith(GMAIL_SUFFIX)) {
                httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                throw new BadCredentialsException("Invalid form gmail");
            }
        });
        chain.doFilter(request, httpServletResponse);
    }
}
