package com.kb.healthcare.activity.application.service;

import com.kb.healthcare.activity.application.port.in.command.SaveActivityCommand;
import com.kb.healthcare.activity.application.port.out.CreateActivityPort;
import com.kb.healthcare.activity.application.port.out.PublishActivityCreateEventPort;
import com.kb.healthcare.activity.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.kb.healthcare.activity.domain.ActivityType.STEPS;
import static com.kb.healthcare.activity.domain.CalorieUnit.KCAL;
import static com.kb.healthcare.activity.domain.DistanceUnit.KM;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(value = MockitoExtension.class)
class SaveActivityServiceTest {

    @InjectMocks
    private SaveActivityService saveActivityUseCase;

    @Mock
    private CreateActivityPort createActivityPort;

    @Mock
    private PublishActivityCreateEventPort publishActivityCreateEventPort;

    @Test
    @DisplayName(value = "활동을 저장한다.")
    void save() {
        // given
        SaveActivityCommand command = SaveActivityCommand.builder()
                .userId(randomUUID().toString())
                .type(STEPS)
                .period(
                        Period.builder()
                                .from(now())
                                .to(now().plusMinutes(10L))
                                .build()
                )
                .distance(
                        Distance.builder()
                                .unit(KM)
                                .value(new BigDecimal("1.2345678901234567890123456789"))
                                .build()
                )
                .calories(
                        Calorie.builder()
                                .unit(KCAL)
                                .value(new BigDecimal("75.5678901234567890123456789"))
                                .build()
                )
                .steps(new BigDecimal("10"))
                .source(
                        Source.builder()
                                .productName("iPhone")
                                .productName("iPhone")
                                .productVendor("Apple inc.")
                                .type("")
                                .mode("10")
                                .name("Health Kit")
                                .build()
                )
                .build();

        // when
        saveActivityUseCase.save(List.of(command));

        // then
        ArgumentCaptor<List<Activity>> captor = ArgumentCaptor.forClass((Class) List.class);
        verify(createActivityPort).create(captor.capture());
        verify(publishActivityCreateEventPort).publish(any(CreateActivityEvent.class));

        List<Activity> activities = captor.getValue();
        assertThat(activities).hasSize(1);
        assertThat(activities.get(0).userId()).isEqualTo(command.userId());
        assertThat(activities.get(0).type()).isEqualTo(command.type());
        assertThat(activities.get(0).period()).isEqualTo(command.period());
        assertThat(activities.get(0).distance()).isEqualTo(command.distance());
        assertThat(activities.get(0).calories()).isEqualTo(command.calories());
        assertThat(activities.get(0).steps()).isEqualTo(command.steps());
        assertThat(activities.get(0).source()).isEqualTo(command.source());
    }

}
