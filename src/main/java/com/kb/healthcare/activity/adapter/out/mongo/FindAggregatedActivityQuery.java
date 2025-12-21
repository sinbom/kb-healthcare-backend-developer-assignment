package com.kb.healthcare.activity.adapter.out.mongo;

import lombok.Builder;

import java.time.LocalDate;

@Builder
record FindAggregatedActivityQuery(
        int page,

        int limit,

        String userId,

        LocalDate startDate,

        LocalDate endDate
) {
}
