package com.kb.healthcare.activity.domain;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Activity(
        Long id,

        Integer steps,

        BigDecimal calories,

        BigDecimal distance,

        String deviceId,

        Long userId
) {
}
