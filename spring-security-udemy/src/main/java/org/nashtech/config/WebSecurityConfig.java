package org.nashtech.config;

import jakarta.servlet.http.HttpServletRequest;
import org.nashtech.filter.CsrfCookieFilter;
import org.nashtech.filter.GmailValidationFilter;
import org.nashtech.filter.JwtAuthenticationFilter;
import org.nashtech.filter.JwtGeneratorFilter;
import org.nashtech.repository.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestHandler = new CsrfTokenRequestAttributeHandler();
        csrfTokenRequestHandler.setCsrfRequestAttributeName("_csrf");


        http
                // no need to onfig these lines as we using jwt
//                .securityContext(
//                        (context) ->
//                                context.requireExplicitSave(false)
//                )
//                .sessionManagement(
//                        (session) ->
//                                session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                )
                .sessionManagement(
                        (session) ->
                                // STATELESS tells spring boot not to create any JssessionID or historic
                                // HttpSection
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(
                        (authz) -> authz
                                .requestMatchers("secured/view/data1").hasAuthority("VIEWDATA1")
                                .requestMatchers("secured/view/data2").hasAuthority("VIEWDATA2")
                                .requestMatchers("secured/view/data3").hasAuthority("VIEWDATA3")
                                .requestMatchers("secured/view/data4").hasAuthority("VIEWDATA4")
                                .requestMatchers("secured/customer").hasAuthority("VIEWCUSTOMER")
                                .requestMatchers("/secured/**")
                                .authenticated()
                                .requestMatchers("/public/**")
                                .permitAll()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .cors(
                        (cors) -> cors
                                .configurationSource(configurationSource())
                )
                .csrf(
                        (csrf) -> csrf
                                .csrfTokenRequestHandler(csrfTokenRequestHandler)
                                .ignoringRequestMatchers("/public/sensitive/message")
                                // just allow the configurated pattern to make post request
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                ) // enable any POST or PUT request
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new GmailValidationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(), BasicAuthenticationFilter.class);

        /* Below is the custom security config of denying all requests */

        /*http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest()
                        .denyAll()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults());*/

        /* Below is the custom security config of permitting all requests */

        /*http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest()
                        .permitAll()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults());*/

        return http.build();
    }

    private CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:4500")); // config for Resource-Requester server
        corsConfig.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        corsConfig.setAllowedHeaders(List.of(AUTHORIZATION));
        corsConfig.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    //    @Bean
    public UserDetailsService userDetailsService(CustomerRepository customerRepository) {
        return new EazyBankUserDetailsService(customerRepository);
    }


    /* Below is the UserDetailsService configuration with JdbcUserDetailsManager implementation*/
//    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }


    /* Below is the UserDetailsService configuration with InMemoryUserDetailsManager implementation */
//    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password("admin")
                .passwordEncoder(passwordEncoder::encode)
                .authorities("ADMIN")
                .build();

        UserDetails reader = User.builder()
                .username("reader")
                .password("reader")
                .passwordEncoder(passwordEncoder::encode)
                .authorities("READER")
                .build();

        return new InMemoryUserDetailsManager(admin, reader);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
