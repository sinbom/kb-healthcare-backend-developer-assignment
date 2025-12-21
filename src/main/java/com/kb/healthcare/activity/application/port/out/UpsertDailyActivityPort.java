package com.kb.healthcare.activity.application.port.out;

import com.kb.healthcare.activity.domain.DailyActivity;

import java.util.List;

public interface UpsertDailyActivityPort {

    void upsert(List<DailyActivity> activities);

}
