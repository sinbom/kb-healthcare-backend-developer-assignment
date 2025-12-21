package com.kb.healthcare.activity.adapter.in.web;

import com.kb.healthcare.activity.domain.ActivityType;
import com.kb.healthcare.activity.domain.CalorieUnit;
import com.kb.healthcare.activity.domain.DistanceUnit;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
record FindAggregatedActivityResponse(
        Metadata metadata,
        List<AggregatedActivity> contents
) {

    @Builder
    record Metadata(
            int page,
            int limit,
            long totalCount
    ) {

    }

    @Builder
    record AggregatedActivity(
            String id,

            ActivityType type,

            String recordKey,

            Calorie calorie,

            Distance distance,

            BigDecimal steps,

            LocalDate date
    ) {
    }

    @Builder
    record Calorie(
            @NotNull(message = "소모 칼로리 단위가 없어요.")
            CalorieUnit unit,

            @NotNull(message = "소모 칼로리 값이 없어요.")
            BigDecimal value
    ) {
    }

    @Builder
    record Distance(
            @NotNull(message = "이동거리 단위가 없어요.")
            DistanceUnit unit,

            @NotNull(message = "이동거리 값이 없어요.")
            BigDecimal value
    ) {
    }

}
