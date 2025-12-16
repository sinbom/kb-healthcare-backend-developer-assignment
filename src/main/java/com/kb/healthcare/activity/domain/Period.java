package com.kb.healthcare.activity.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Period(
        LocalDateTime from,
        LocalDateTime to
) {
}
