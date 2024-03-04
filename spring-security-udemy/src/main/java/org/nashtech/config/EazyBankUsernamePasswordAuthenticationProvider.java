package org.nashtech.config;

import org.nashtech.model.Customer;
import org.nashtech.service.CustomerService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class EazyBankUsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final EazyBankUserDetailsService eazyBankUserDetailsService;

    public EazyBankUsernamePasswordAuthenticationProvider(PasswordEncoder passwordEncoder, EazyBankUserDetailsService eazyBankUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.eazyBankUserDetailsService = eazyBankUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        try {
            UserDetails loadedUser = this.eazyBankUserDetailsService.loadUserByUsername(username);
            if (Objects.isNull(password) || !passwordEncoder.matches(password, loadedUser.getPassword())) {
                throw new BadCredentialsException("Bad credentials: Invalid password !!!");
            }
            return new UsernamePasswordAuthenticationToken(username, password, loadedUser.getAuthorities());
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
