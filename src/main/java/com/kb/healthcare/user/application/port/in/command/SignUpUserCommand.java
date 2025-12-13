package com.kb.healthcare.user.application.port.in.command;

import lombok.Builder;

@Builder
public record SignUpUserCommand(
        String name,

        String nickname,

        String email,

        String password
) {
}
