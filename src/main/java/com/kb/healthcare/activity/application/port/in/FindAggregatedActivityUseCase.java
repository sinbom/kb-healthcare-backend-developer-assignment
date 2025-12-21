package com.kb.healthcare.activity.application.port.in;

import com.kb.healthcare.activity.application.port.in.command.FindAggregatedActivityCommand;
import com.kb.healthcare.activity.domain.DailyActivity;
import com.kb.healthcare.activity.domain.MonthlyActivity;
import org.springframework.data.domain.Page;

public interface FindAggregatedActivityUseCase {

    Page<DailyActivity> findDaily(FindAggregatedActivityCommand query);

    Page<MonthlyActivity> findMonthly(FindAggregatedActivityCommand query);

}
