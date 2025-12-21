package com.kb.healthcare.activity.adapter.out.event;

import com.kb.healthcare.activity.application.port.out.PublishActivityCreateEventPort;
import com.kb.healthcare.activity.domain.CreateActivityEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ActivityCreateEventPublisher implements PublishActivityCreateEventPort {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(CreateActivityEvent event) {
        eventPublisher.publishEvent(event);
    }

}
