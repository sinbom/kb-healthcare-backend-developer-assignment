package com.kb.healthcare.activity.adapter.in.web;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.springframework.util.StringUtils.hasText;

class MultiFormatLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter SPACE_FORMAT = ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter TIME_OFFSET_FORMAT = ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    private static final DateTimeFormatter SPACE_OFFSET_FORMAT = ofPattern("yyyy-MM-dd HH:mm:ss Z");

    @Override
    public LocalDateTime deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext
    ) throws IOException {
        String value = jsonParser.getValueAsString();

        if (!hasText(value)) {
            return null;
        }

        try {
            return LocalDateTime.parse(
                    value,
                    ISO_LOCAL_DATE_TIME
            );
        } catch (Exception ignored) {
        }

        try {
            return LocalDateTime.parse(
                    value,
                    SPACE_FORMAT
            );
        } catch (Exception ignored) {
        }

        try {
            return LocalDateTime.ofInstant(
                    ZonedDateTime.parse(
                                    value,
                                    TIME_OFFSET_FORMAT
                            )
                            .toInstant(),
                    systemDefault()
            );
        } catch (Exception ignored) {
        }

        try {
            return LocalDateTime.ofInstant(
                    ZonedDateTime.parse(
                                    value,
                                    SPACE_OFFSET_FORMAT
                            )
                            .toInstant(),
                    systemDefault()
            );
        } catch (Exception ignored) {
        }

        throw new IllegalArgumentException("지원하지 않는 날짜 포맷이에요.");
    }

}
