package com.kb.healthcare.activity.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record Activity(
        String id,

        ActivityType type,

        String userId,

        Period period,

        Calorie calories,

        Distance distance,

        BigDecimal steps,

        Source source,

        LocalDateTime createdAt
) {
}
