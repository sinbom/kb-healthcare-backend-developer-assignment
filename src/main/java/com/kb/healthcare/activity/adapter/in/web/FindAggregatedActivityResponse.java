package com.kb.healthcare.activity.adapter.in.web;

import com.kb.healthcare.activity.domain.ActivityType;
import com.kb.healthcare.activity.domain.CalorieUnit;
import com.kb.healthcare.activity.domain.DistanceUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Schema(title = "일별/월별 활동 데이터 응답")
@Builder
record FindAggregatedActivityResponse(
        @Schema(description = "조회 메타데이터")
        Metadata metadata,

        @Schema(description = "조회 데이터")
        List<AggregatedActivity> contents
) {

    @Schema(description = "메타 데이터")
    @Builder
    record Metadata(
            @Schema(description = "조회 페이지")
            int page,

            @Schema(description = "조회 갯수")
            int limit,

            @Schema(description = "조회 전체 갯수")
            long totalCount
    ) {

    }

    @Schema(description = "활동")
    @Builder
    record AggregatedActivity(
            @Schema(description = "활동 Id")
            String id,

            @Schema(description = "활동 유형")
            ActivityType type,

            @Schema(description = "유저 구분 키")
            String recordKey,

            @Schema(description = "소모 칼로리")
            Calorie calories,

            @Schema(description = "이동거리")
            Distance distance,

            @Schema(description = "걸음 수")
            BigDecimal steps,

            @Schema(description = "집계 날짜")
            String date
    ) {
    }

    @Schema(description = "칼로리")
    @Builder
    record Calorie(
            @Schema(description = "칼로리 단위")
            @NotNull(message = "소모 칼로리 단위가 없어요.")
            CalorieUnit unit,

            @Schema(description = "칼로리")
            @NotNull(message = "소모 칼로리 값이 없어요.")
            BigDecimal value
    ) {
    }

    @Schema(description = "이동거리")
    @Builder
    record Distance(
            @Schema(description = "이동거리 단위")
            @NotNull(message = "이동거리 단위가 없어요.")
            DistanceUnit unit,

            @Schema(description = "이동거리")
            @NotNull(message = "이동거리 값이 없어요.")
            BigDecimal value
    ) {
    }

}
