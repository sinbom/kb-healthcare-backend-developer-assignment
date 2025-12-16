package com.kb.healthcare.activity.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum DistanceUnit {

    KM("km");

    @JsonValue
    private final String type;

    @JsonCreator
    public static Optional<DistanceUnit> findByType(String type) {
        return stream(values())
                .filter(distanceUnit -> distanceUnit.getType().equals(type))
                .findFirst();
    }

}
