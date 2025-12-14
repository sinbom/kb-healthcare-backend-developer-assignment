package com.kb.healthcare.user.application.port.in.command;

import lombok.Builder;

@Builder
public record LoginUserCommand(
        String email,

        String password
) {
}
