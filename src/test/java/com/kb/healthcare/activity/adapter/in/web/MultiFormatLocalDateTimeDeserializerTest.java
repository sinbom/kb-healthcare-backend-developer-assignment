package com.kb.healthcare.activity.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MultiFormatLocalDateTimeDeserializerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ParameterizedTest
    @MethodSource(value = "deserialize")
    @DisplayName(value = "날짜 포맷에 맞춰 역직렬화한다.")
    void deserialize(
            String raw,
            LocalDateTime expected
    ) throws Exception {
        // given
        String json = "{\"dateTime\":\"" + raw + "\"}";

        // when
        Dto dto = objectMapper.readValue(json, Dto.class);

        // then
        assertThat(dto.dateTime()).isEqualTo(expected);
    }

    static Stream<Arguments> deserialize() {
        return Stream.of(
                arguments(
                        "2025-12-16T20:36:31.071716",
                        LocalDateTime.parse("2025-12-16T20:36:31.071716", ISO_LOCAL_DATE_TIME)
                ),
                arguments(
                        "2024-12-16 14:40:00",
                        LocalDateTime.parse("2024-12-16 14:40:00", ofPattern("yyyy-MM-dd HH:mm:ss"))
                ),
                arguments(
                        "2024-11-14T23:10:00+0000",
                        LocalDateTime.ofInstant(
                                ZonedDateTime.parse("2024-11-14T23:10:00+0000", ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"))
                                        .toInstant(),
                                systemDefault()
                        )
                ),
                arguments(
                        "2024-12-16 14:40:00 +0000",
                        LocalDateTime.ofInstant(
                                ZonedDateTime.parse("2024-12-16 14:40:00 +0000", ofPattern("yyyy-MM-dd HH:mm:ss Z"))
                                        .toInstant(),
                                systemDefault()
                        )
                )
        );
    }

    private record Dto(
            @JsonDeserialize(using = MultiFormatLocalDateTimeDeserializer.class)
            LocalDateTime dateTime
    ) {
    }

}