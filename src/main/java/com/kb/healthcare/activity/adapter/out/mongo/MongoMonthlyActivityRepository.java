package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.application.port.in.command.FindAggregatedActivityCommand;
import com.kb.healthcare.activity.domain.MonthlyActivity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
@RequiredArgsConstructor
class MongoMonthlyActivityRepository implements MonthlyActivityRepository {

    private final SpringDataMongoMonthlyActivityRepository monthlyActivityRepository;

    private final MonthlyActivityMapper monthlyActivityMapper;

    @Override
    public void upsert(List<MonthlyActivity> monthlyActivities) {
        if (isEmpty(monthlyActivities)) {
            return;
        }

        monthlyActivityRepository.upsert(monthlyActivities);
    }

    @Override
    public Page<MonthlyActivity> find(FindAggregatedActivityCommand command) {
        FindAggregatedActivityQuery query = FindAggregatedActivityQuery.builder()
                .page(command.page())
                .limit(command.limit())
                .userId(command.userId())
                .startDate(command.startDate())
                .endDate(command.endDate())
                .build();

        return monthlyActivityRepository.find(query)
                .map(monthlyActivityMapper::mapToDomain);
    }

}
