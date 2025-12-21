package com.kb.healthcare.activity.application.service;

import com.kb.healthcare.activity.application.port.in.SaveActivityUseCase;
import com.kb.healthcare.activity.application.port.in.command.SaveActivityCommand;
import com.kb.healthcare.activity.application.port.out.CreateActivityPort;
import com.kb.healthcare.activity.application.port.out.PublishActivityCreateEventPort;
import com.kb.healthcare.activity.domain.Activity;
import com.kb.healthcare.activity.domain.CreateActivityEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class SaveActivityService implements SaveActivityUseCase {

    private final CreateActivityPort createActivityPort;

    private final PublishActivityCreateEventPort publishActivityCreateEventPort;

    @Override
    public void save(List<SaveActivityCommand> commands) {
        List<Activity> activities = commands.stream()
                .map(command ->
                        Activity.builder()
                                .type(command.type())
                                .userId(command.userId())
                                .period(command.period())
                                .distance(command.distance())
                                .calories(command.calories())
                                .steps(command.steps())
                                .source(command.source())
                                .build()
                )
                .toList();

        createActivityPort.create(activities);

        CreateActivityEvent event = CreateActivityEvent.builder()
                .activities(activities)
                .build();

        publishActivityCreateEventPort.publish(event);
    }

}
