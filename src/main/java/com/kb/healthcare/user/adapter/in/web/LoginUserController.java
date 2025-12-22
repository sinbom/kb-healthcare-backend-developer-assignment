package com.kb.healthcare.user.adapter.in.web;

import com.kb.healthcare.user.application.port.in.LoginUserUseCase;
import com.kb.healthcare.user.application.port.in.command.LoginUserCommand;
import com.kb.healthcare.user.domain.UserToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
class LoginUserController extends LoginUserControllerDocumentation {

    private final LoginUserUseCase loginUserUseCase;

    @PostMapping(
            path = "/api/v1/users/login",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LoginUserResponse> login(@RequestBody @Valid LoginUserRequest request) {
        LoginUserCommand command = LoginUserCommand.builder()
                .email(request.email())
                .password(request.password())
                .build();

        UserToken userToken = loginUserUseCase.login(command);

        LoginUserResponse response = LoginUserResponse.builder()
                .accessToken(userToken.accessToken())
                .build();

        return ok(response);
    }

}
