package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.domain.Calorie;
import com.kb.healthcare.activity.domain.DailyActivity;
import com.kb.healthcare.activity.domain.Distance;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.kb.healthcare.activity.domain.CalorieUnit.KCAL;
import static com.kb.healthcare.activity.domain.DistanceUnit.KM;
import static com.kb.healthcare.util.LocalDateTimeUtil.toLocalDateTime;
import static com.kb.healthcare.util.LocalDateUtil.formatDefaultPattern;
import static com.kb.healthcare.util.LocalDateUtil.parseDefaultPattern;

@Component
class DailyActivityMapper {

    DailyActivity mapToDomain(DailyActivityEntity entity) {
        return DailyActivity.builder()
                .id(entity.getId())
                .type(entity.getType())
                .userId(entity.getUserId().toString())
                .distance(
                        Distance.builder()
                                .unit(KM)
                                .value(entity.getDistance())
                                .build()
                )
                .calories(
                        Calorie.builder()
                                .unit(KCAL)
                                .value(entity.getCalories())
                                .build()
                )
                .steps(entity.getSteps())
                .date(parseDefaultPattern(entity.getDate()))
                .createdAt(toLocalDateTime(entity.getCreatedAt()))
                .build();
    }

    DailyActivityEntity mapToEntity(DailyActivity activity) {
        return DailyActivityEntity.builder()
                .id(activity.id())
                .type(activity.type())
                .userId(UUID.fromString(activity.userId()))
                .distance(activity.distance().toKm().value())
                .calories(activity.calories().toKcal().value())
                .steps(activity.steps())
                .date(formatDefaultPattern(activity.date()))
                .build();
    }

}
