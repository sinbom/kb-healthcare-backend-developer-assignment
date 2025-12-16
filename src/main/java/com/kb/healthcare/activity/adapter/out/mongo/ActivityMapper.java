package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.domain.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.kb.healthcare.activity.domain.CalorieUnit.KCAL;
import static com.kb.healthcare.activity.domain.DistanceUnit.KM;
import static com.kb.healthcare.util.LocalDateTimeUtil.toInstant;
import static com.kb.healthcare.util.LocalDateTimeUtil.toLocalDateTime;

@Component
class ActivityMapper {

    Activity mapToDomain(ActivityEntity entity) {
        return Activity.builder()
                .id(entity.getId())
                .type(entity.getType())
                .userId(entity.getUserId().toString())
                .period(
                        Period.builder()
                                .from(toLocalDateTime(entity.getFromAt()))
                                .to(toLocalDateTime(entity.getToAt()))
                                .build()
                )
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
                .source(
                        Source.builder()
                                .productName(entity.getProductName())
                                .productVendor(entity.getProductVendor())
                                .type(entity.getSourceType())
                                .mode(entity.getSourceMode())
                                .name(entity.getProductName())
                                .build()
                )
                .createdAt(toLocalDateTime(entity.getCreatedAt()))
                .build();
    }

    ActivityEntity mapToEntity(Activity activity) {
        return ActivityEntity.builder()
                .type(activity.type())
                .userId(UUID.fromString(activity.userId()))
                .fromAt(toInstant(activity.period().from()))
                .toAt(toInstant(activity.period().to()))
                .distance(activity.distance().value())
                .calories(activity.calories().value())
                .steps(activity.steps())
                .productName(activity.source().productName())
                .productVendor(activity.source().productVendor())
                .sourceType(activity.source().type())
                .sourceMode(activity.source().mode())
                .sourceName(activity.source().name())
                .build();
    }

}
