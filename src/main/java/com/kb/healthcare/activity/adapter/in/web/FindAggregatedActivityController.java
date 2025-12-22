package com.kb.healthcare.activity.adapter.in.web;

import com.kb.healthcare.activity.adapter.in.web.FindAggregatedActivityResponse.AggregatedActivity;
import com.kb.healthcare.activity.adapter.in.web.FindAggregatedActivityResponse.Calorie;
import com.kb.healthcare.activity.adapter.in.web.FindAggregatedActivityResponse.Distance;
import com.kb.healthcare.activity.adapter.in.web.FindAggregatedActivityResponse.Metadata;
import com.kb.healthcare.activity.application.port.in.FindAggregatedActivityUseCase;
import com.kb.healthcare.activity.application.port.in.command.FindAggregatedActivityCommand;
import com.kb.healthcare.activity.domain.DailyActivity;
import com.kb.healthcare.activity.domain.MonthlyActivity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
class FindAggregatedActivityController {

    private final FindAggregatedActivityUseCase findAggregatedActivityUseCase;

    @GetMapping(
            path = "/api/v1/activities/daily",
            produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity<FindAggregatedActivityResponse> findDaily(@Valid FindAggregatedActivityRequest request) {
        FindAggregatedActivityCommand command = FindAggregatedActivityCommand.builder()
                .page(request.page())
                .limit(request.limit())
                .userId(request.userId())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();

        Page<DailyActivity> dailyActivities = findAggregatedActivityUseCase.findDaily(command);

        Metadata metadata = Metadata.builder()
                .page(command.page())
                .limit(command.limit())
                .totalCount(dailyActivities.getTotalElements())
                .build();

        List<AggregatedActivity> responses = dailyActivities.getContent()
                .stream()
                .map(dailyActivity ->
                        AggregatedActivity.builder()
                                .id(dailyActivity.id())
                                .type(dailyActivity.type())
                                .recordKey(dailyActivity.userId())
                                .calorie(
                                        Calorie.builder()
                                                .unit(dailyActivity.calories().unit())
                                                .value(dailyActivity.calories().value())
                                                .build()
                                )
                                .distance(
                                        Distance.builder()
                                                .unit(dailyActivity.distance().unit())
                                                .value(dailyActivity.distance().value())
                                                .build()
                                )
                                .steps(dailyActivity.steps())
                                .date(dailyActivity.date())
                                .build()
                )
                .toList();

        FindAggregatedActivityResponse response = FindAggregatedActivityResponse.builder()
                .metadata(metadata)
                .contents(responses)
                .build();

        return ok(response);
    }

    @GetMapping(
            path = "/api/v1/activities/monthly",
            produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity<FindAggregatedActivityResponse> findMonthly(@Valid FindAggregatedActivityRequest request) {
        FindAggregatedActivityCommand command = FindAggregatedActivityCommand.builder()
                .page(request.page())
                .limit(request.limit())
                .userId(request.userId())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();

        Page<MonthlyActivity> monthlyActivities = findAggregatedActivityUseCase.findMonthly(command);

        Metadata metadata = Metadata.builder()
                .page(command.page())
                .limit(command.limit())
                .totalCount(monthlyActivities.getTotalElements())
                .build();

        List<AggregatedActivity> responses = monthlyActivities.getContent()
                .stream()
                .map(monthlyActivity ->
                        AggregatedActivity.builder()
                                .id(monthlyActivity.id())
                                .type(monthlyActivity.type())
                                .recordKey(monthlyActivity.userId())
                                .calorie(
                                        Calorie.builder()
                                                .unit(monthlyActivity.calories().unit())
                                                .value(monthlyActivity.calories().value())
                                                .build()
                                )
                                .distance(
                                        Distance.builder()
                                                .unit(monthlyActivity.distance().unit())
                                                .value(monthlyActivity.distance().value())
                                                .build()
                                )
                                .steps(monthlyActivity.steps())
                                .date(monthlyActivity.date())
                                .build()
                )
                .toList();

        FindAggregatedActivityResponse response = FindAggregatedActivityResponse.builder()
                .metadata(metadata)
                .contents(responses)
                .build();

        return ok(response);
    }

}
