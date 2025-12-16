package com.kb.healthcare.activity.domain;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Calorie(
        CalorieUnit unit,

        BigDecimal value
) {
}
