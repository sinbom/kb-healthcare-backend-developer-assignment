package com.kb.healthcare.activity.application.port.in.command;

import com.kb.healthcare.activity.domain.*;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SaveActivityCommand(
        String userId,
        ActivityType type,
        Period period,
        Distance distance,
        Calorie calories,
        BigDecimal steps,
        Source source
) {
}
