package com.kb.healthcare.activity.application.service;

import com.kb.healthcare.activity.application.port.in.FindAggregatedActivityUseCase;
import com.kb.healthcare.activity.application.port.in.command.FindAggregatedActivityCommand;
import com.kb.healthcare.activity.application.port.out.FindDailyActivityPort;
import com.kb.healthcare.activity.application.port.out.FindMonthlyActivityPort;
import com.kb.healthcare.activity.domain.DailyActivity;
import com.kb.healthcare.activity.domain.MonthlyActivity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class FindAggregatedActivityService implements FindAggregatedActivityUseCase {

    private final FindDailyActivityPort findDailyActivityPort;

    private final FindMonthlyActivityPort findMonthlyActivityPort;

    @Override
    public Page<DailyActivity> findDaily(FindAggregatedActivityCommand command) {
        return findDailyActivityPort.find(command);
    }

    @Override
    public Page<MonthlyActivity> findMonthly(FindAggregatedActivityCommand command) {
        return findMonthlyActivityPort.find(command);
    }

}
