package com.kb.healthcare.user.application.port.in;

import com.kb.healthcare.user.application.port.in.command.LoginUserCommand;
import com.kb.healthcare.user.domain.UserToken;

public interface LoginUserUseCase {

    UserToken login(LoginUserCommand command);

}
