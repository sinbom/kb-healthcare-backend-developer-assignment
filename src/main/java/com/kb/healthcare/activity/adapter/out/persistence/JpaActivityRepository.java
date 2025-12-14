package com.kb.healthcare.activity.adapter.out.persistence;

import com.kb.healthcare.activity.domain.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static java.util.UUID.fromString;

@Repository
@RequiredArgsConstructor
class JpaActivityRepository implements ActivityRepository {

    private final SpringDataJpaActivityRepository activityRepository;

    @Override
    public void create(Activity activity) {
        ActivityEntity activityEntity = ActivityEntity.builder()
                .steps(activity.steps())
                .calories(activity.calories())
                .distance(activity.distance())
                .userId(fromString(activity.userId()))
                .build();

        activityRepository.save(activityEntity);
    }

}
