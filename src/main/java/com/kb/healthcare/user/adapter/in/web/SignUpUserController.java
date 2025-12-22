package com.kb.healthcare.user.adapter.in.web;

import com.kb.healthcare.user.application.port.in.SignUpUserUseCase;
import com.kb.healthcare.user.application.port.in.command.SignUpUserCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequiredArgsConstructor
class SignUpUserController extends SignUpUserControllerDocumentation {

    private final SignUpUserUseCase signUpUserUseCase;

    @PostMapping(
            path = "/api/v1/users",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity<Void> signUp(@RequestBody @Valid SignUpUserRequest request) {
        SignUpUserCommand command = SignUpUserCommand.builder()
                .name(request.name())
                .nickname(request.nickname())
                .email(request.email())
                .password(request.password())
                .build();

        signUpUserUseCase.signUp(command);

        return noContent()
                .build();
    }

}
