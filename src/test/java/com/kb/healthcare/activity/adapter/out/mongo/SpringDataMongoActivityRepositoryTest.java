package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.configuration.AbstractDataMongoTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static com.kb.healthcare.activity.domain.ActivityType.STEPS;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class SpringDataMongoActivityRepositoryTest extends AbstractDataMongoTestContext {

    @Autowired
    private SpringDataMongoActivityRepository repository;

    @Test
    @DisplayName(value = "ID로 활동을 조회한다.")
    void findById() {
        // given
        ActivityEntity saved = repository.save(
                create(
                        new BigDecimal("9876"),
                        new BigDecimal("123.456789012345678901234567890"),
                        new BigDecimal("45.678901234567890123456789012")
                )
        );

        // when
        Optional<ActivityEntity> found = repository.findById(saved.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
        assertThat(found.get().getType()).isEqualTo(saved.getType());
        assertThat(found.get().getSteps()).isEqualTo(saved.getSteps());
        assertThat(found.get().getCalories()).isEqualTo(saved.getCalories());
        assertThat(found.get().getDistance()).isEqualTo(saved.getDistance());
        assertThat(found.get().getFromAt()).isEqualTo(saved.getFromAt().truncatedTo(MILLIS));
        assertThat(found.get().getToAt()).isEqualTo(saved.getToAt().truncatedTo(MILLIS));
        assertThat(found.get().getUserId()).isEqualTo(saved.getUserId());
        assertThat(found.get().getProductName()).isEqualTo(saved.getProductName());
        assertThat(found.get().getProductVendor()).isEqualTo(saved.getProductVendor());
        assertThat(found.get().getSourceType()).isEqualTo(saved.getSourceType());
        assertThat(found.get().getSourceMode()).isEqualTo(saved.getSourceMode());
        assertThat(found.get().getSourceName()).isEqualTo(saved.getSourceName());
        assertThat(found.get().getCreatedAt()).isNotNull();
    }

    private ActivityEntity create(
            BigDecimal steps,
            BigDecimal calories,
            BigDecimal distance
    ) {
        return ActivityEntity.builder()
                .type(STEPS)
                .steps(steps)
                .calories(calories)
                .distance(distance)
                .userId(randomUUID())
                .fromAt(now())
                .toAt(now().plus(1, DAYS))
                .productName("IPhone")
                .productVendor("Apple inc.")
                .sourceType("")
                .sourceMode("10")
                .sourceName("Health Kit")
                .build();
    }

}
