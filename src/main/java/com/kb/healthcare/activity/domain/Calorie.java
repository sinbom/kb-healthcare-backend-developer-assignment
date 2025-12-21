package com.kb.healthcare.activity.domain;

import lombok.Builder;

import java.math.BigDecimal;

import static com.kb.healthcare.activity.domain.CalorieUnit.KCAL;

@Builder
public record Calorie(
        CalorieUnit unit,

        BigDecimal value
) {

    public Calorie mergeToKcal(Calorie calorie) {
        BigDecimal value = this.toKcal()
                .value()
                .add(calorie.toKcal().value());

        return Calorie.builder()
                .unit(KCAL)
                .value(value)
                .build();
    }

    public Calorie toKcal() {
        return switch (this.unit) {
            case KCAL -> this;
            case CAL -> Calorie.builder()
                    .unit(KCAL)
                    .value(this.value.multiply(BigDecimal.valueOf(1000L)))
                    .build();
        };
    }

}
