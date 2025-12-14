package com.kb.healthcare.user.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record User(
        String id,

        String name,

        String nickname,

        String email,

        String password,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
