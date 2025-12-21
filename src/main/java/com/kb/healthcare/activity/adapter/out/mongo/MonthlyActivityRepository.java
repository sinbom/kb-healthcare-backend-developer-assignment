package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.application.port.out.FindMonthlyActivityPort;
import com.kb.healthcare.activity.application.port.out.UpsertMonthlyActivityPort;

interface MonthlyActivityRepository extends UpsertMonthlyActivityPort, FindMonthlyActivityPort {
}
