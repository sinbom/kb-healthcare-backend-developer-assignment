package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.application.port.out.FindDailyActivityPort;
import com.kb.healthcare.activity.application.port.out.UpsertDailyActivityPort;

interface DailyActivityRepository extends UpsertDailyActivityPort, FindDailyActivityPort {
}
