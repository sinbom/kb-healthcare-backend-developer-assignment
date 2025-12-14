package com.kb.healthcare.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.healthcare.exception.DefaultAccessDeniedHandler;
import com.kb.healthcare.exception.DefaultAuthenticationEntryPoint;
import com.kb.healthcare.user.adapter.out.jwt.JwsTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static java.security.KeyPairGenerator.getInstance;
import static java.time.Duration.ofHours;
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
    JwsTokenProvider jwtTokenProvider() {
        KeyPairGenerator keyPairGenerator; // 실제 프로덕션 환경에서는 KeyPair를 외부에서 주입받아 사용

        try {
            keyPairGenerator = getInstance("RSA");

            keyPairGenerator.initialize(2048);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        return JwsTokenProvider.builder()
                .tokenIssuer("kb-healthcare-application")
                .tokenTimeToLive(ofHours(1L))
                .publicKey(keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            ObjectMapper objectMapper
    ) throws Exception {
        return httpSecurity.authorizeHttpRequests(authorizeHttpRequest ->
                        authorizeHttpRequest.requestMatchers(
                                        POST,
                                        "/api/v1/users",
                                        "/api/v1/users/login"
                                )
                                .permitAll()
                                .requestMatchers("/error")
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
                        new JwtAuthenticationFilter(jwtTokenProvider()),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

}
