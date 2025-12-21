package com.kb.healthcare.activity.application.port.in.command;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FindAggregatedActivityCommand(
        int page,

        int limit,

        String userId,

        LocalDate startDate,

        LocalDate endDate
) {
}
