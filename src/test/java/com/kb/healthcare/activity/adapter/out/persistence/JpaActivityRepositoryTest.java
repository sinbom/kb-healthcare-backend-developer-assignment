package com.kb.healthcare.activity.adapter.out.persistence;

import com.kb.healthcare.activity.domain.Activity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(value = MockitoExtension.class)
class JpaActivityRepositoryTest {

    @InjectMocks
    private JpaActivityRepository jpaActivityRepository;

    @Mock
    private SpringDataJpaActivityRepository springDataJpaActivityRepository;

    @Test
    @DisplayName(value = "활동을 저장한다.")
    void create() {
        // given
        Activity activity = Activity.builder()
                .steps(12345)
                .calories(new BigDecimal("12.345678901234567890123456789"))
                .distance(new BigDecimal("1.234567890123456789012345678"))
                .userId(UUID.randomUUID().toString())
                .build();

        // when
        jpaActivityRepository.create(activity);

        // then
        ArgumentCaptor<ActivityEntity> captor = ArgumentCaptor.forClass(ActivityEntity.class);
        verify(springDataJpaActivityRepository).save(captor.capture());

        ActivityEntity saved = captor.getValue();
        assertThat(saved.getId()).isNull();
        assertThat(saved.getSteps()).isEqualTo(activity.steps());
        assertThat(saved.getCalories()).isEqualTo(activity.calories());
        assertThat(saved.getDistance()).isEqualTo(activity.distance());
        assertThat(saved.getUserId().toString()).isEqualTo(activity.userId());
    }

}
