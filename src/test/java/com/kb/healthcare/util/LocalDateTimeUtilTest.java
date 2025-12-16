package com.kb.healthcare.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static java.time.ZoneId.systemDefault;
import static org.assertj.core.api.Assertions.assertThat;

class LocalDateTimeUtilTest {

    @Test
    @DisplayName(value = "LocalDateTime -> Instant 변환한다.")
    void toInstant() {
        // given
        LocalDateTime source = LocalDateTime.now();

        // when
        Instant converted = LocalDateTimeUtil.toInstant(source);

        // then
        assertThat(converted).isEqualTo(source.atZone(systemDefault()).toInstant());
    }

    @Test
    @DisplayName(value = "Instant -> LocalDateTime 변환한다.")
    void toLocalDateTime() {
        // given
        Instant instant = Instant.now().atZone(systemDefault()).toInstant();

        // when
        LocalDateTime converted = LocalDateTimeUtil.toLocalDateTime(instant);

        // then
        assertThat(converted).isEqualTo(instant.atZone(systemDefault()).toLocalDateTime());
    }

}
