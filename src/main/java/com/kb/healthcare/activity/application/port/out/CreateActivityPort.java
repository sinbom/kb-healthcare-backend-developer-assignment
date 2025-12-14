package com.kb.healthcare.activity.application.port.out;

import com.kb.healthcare.activity.domain.Activity;

public interface CreateActivityPort {

    void create(Activity activity);

}
