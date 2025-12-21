package com.kb.healthcare.user.application.service;

import com.kb.healthcare.user.application.port.in.SignUpUserUseCase;
import com.kb.healthcare.user.application.port.in.command.SignUpUserCommand;
import com.kb.healthcare.user.application.port.out.CreateUserPort;
import com.kb.healthcare.user.application.port.out.FindUserPort;
import com.kb.healthcare.user.domain.User;
import com.kb.healthcare.user.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SignUpUserService implements SignUpUserUseCase {

    private final CreateUserPort createUserPort;

    private final FindUserPort findUserPort;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpUserCommand command) {
        findUserPort.findByEmail(command.email())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("이미 사용중인 이메일이에요.");
                });

        findUserPort.findByNickname(command.nickname())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("이미 사용중인 닉네임이에요.");
                });

        User user = User.builder()
                .name(command.name())
                .nickname(command.nickname())
                .email(command.email())
                .password(passwordEncoder.encode(command.password()))
                .build();

        createUserPort.create(user);
    }

}
