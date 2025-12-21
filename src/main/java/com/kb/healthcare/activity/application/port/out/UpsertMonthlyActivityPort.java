package com.kb.healthcare.activity.application.port.out;

import com.kb.healthcare.activity.domain.MonthlyActivity;

import java.util.List;

public interface UpsertMonthlyActivityPort {

    void upsert(List<MonthlyActivity> activities);

}
