package com.kb.healthcare.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateUtil {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatDefaultPattern(LocalDate localDate) {
        return localDate.format(DEFAULT_FORMATTER);
    }

    public static LocalDate parseDefaultPattern(String date) {
        return LocalDate.parse(
                date,
                DEFAULT_FORMATTER
        );
    }

}
