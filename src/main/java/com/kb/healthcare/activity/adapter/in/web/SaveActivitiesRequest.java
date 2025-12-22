package com.kb.healthcare.activity.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kb.healthcare.activity.domain.ActivityType;
import com.kb.healthcare.activity.domain.CalorieUnit;
import com.kb.healthcare.activity.domain.DistanceUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(title = "활동 데이터 저장 요청")
record SaveActivitiesRequest(
        @Schema(description = "유저 구분 키")
        @JsonProperty(value = "recordkey")
        @NotBlank(message = "사용자 구분 키를 입력해주세요.")
        String userId,

        @Schema(description = "활동 데이터")
        @NotNull(message = "데이터가 없어요.")
        @Valid Activities data,

        @Schema(description = "최종 업데이트 날짜")
        @NotNull(message = "최종 업데이트 날짜가 없어요.")
        @JsonDeserialize(using = MultiFormatLocalDateTimeDeserializer.class)
        LocalDateTime lastUpdate,

        @Schema(description = "활동 유형")
        @NotNull(message = "활동 유형이 없어요.")
        ActivityType type
) {

    @Schema(description = "활동")
    record Activities(
            @Schema(description = "메모")
            String memo,

            @Schema(description = "활동 데이터")
            @Size(
                    max = 1500,
                    message = "활동 데이터가 너무 많아요."
            )
            @NotEmpty(message = "데이터가 없어요.")
            List<@Valid Activity> entries,

            @Schema(description = "전송 대상 정보")
            @NotNull(message = "전송 대상 정보가 없어요.")
            Source source
    ) {

        @Schema(description = "활동")
        record Activity(
                @Schema(description = "활동 기간 정보")
                @NotNull(message = "활동 기간 정보가 없어요.")
                @Valid Period period,

                @Schema(description = "이동거리 정보")
                @NotNull(message = "이동거리 정보가 없어요.")
                @Valid Distance distance,

                @Schema(description = "소모 칼로리 정보")
                @NotNull(message = "소모 칼로리 정보가 없어요.")
                @Valid Calorie calories,

                @Schema(description = "활동 걸음 수")
                @NotNull(message = "활동 걸음 수가 없어요.")
                BigDecimal steps
        ) {
        }

        @Schema(description = "활동 기간")
        record Period(
                @Schema(description = "활동 기간 시작 일시")
                @JsonDeserialize(using = MultiFormatLocalDateTimeDeserializer.class)
                @NotNull(message = "활동 기간 시작 일시가 없어요.")
                LocalDateTime from,

                @Schema(description = "활동 기간 종료 일시")
                @JsonDeserialize(using = MultiFormatLocalDateTimeDeserializer.class)
                @NotNull(message = "활동 기간 종료 일시가 없어요.")
                LocalDateTime to
        ) {
        }

        @Schema(description = "칼로리")
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

    @Schema(description = "전송 대상 정보")
    record Source(
            @Schema(description = "제품")
            Product product,

            @Schema(description = "모드")
            String mode,

            @Schema(description = "이름")
            String name,

            @Schema(description = "유형")
            String type
    ) {

        @Schema(description = "제품")
        record Product(
                @Schema(description = "벤더")
                @JsonProperty(value = "vender")
                String vendor,

                @Schema(description = "이름")
                String name
        ) {
        }

    }

}
