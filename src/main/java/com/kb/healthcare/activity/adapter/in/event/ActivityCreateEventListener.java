package com.kb.healthcare.activity.adapter.in.event;

import com.kb.healthcare.activity.application.port.out.UpsertDailyActivityPort;
import com.kb.healthcare.activity.application.port.out.UpsertMonthlyActivityPort;
import com.kb.healthcare.activity.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.List;

import static java.util.stream.Collectors.toMap;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@RequiredArgsConstructor
class ActivityCreateEventListener {

    private final UpsertDailyActivityPort upsertDailyActivityPort;

    private final UpsertMonthlyActivityPort upsertMonthlyActivityPort;

    @Async(value = "activityDomainEventExecutor")
    @EventListener
    public void createDailyActivity(CreateActivityEvent event) {
        if (isEmpty(event.activities())) {
            return;
        }

        List<DailyActivity> activities = event.activities()
                .stream()
                .collect(
                        toMap(
                                activity -> DailyActivityKey.builder()
                                        .userId(activity.userId())
                                        .date(
                                                activity.period()
                                                        .from()
                                                        .toLocalDate()
                                        )
                                        .build(),
                                activity -> DailyActivity.builder()
                                        .type(activity.type())
                                        .userId(activity.userId())
                                        .distance(
                                                Distance.builder()
                                                        .unit(activity.distance().unit())
                                                        .value(activity.distance().value())
                                                        .build()
                                        )
                                        .calories(
                                                Calorie.builder()
                                                        .unit(activity.calories().unit())
                                                        .value(activity.calories().value())
                                                        .build()
                                        )
                                        .steps(activity.steps())
                                        .date(
                                                activity.period()
                                                        .from()
                                                        .toLocalDate()
                                        )
                                        .build(),
                                (l, r) -> DailyActivity.builder()
                                        .type(l.type())
                                        .userId(l.userId())
                                        .calories(
                                                l.calories()
                                                        .mergeToKcal(r.calories())
                                        )
                                        .distance(
                                                l.distance()
                                                        .mergeToKm(r.distance())
                                        )
                                        .steps(
                                                l.steps()
                                                        .add(r.steps())
                                        )
                                        .date(l.date())
                                        .build()
                        )
                )
                .values()
                .stream()
                .toList();

        upsertDailyActivityPort.upsert(activities);
    }

    @Async(value = "activityDomainEventExecutor")
    @EventListener
    public void createMonthlyActivity(CreateActivityEvent event) {
        if (isEmpty(event.activities())) {
            return;
        }

        List<MonthlyActivity> monthlyActivities = event.activities()
                .stream()
                .collect(
                        toMap(
                                activity -> MonthlyActivityKey.builder()
                                        .userId(activity.userId())
                                        .date(
                                                YearMonth.from(
                                                        activity.period()
                                                                .from()
                                                )
                                        )
                                        .build(),
                                activity -> MonthlyActivity.builder()
                                        .type(activity.type())
                                        .userId(activity.userId())
                                        .distance(
                                                Distance.builder()
                                                        .unit(activity.distance().unit())
                                                        .value(activity.distance().value())
                                                        .build()
                                        )
                                        .calories(
                                                Calorie.builder()
                                                        .unit(activity.calories().unit())
                                                        .value(activity.calories().value())
                                                        .build()
                                        )
                                        .steps(activity.steps())
                                        .date(
                                                activity.period()
                                                        .from()
                                                        .toLocalDate()
                                                        .withDayOfMonth(1)
                                        )
                                        .build(),
                                (l, r) -> MonthlyActivity.builder()
                                        .type(l.type())
                                        .userId(l.userId())
                                        .calories(
                                                l.calories()
                                                        .mergeToKcal(r.calories())
                                        )
                                        .distance(
                                                l.distance()
                                                        .mergeToKm(r.distance())
                                        )
                                        .steps(l.steps().add(r.steps()))
                                        .date(l.date())
                                        .build()
                        )
                )
                .values()
                .stream()
                .toList();

        upsertMonthlyActivityPort.upsert(monthlyActivities);
    }

}
