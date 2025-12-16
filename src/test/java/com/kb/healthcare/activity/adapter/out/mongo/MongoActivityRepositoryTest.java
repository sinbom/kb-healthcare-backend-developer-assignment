package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.kb.healthcare.activity.domain.ActivityType.STEPS;
import static com.kb.healthcare.activity.domain.CalorieUnit.KCAL;
import static com.kb.healthcare.activity.domain.DistanceUnit.KM;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(value = MockitoExtension.class)
class MongoActivityRepositoryTest {

    @InjectMocks
    private MongoActivityRepository mongoActivityRepository;

    @Mock
    private SpringDataMongoActivityRepository springDataMongoActivityRepository;

    @Spy
    private ActivityMapper activityMapper;

    @Test
    @DisplayName(value = "활동을 저장한다.")
    void create() {
        // given
        Activity activity = Activity.builder()
                .type(STEPS)
                .steps(new BigDecimal("12345"))
                .calories(
                        Calorie.builder()
                                .unit(KCAL)
                                .value(new BigDecimal("12.345678901234567890123456789"))
                                .build()
                )
                .distance(
                        Distance.builder()
                                .unit(KM)
                                .value(new BigDecimal("1.234567890123456789012345678"))
                                .build()
                )
                .period(
                        Period.builder()
                                .from(now())
                                .to(now().plusMinutes(1L))
                                .build()
                )
                .userId(UUID.randomUUID().toString())
                .source(
                        Source.builder()
                                .productName("iPhone")
                                .productVendor("Apple inc.")
                                .type("")
                                .mode("10")
                                .name("health Kit")
                                .build()
                )
                .build();

        // when
        mongoActivityRepository.create(List.of(activity));

        // then
        ArgumentCaptor<List<ActivityEntity>> captor = ArgumentCaptor.forClass((Class) List.class);
        verify(springDataMongoActivityRepository).saveAll(captor.capture());

        List<ActivityEntity> saved = captor.getValue();
        assertThat(saved).hasSize(1);
        assertThat(saved.get(0).getId()).isNull();
        assertThat(saved.get(0).getType()).isEqualTo(activity.type());
        assertThat(saved.get(0).getSteps()).isEqualTo(activity.steps());
        assertThat(saved.get(0).getCalories()).isEqualTo(activity.calories().value());
        assertThat(saved.get(0).getDistance()).isEqualTo(activity.distance().value());
        assertThat(saved.get(0).getFromAt()).isEqualTo(activity.period().from().atZone(systemDefault()).toInstant());
        assertThat(saved.get(0).getToAt()).isEqualTo(activity.period().to().atZone(systemDefault()).toInstant());
        assertThat(saved.get(0).getUserId().toString()).isEqualTo(activity.userId());
        assertThat(saved.get(0).getProductName()).isEqualTo(activity.source().productName());
        assertThat(saved.get(0).getProductVendor()).isEqualTo(activity.source().productVendor());
        assertThat(saved.get(0).getSourceType()).isEqualTo(activity.source().type());
        assertThat(saved.get(0).getSourceMode()).isEqualTo(activity.source().mode());
        assertThat(saved.get(0).getSourceName()).isEqualTo(activity.source().name());
    }

}
