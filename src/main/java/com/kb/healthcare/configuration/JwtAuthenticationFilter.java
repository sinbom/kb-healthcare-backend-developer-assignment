package com.kb.healthcare.configuration;

import com.kb.healthcare.user.application.port.out.VerifyJwtPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.core.context.SecurityContextHolder.clearContext;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    private final VerifyJwtPort verifyJwtPort;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = extractBearerToken(request);

        if (verifyJwtPort.verify(token)) {
            attemptAuthentication(token);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            clearContext();
        }
    }

    private String extractBearerToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION);

        if (!hasText(bearerToken)) {
            return null;
        }

        if (!bearerToken.startsWith(BEARER_TOKEN_PREFIX)) {
            return null;
        }

        return bearerToken.substring(BEARER_TOKEN_PREFIX.length());
    }

    private void attemptAuthentication(String token) {
        String subject = verifyJwtPort.extractSubject(token);

        if (!hasText(subject)) {
            return;
        }

        UserDetails userDetails = User.builder()
                .username(subject)
                .password("erase")
                .build();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        getContext().setAuthentication(authenticationToken);
    }

}
