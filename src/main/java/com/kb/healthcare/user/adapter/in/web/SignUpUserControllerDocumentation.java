package com.kb.healthcare.user.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

import static com.kb.healthcare.configuration.SwaggerConfiguration.SECURITY_SCHEME_NAME;

abstract class SignUpUserControllerDocumentation {

    @Operation(
            summary = "유저 회원가입 API",
            tags = "User"
    )
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    abstract ResponseEntity<Void> signUp(SignUpUserRequest request);

}
