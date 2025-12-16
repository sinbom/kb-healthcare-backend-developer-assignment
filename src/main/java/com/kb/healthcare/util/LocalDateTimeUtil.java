package com.kb.healthcare.util;

import java.time.Instant;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;

public class LocalDateTimeUtil {

    public static Instant toInstant(LocalDateTime localDateTime) {
        return localDateTime.atZone(systemDefault())
                .toInstant();
    }

    public static LocalDateTime toLocalDateTime(Instant instant) {
        return ofInstant(
                instant,
                systemDefault()
        );
    }

}
