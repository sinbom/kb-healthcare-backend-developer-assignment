package com.kb.healthcare.user.application.service;

import com.kb.healthcare.user.application.port.in.LoginUserUseCase;
import com.kb.healthcare.user.application.port.in.command.LoginUserCommand;
import com.kb.healthcare.user.application.port.out.FindUserPort;
import com.kb.healthcare.user.application.port.out.SignJwtPort;
import com.kb.healthcare.user.domain.User;
import com.kb.healthcare.user.domain.UserToken;
import com.kb.healthcare.user.exception.UserNotExistsException;
import com.kb.healthcare.user.exception.UserPasswordNotMatchedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LoginUserService implements LoginUserUseCase {

    private final FindUserPort findUserPort;

    private final PasswordEncoder passwordEncoder;

    private final SignJwtPort signJwtPort;

    @Override
    public UserToken login(LoginUserCommand command) {
        User user = findUserPort.findByEmail(command.email())
                .orElseThrow(UserNotExistsException::new);

        boolean isPasswordMatched = passwordEncoder.matches(
                command.password(),
                user.password()
        );

        if (!isPasswordMatched) {
            throw new UserPasswordNotMatchedException();
        }

        return UserToken.builder()
                .accessToken(signJwtPort.sign(user.id()))
                .build();
    }

}
