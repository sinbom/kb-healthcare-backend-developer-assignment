package com.kb.healthcare.activity.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum ActivityType {

    STEPS("steps");

    @JsonValue
    private final String type;

    @JsonCreator
    public static Optional<ActivityType> findByType(String type) {
        return stream(values())
                .filter(activityType -> activityType.getType().equals(type))
                .findFirst();
    }

}
