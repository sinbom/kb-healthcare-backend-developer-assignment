package com.kb.healthcare.activity.domain;

import lombok.Builder;

import java.time.YearMonth;

@Builder
public record MonthlyActivityKey(
        String userId,

        YearMonth date
) {
}
