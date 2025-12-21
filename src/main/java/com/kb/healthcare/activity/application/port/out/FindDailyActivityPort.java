package com.kb.healthcare.activity.application.port.out;

import com.kb.healthcare.activity.application.port.in.command.FindAggregatedActivityCommand;
import com.kb.healthcare.activity.domain.DailyActivity;
import org.springframework.data.domain.Page;

public interface FindDailyActivityPort {

    Page<DailyActivity> find(FindAggregatedActivityCommand query);

}
