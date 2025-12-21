package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.domain.DailyActivity;
import org.springframework.data.domain.Page;

import java.util.List;

interface MongoDailyActivityQueryRepository {

    void upsert(List<DailyActivity> dailyActivities);

    Page<DailyActivityEntity> find(FindAggregatedActivityQuery query);

}
