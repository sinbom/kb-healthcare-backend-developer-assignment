package com.kb.healthcare.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.healthcare.exception.DefaultAccessDeniedHandler;
import com.kb.healthcare.exception.DefaultAuthenticationEntryPoint;
import com.kb.healthcare.user.application.port.out.VerifyJwtPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toStaticResources;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@Configuration
class SecurityConfiguration {

    @Bean
    PasswordEncoder passwordEncoder() {
        return createDelegatingPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            ObjectMapper objectMapper,
            VerifyJwtPort verifyJwtPort
    ) throws Exception {
        return httpSecurity.authorizeHttpRequests(authorizeHttpRequest ->
                        authorizeHttpRequest.requestMatchers(
                                        POST,
                                        "/api/v1/users",
                                        "/api/v1/users/login"
                                )
                                .permitAll()
                                .requestMatchers(
                                        "/error",
                                        "/swagger.html",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                )
                                .permitAll()
                                .requestMatchers(toStaticResources().atCommonLocations())
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(new DefaultAuthenticationEntryPoint(objectMapper))
                                .accessDeniedHandler(new DefaultAccessDeniedHandler(objectMapper))
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(verifyJwtPort),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

}
