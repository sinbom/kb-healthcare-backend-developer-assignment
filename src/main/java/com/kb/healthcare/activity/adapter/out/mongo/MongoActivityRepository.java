package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.domain.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
class MongoActivityRepository implements ActivityRepository {

    private final SpringDataMongoActivityRepository activityRepository;

    private final ActivityMapper activityMapper;

    @Override
    public void create(List<Activity> activities) {
        List<ActivityEntity> activityEntities = activities.stream()
                .map(activityMapper::mapToEntity)
                .toList();

        activityRepository.saveAll(activityEntities);
    }

}
