package com.kb.healthcare.activity.domain;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DailyActivityKey(
        String userId,

        LocalDate date
) {
}
