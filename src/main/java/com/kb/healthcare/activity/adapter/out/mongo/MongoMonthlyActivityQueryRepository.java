package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.domain.MonthlyActivity;
import org.springframework.data.domain.Page;

import java.util.List;

interface MongoMonthlyActivityQueryRepository {

    void upsert(List<MonthlyActivity> monthlyActivities);

    Page<MonthlyActivityEntity> find(FindAggregatedActivityQuery query);

}
