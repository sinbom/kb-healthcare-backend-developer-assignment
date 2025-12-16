package com.kb.healthcare.activity.application.port.out;

import com.kb.healthcare.activity.domain.Activity;

import java.util.List;

public interface CreateActivityPort {

    void create(List<Activity> activities);

}
