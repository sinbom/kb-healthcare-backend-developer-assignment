package com.kb.healthcare.activity.domain;

import lombok.Builder;

import java.math.BigDecimal;

import static com.kb.healthcare.activity.domain.DistanceUnit.KM;

@Builder
public record Distance(
        DistanceUnit unit,

        BigDecimal value
) {

    public Distance mergeToKm(Distance distance) {
        BigDecimal value = this.toKm()
                .value()
                .add(distance.toKm().value());

        return Distance.builder()
                .unit(KM)
                .value(value)
                .build();
    }

    public Distance toKm() {
        return switch (this.unit) {
            case KM -> this;
        };
    }

}
