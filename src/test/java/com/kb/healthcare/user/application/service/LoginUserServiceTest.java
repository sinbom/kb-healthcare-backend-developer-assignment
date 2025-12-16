package com.kb.healthcare.user.application.service;

import com.kb.healthcare.user.application.port.in.command.LoginUserCommand;
import com.kb.healthcare.user.application.port.out.FindUserPort;
import com.kb.healthcare.user.application.port.out.SignJwtPort;
import com.kb.healthcare.user.domain.User;
import com.kb.healthcare.user.domain.UserToken;
import com.kb.healthcare.user.exception.UserNotExistsException;
import com.kb.healthcare.user.exception.UserPasswordNotMatchedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
class LoginUserServiceTest {

    @InjectMocks
    private LoginUserService loginUserService;

    @Mock
    private FindUserPort findUserPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SignJwtPort signJwtPort;

    @Test
    @DisplayName(value = "로그인한다.")
    void login() {
        // given
        LoginUserCommand command = create();

        User saved = User.builder()
                .id(randomUUID().toString())
                .email(command.email())
                .password("{bcrypt}encoded")
                .build();

        String jwt = "signed.jwt.token";

        when(findUserPort.findByEmail(command.email())).thenReturn(of(saved));
        when(passwordEncoder.matches(command.password(), saved.password())).thenReturn(true);
        when(signJwtPort.sign(saved.id())).thenReturn(jwt);

        // when
        UserToken token = loginUserService.login(command);

        // then
        assertThat(token).isNotNull();
        assertThat(token.accessToken()).isEqualTo(jwt);
        verify(findUserPort).findByEmail(command.email());
        verify(passwordEncoder).matches(command.password(), saved.password());
        verify(signJwtPort).sign(saved.id());
    }

    @Test
    @DisplayName(value = "존재하지 않는 이메일로 로그인할 수 없다.")
    void loginWhenUserNotFound() {
        // given
        LoginUserCommand command = create();

        when(findUserPort.findByEmail(command.email())).thenReturn(empty());

        // when & then
        assertThatThrownBy(() -> loginUserService.login(command))
                .isInstanceOf(UserNotExistsException.class);

        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(signJwtPort, never()).sign(anyString());
    }

    @Test
    @DisplayName(value = "잘못된 비밀번호로 로그인할 수 없다.")
    void loginWhenPasswordNotMatched() {
        // given
        LoginUserCommand command = create();

        User saved = User.builder()
                .id(randomUUID().toString())
                .email(command.email())
                .password("{bcrypt}encoded")
                .build();

        when(findUserPort.findByEmail(command.email())).thenReturn(of(saved));
        when(passwordEncoder.matches(command.password(), saved.password())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> loginUserService.login(command))
                .isInstanceOf(UserPasswordNotMatchedException.class);

        verify(signJwtPort, never()).sign(anyString());
    }

    private LoginUserCommand create() {
        return LoginUserCommand.builder()
                .email("gildong@example.com")
                .password("plain-pass")
                .build();
    }

}
