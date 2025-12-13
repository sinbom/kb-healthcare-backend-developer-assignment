package com.kb.healthcare.user.application.port.in;

import com.kb.healthcare.user.application.port.in.command.SignUpUserCommand;

public interface SignUpUserUseCase {

    void signUp(SignUpUserCommand command);

}
