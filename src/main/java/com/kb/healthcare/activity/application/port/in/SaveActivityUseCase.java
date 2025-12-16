package com.kb.healthcare.activity.application.port.in;

import com.kb.healthcare.activity.application.port.in.command.SaveActivityCommand;

import java.util.List;

public interface SaveActivityUseCase {

    void save(List<SaveActivityCommand> commands);

}
