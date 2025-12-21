package com.kb.healthcare.activity.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateActivityEvent(
        List<Activity> activities
) {
}
