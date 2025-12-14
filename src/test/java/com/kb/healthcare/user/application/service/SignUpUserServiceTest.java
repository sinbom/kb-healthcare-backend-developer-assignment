package com.kb.healthcare.user.application.service;

import com.kb.healthcare.user.application.port.in.command.SignUpUserCommand;
import com.kb.healthcare.user.application.port.out.CreateUserPort;
import com.kb.healthcare.user.application.port.out.FindUserPort;
import com.kb.healthcare.user.domain.User;
import com.kb.healthcare.user.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
class SignUpUserServiceTest {

    @InjectMocks
    private SignUpUserService signUpUserService;

    @Mock
    private CreateUserPort createUserPort;

    @Mock
    private FindUserPort findUserPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    private SignUpUserCommand command;

    @BeforeEach
    void setUp() {
        command = SignUpUserCommand.builder()
                .name("홍길동")
                .nickname("길동이")
                .email("gildong@example.com")
                .password("plain-pass")
                .build();
    }

    @Test
    @DisplayName(value = "회원가입한다.")
    void signUp() {
        // given
        when(passwordEncoder.encode(command.password())).thenReturn("{bcrypt}encoded");

        // when
        signUpUserService.signUp(command);

        // then
        verify(findUserPort).findByEmail(command.email());
        verify(findUserPort).findByNickname(command.nickname());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(createUserPort).create(captor.capture());
        verify(passwordEncoder).encode(command.password());

        User saved = captor.getValue();
        assertThat(saved).isNotNull();
        assertThat(saved.name()).isEqualTo(command.name());
        assertThat(saved.nickname()).isEqualTo(command.nickname());
        assertThat(saved.email()).isEqualTo(command.email());
        assertThat(saved.password()).isEqualTo("{bcrypt}encoded");
    }

    @Test
    @DisplayName(value = "이미 사용중인 이메일로 회원가입할 수 없다.")
    void signUpWhenDuplicatedEmail() {
        // given
        when(findUserPort.findByEmail(command.email())).thenReturn(of(User.builder().build()));

        // when & then
        assertThatThrownBy(() -> signUpUserService.signUp(command))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("이미 사용중인 이메일이에요.");

        verify(createUserPort, never()).create(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    @DisplayName(value = "이미 사용중인 닉네임으로 회원가입할 수 없다.")
    void signUpWhenDuplicatedNickname() {
        // given
        when(findUserPort.findByEmail(command.email())).thenReturn(empty());
        when(findUserPort.findByNickname(command.nickname())).thenReturn(of(User.builder().build()));

        // when & then
        assertThatThrownBy(() -> signUpUserService.signUp(command))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("이미 사용중인 닉네임이에요.");

        verify(createUserPort, never()).create(any());
        verify(passwordEncoder, never()).encode(any());
    }

}
