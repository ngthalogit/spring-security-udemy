package org.nashtech.config;

import org.nashtech.model.Authority;
import org.nashtech.model.Customer;
import org.nashtech.repository.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EazyBankUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    public EazyBankUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Customer> customers = customerRepository.findByEmail(username);
        if (customers.isEmpty()) {
            throw new UsernameNotFoundException("User details not found for the user: " + username);
        }
        String password = customers.get(0).getPwd();
        return new User(username, password, getGrantedAuthorities(customers.get(0).getAuthorities()));
    }
    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authoritySet) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        authoritySet.forEach(authority -> grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName())));
        return grantedAuthorities;
    }
}
