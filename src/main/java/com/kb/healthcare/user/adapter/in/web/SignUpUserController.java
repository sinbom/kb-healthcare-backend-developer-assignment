package com.kb.healthcare.user.adapter.in.web;

import com.kb.healthcare.user.adapter.in.web.request.SignUpUserRequest;
import com.kb.healthcare.user.application.port.in.command.SignUpUserCommand;
import com.kb.healthcare.user.application.service.SignUpUserService;
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
class SignUpUserController {

    private final SignUpUserService signUpUserService;

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

        signUpUserService.signUp(command);

        return noContent()
                .build();
    }

}
