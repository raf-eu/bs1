package com.example.holidays.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityProperties securityProperties;
    private static final String USER_ROLE = "USER";
    private static final String SPRING_ACTUATOR_ENDPOINT = "/actuator/**";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, SPRING_ACTUATOR_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().csrf().disable().build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails userDetails = User.withUsername(securityProperties.getUserName())
                .password(encoder.encode(securityProperties.getPassword()))
                .roles(USER_ROLE)
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
