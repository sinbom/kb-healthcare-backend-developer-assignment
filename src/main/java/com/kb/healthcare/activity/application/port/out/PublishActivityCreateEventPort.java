package com.kb.healthcare.activity.application.port.out;

import com.kb.healthcare.activity.domain.CreateActivityEvent;

public interface PublishActivityCreateEventPort {

    void publish(CreateActivityEvent event);

}
