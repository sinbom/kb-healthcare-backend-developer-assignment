package com.kb.healthcare.activity.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record DailyActivity(
        String id,

        ActivityType type,

        String userId,

        Calorie calories,

        Distance distance,

        BigDecimal steps,

        LocalDate date,

        LocalDateTime createdAt
) {
}
