package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.application.port.in.command.FindAggregatedActivityCommand;
import com.kb.healthcare.activity.domain.DailyActivity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
@RequiredArgsConstructor
class MongoDailyActivityRepository implements DailyActivityRepository {

    private final SpringDataMongoDailyActivityRepository dailyActivityRepository;

    private final DailyActivityMapper dailyActivityMapper;

    @Override
    public void upsert(List<DailyActivity> dailyActivities) {
        if (isEmpty(dailyActivities)) {
            return;
        }

        dailyActivityRepository.upsert(dailyActivities);
    }

    @Override
    public Page<DailyActivity> find(FindAggregatedActivityCommand command) {
        FindAggregatedActivityQuery query = FindAggregatedActivityQuery.builder()
                .page(command.page())
                .limit(command.limit())
                .userId(command.userId())
                .startDate(command.startDate())
                .endDate(command.endDate())
                .build();

        return dailyActivityRepository.find(query)
                .map(dailyActivityMapper::mapToDomain);
    }

}
