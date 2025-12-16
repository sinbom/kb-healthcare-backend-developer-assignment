package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import static com.kb.healthcare.activity.domain.ActivityType.STEPS;
import static com.kb.healthcare.activity.domain.CalorieUnit.KCAL;
import static com.kb.healthcare.activity.domain.DistanceUnit.KM;
import static java.time.ZoneId.systemDefault;
import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class ActivityMapperTest {

    private final ActivityMapper mapper = new ActivityMapper();

    @Test
    @DisplayName(value = "활동 도메인을 엔티티로 변환한다.")
    void mapToEntity() {
        // given
        Activity domain = Activity.builder()
                .id(randomUUID().toString())
                .type(STEPS)
                .userId(randomUUID().toString())
                .steps(new BigDecimal("10"))
                .period(
                        Period.builder()
                                .from(LocalDateTime.now())
                                .to(LocalDateTime.now())
                                .build()
                )
                .calories(
                        Calorie.builder()
                                .unit(KCAL)
                                .value(new BigDecimal("3.123123"))
                                .build()
                )
                .distance(
                        Distance.builder()
                                .unit(KM)
                                .value(new BigDecimal("1.98634"))
                                .build()
                )
                .source(
                        Source.builder()
                                .productName("iPhone")
                                .productVendor("Apple inc.")
                                .type("")
                                .mode("10")
                                .name("Health Kit")
                                .build()
                )
                .createdAt(LocalDateTime.now())
                .build();

        // when
        ActivityEntity entity = mapper.mapToEntity(domain);

        // then
        assertThat(entity.getId()).isEqualTo(domain.id());
        assertThat(entity.getType()).isEqualTo(domain.type());
        assertThat(entity.getUserId()).isEqualTo(fromString(domain.userId()));
        assertThat(entity.getSteps()).isEqualTo(domain.steps());
        assertThat(entity.getCalories()).isEqualTo(domain.calories().value());
        assertThat(entity.getDistance()).isEqualTo(domain.distance().value());
        assertThat(entity.getFromAt()).isEqualTo(domain.period().from().atZone(systemDefault()).toInstant());
        assertThat(entity.getToAt()).isEqualTo(domain.period().to().atZone(systemDefault()).toInstant());
        assertThat(entity.getProductName()).isEqualTo(domain.source().productName());
        assertThat(entity.getProductVendor()).isEqualTo(domain.source().productVendor());
        assertThat(entity.getSourceType()).isEqualTo(domain.source().type());
        assertThat(entity.getSourceMode()).isEqualTo(domain.source().mode());
        assertThat(entity.getSourceName()).isEqualTo(domain.source().name());
        assertThat(entity.getCreatedAt()).isNull();
    }

    @Test
    @DisplayName(value = "활동 엔티티를 도메인으로 변환한다.")
    void mapToDomain() {
        // given
        ActivityEntity entity = ActivityEntity.builder()
                .id(randomUUID().toString())
                .userId(randomUUID())
                .steps(new BigDecimal("10"))
                .calories(new BigDecimal("3.123123"))
                .distance(new BigDecimal("1.98634"))
                .fromAt(Instant.now())
                .toAt(Instant.now())
                .productName("iPhone")
                .productVendor("Apple inc.")
                .sourceType("")
                .sourceMode("10")
                .sourceName("Health Kit")
                .createdAt(Instant.now())
                .build();

        // when
        Activity activity = mapper.mapToDomain(entity);

        // then
        assertThat(activity.id()).isEqualTo(entity.getId());
        assertThat(activity.type()).isEqualTo(entity.getType());
        assertThat(activity.userId()).isEqualTo(entity.getUserId().toString());
        assertThat(activity.steps()).isEqualTo(entity.getSteps());
        assertThat(activity.calories().unit()).isEqualTo(KCAL);
        assertThat(activity.calories().value()).isEqualTo(entity.getCalories());
        assertThat(activity.distance().unit()).isEqualTo(KM);
        assertThat(activity.distance().value()).isEqualTo(entity.getDistance());
        assertThat(activity.period().from()).isEqualTo(entity.getFromAt().atZone(systemDefault()).toLocalDateTime());
        assertThat(activity.period().to()).isEqualTo(entity.getToAt().atZone(systemDefault()).toLocalDateTime());
        assertThat(activity.source().productName()).isEqualTo(entity.getProductName());
        assertThat(activity.source().productVendor()).isEqualTo(entity.getProductVendor());
        assertThat(activity.source().type()).isEqualTo(entity.getSourceType());
        assertThat(activity.source().mode()).isEqualTo(entity.getSourceMode());
        assertThat(activity.source().name()).isEqualTo(entity.getProductName());
        assertThat(activity.createdAt()).isEqualTo(entity.getCreatedAt().atZone(systemDefault()).toLocalDateTime());
    }

}
