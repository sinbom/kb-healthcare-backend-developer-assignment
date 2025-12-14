package com.kb.healthcare.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

@RequiredArgsConstructor
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

    private static final HttpStatus RESPONSE_STATUS = FORBIDDEN;

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        RestErrorResponse errorResponse = new RestErrorResponse(
                RESPONSE_STATUS.value(),
                "접근 권한이 없어요."
        );

        response.setContentType(APPLICATION_PROBLEM_JSON_VALUE);

        response.setCharacterEncoding(UTF_8.name());

        response.setStatus(RESPONSE_STATUS.value());

        response.getWriter()
                .write(objectMapper.writeValueAsString(errorResponse));
    }

}
