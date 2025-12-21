package com.kb.healthcare.activity.application.port.out;

import com.kb.healthcare.activity.application.port.in.command.FindAggregatedActivityCommand;
import com.kb.healthcare.activity.domain.MonthlyActivity;
import org.springframework.data.domain.Page;

public interface FindMonthlyActivityPort {

    Page<MonthlyActivity> find(FindAggregatedActivityCommand query);

}
