package com.kb.healthcare.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static com.kb.healthcare.configuration.SwaggerConfiguration.SECURITY_SCHEME_NAME;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "KB HealthCare assignment HTTP API specification",
                description = "과제에서 구현한 HTTP API에 대한 명세를 확인하실 수 있습니다.",
                version = "1.0.0"
        )
)
@SecurityScheme(
        name = SECURITY_SCHEME_NAME,
        type = HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfiguration {

    public static final String SECURITY_SCHEME_NAME = "Bearer Authentication";

}
