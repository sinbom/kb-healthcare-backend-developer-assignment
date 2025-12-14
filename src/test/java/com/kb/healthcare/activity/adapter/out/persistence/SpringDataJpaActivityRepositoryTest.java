package com.kb.healthcare.activity.adapter.out.persistence;

import com.kb.healthcare.configuration.AbstractDataJpaTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class SpringDataJpaActivityRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private SpringDataJpaActivityRepository repository;

    private ActivityEntity create(
            int steps,
            BigDecimal calories,
            BigDecimal distance,
            UUID userId
    ) {
        return ActivityEntity.builder()
                .steps(steps)
                .calories(calories)
                .distance(distance)
                .userId(userId)
                .build();
    }

    @Test
    @DisplayName(value = "ID로 활동을 조회한다.")
    void findById() {
        // given
        ActivityEntity saved = repository.save(
                create(
                        9876,
                        new BigDecimal("123.456789012345678901234567890"),
                        new BigDecimal("45.678901234567890123456789012"),
                        randomUUID()
                )
        );

        // when
        Optional<ActivityEntity> found = repository.findById(saved.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
        assertThat(found.get().getSteps()).isEqualTo(saved.getSteps());
        assertThat(found.get().getCalories()).isEqualTo(saved.getCalories());
        assertThat(found.get().getDistance()).isEqualTo(saved.getDistance());
        assertThat(found.get().getUserId()).isEqualTo(saved.getUserId());
        assertThat(found.get().getCreatedAt()).isNotNull();
    }

}
