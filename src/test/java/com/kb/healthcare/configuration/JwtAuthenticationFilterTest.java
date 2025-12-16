package com.kb.healthcare.configuration;

import com.kb.healthcare.user.application.port.out.VerifyJwtPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@ExtendWith(value = MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private VerifyJwtPort verifyJwtPort;

    @Test
    @DisplayName(value = "유효한 토큰이면 인증이 설정되고 이후에는 초기화된다.")
    void doFilterWithValidToken() throws ServletException, IOException {
        // given
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(verifyJwtPort);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AUTHORIZATION, "Bearer valid.token");

        when(verifyJwtPort.verify("valid.token")).thenReturn(true);
        when(verifyJwtPort.extractSubject("valid.token")).thenReturn("123");

        // when
        filter.doFilterInternal(
                request,
                new MockHttpServletResponse(),
                new AssertingAuthFilterChain(true)
        );

        // then
        assertThat(getContext().getAuthentication()).isNull();
        verify(verifyJwtPort).verify("valid.token");
        verify(verifyJwtPort).extractSubject("valid.token");
    }

    @Test
    @DisplayName(value = "Authorization 헤더가 없으면 인증을 설정하지 않는다.")
    void doFilterWithoutHeader() throws ServletException, IOException {
        // given
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(verifyJwtPort);

        // when
        filter.doFilterInternal(
                new MockHttpServletRequest(),
                new MockHttpServletResponse(),
                new AssertingAuthFilterChain(false)
        );

        // then
        assertThat(getContext().getAuthentication()).isNull();
        verify(verifyJwtPort, never()).verify(anyString());
    }

    @Test
    @DisplayName(value = "Bearer 접두사가 아니면 인증을 설정하지 않는다.")
    void doFilterWithInvalidPrefix() throws ServletException, IOException {
        // given
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(verifyJwtPort);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AUTHORIZATION, "Token abc");

        // when
        filter.doFilterInternal(
                request,
                new MockHttpServletResponse(),
                new AssertingAuthFilterChain(false)
        );

        // then
        assertThat(getContext().getAuthentication()).isNull();
        verify(verifyJwtPort, never()).verify(anyString());
    }

    @Test
    @DisplayName(value = "토큰 검증이 실패하면 인증을 설정하지 않는다.")
    void doFilterWhenVerifyFalse() throws ServletException, IOException {
        // given
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(verifyJwtPort);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AUTHORIZATION, "Bearer bad.token");

        when(verifyJwtPort.verify("bad.token")).thenReturn(false);

        // when
        filter.doFilterInternal(
                request,
                new MockHttpServletResponse(),
                new AssertingAuthFilterChain(false)
        );

        // then
        assertThat(getContext().getAuthentication()).isNull();
        verify(verifyJwtPort).verify("bad.token");
        verify(verifyJwtPort, never()).extractSubject(anyString());
    }

    @Test
    @DisplayName(value = "subject가 비어있으면 인증을 설정하지 않는다.")
    void doFilterWhenEmptySubject() throws ServletException, IOException {
        // given
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(verifyJwtPort);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AUTHORIZATION, "Bearer empty.subject");

        when(verifyJwtPort.verify("empty.subject")).thenReturn(true);
        when(verifyJwtPort.extractSubject("empty.subject")).thenReturn(null);

        // when
        filter.doFilterInternal(
                request,
                new MockHttpServletResponse(),
                new AssertingAuthFilterChain(false)
        );

        // then
        assertThat(getContext().getAuthentication()).isNull();
        verify(verifyJwtPort).verify("empty.subject");
        verify(verifyJwtPort).extractSubject("empty.subject");
    }

    private record AssertingAuthFilterChain(boolean expectAuthenticated) implements FilterChain {

        @Override
        public void doFilter(
                ServletRequest request,
                ServletResponse response
        ) {
            if (expectAuthenticated) {
                assertThat(getContext().getAuthentication()).isNotNull();
                assertThat(getContext().getAuthentication().isAuthenticated()).isTrue();
            } else {
                assertThat(getContext().getAuthentication()).isNull();
            }
        }

    }

}
