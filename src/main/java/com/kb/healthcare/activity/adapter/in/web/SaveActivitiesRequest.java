package com.kb.healthcare.activity.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kb.healthcare.activity.domain.ActivityType;
import com.kb.healthcare.activity.domain.CalorieUnit;
import com.kb.healthcare.activity.domain.DistanceUnit;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

record SaveActivitiesRequest(
        @JsonProperty(value = "recordkey")
        @NotBlank(message = "사용자 구분 키를 입력해주세요.")
        String userId,

        @NotNull(message = "데이터가 없어요.")
        @Valid Activities data,

        @NotNull(message = "최종 업데이트 날짜가 없어요.")
        @JsonDeserialize(using = MultiFormatLocalDateTimeDeserializer.class)
        LocalDateTime lastUpdate,

        @NotNull(message = "활동 유형이 없어요.")
        ActivityType type
) {

    record Activities(
            String memo,

            @Size(
                    max = 1500,
                    message = "활동 데이터가 너무 많아요."
            )
            @NotEmpty(message = "데이터가 없어요.")
            List<@Valid Activity> entries,

            @NotNull(message = "전송 대상 정보가 없어요.")
            Source source
    ) {

        record Activity(
                @NotNull(message = "활동 기간 정보가 없어요.")
                @Valid Period period,

                @NotNull(message = "이동거리 정보가 없어요.")
                @Valid Distance distance,

                @NotNull(message = "소모 칼로리 정보가 없어요.")
                @Valid Calorie calories,

                @NotNull(message = "활동 걸음 수가 없어요.")
                BigDecimal steps
        ) {
        }

        record Period(
                @JsonDeserialize(using = MultiFormatLocalDateTimeDeserializer.class)
                @NotNull(message = "활동 기간 시작 일시가 없어요.")
                LocalDateTime from,

                @JsonDeserialize(using = MultiFormatLocalDateTimeDeserializer.class)
                @NotNull(message = "활동 기간 종료 일시가 없어요.")
                LocalDateTime to
        ) {
        }

        record Calorie(
                @NotNull(message = "소모 칼로리 단위가 없어요.")
                CalorieUnit unit,

                @NotNull(message = "소모 칼로리 값이 없어요.")
                BigDecimal value
        ) {
        }

        record Distance(
                @NotNull(message = "이동거리 단위가 없어요.")
                DistanceUnit unit,

                @NotNull(message = "이동거리 값이 없어요.")
                BigDecimal value
        ) {
        }

    }

    record Source(
            Product product,

            String mode,

            String name,

            String type
    ) {

        record Product(
                @JsonProperty(value = "vender")
                String vendor,

                String name
        ) {
        }

    }

}
