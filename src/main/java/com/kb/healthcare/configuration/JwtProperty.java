package com.kb.healthcare.configuration;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperty(
        @NotBlank
        String privateKey,

        @NotBlank
        String publicKey
) {
}
