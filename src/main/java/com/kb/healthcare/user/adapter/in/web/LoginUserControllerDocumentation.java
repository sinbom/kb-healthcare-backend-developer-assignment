package com.kb.healthcare.user.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

import static com.kb.healthcare.configuration.SwaggerConfiguration.SECURITY_SCHEME_NAME;

abstract class LoginUserControllerDocumentation {

    @Operation(
            summary = "유저 로그인 API",
            tags = "User"
    )
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    abstract ResponseEntity<LoginUserResponse> login(LoginUserRequest request);

}
