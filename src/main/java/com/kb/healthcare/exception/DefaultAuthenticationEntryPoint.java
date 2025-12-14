package com.kb.healthcare.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

@RequiredArgsConstructor
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final HttpStatus RESPONSE_STATUS = UNAUTHORIZED;

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        RestErrorResponse errorResponse = new RestErrorResponse(
                RESPONSE_STATUS.value(),
                "로그인이 유효하지 않아요."
        );

        response.setContentType(APPLICATION_PROBLEM_JSON_VALUE);

        response.setCharacterEncoding(UTF_8.name());

        response.setStatus(RESPONSE_STATUS.value());

        response.getWriter()
                .write(objectMapper.writeValueAsString(errorResponse));
    }

}
