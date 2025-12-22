package com.kb.healthcare.activity.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

import static com.kb.healthcare.configuration.SwaggerConfiguration.SECURITY_SCHEME_NAME;

abstract class SaveActivityControllerDocumentation {

    @Operation(
            summary = "활동 데이터 저장 API",
            tags = "Activity"
    )
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    abstract ResponseEntity<Void> save(SaveActivitiesRequest request);

}
