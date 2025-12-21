package com.kb.healthcare.activity.adapter.in.web;

import com.kb.healthcare.activity.application.port.in.SaveActivityUseCase;
import com.kb.healthcare.activity.application.port.in.command.SaveActivityCommand;
import com.kb.healthcare.activity.domain.Calorie;
import com.kb.healthcare.activity.domain.Distance;
import com.kb.healthcare.activity.domain.Period;
import com.kb.healthcare.activity.domain.Source;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequiredArgsConstructor
class SaveActivityController {

    private final SaveActivityUseCase saveActivityUseCase;

    @PostMapping(
            path = "/api/v1/activities",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity<Void> save(
            @RequestBody @Valid SaveActivitiesRequest request,
            @AuthenticationPrincipal User principal
    ) {
        List<SaveActivityCommand> commands = request.data()
                .entries()
                .stream()
                .map(activity -> SaveActivityCommand.builder()
                        .userId(request.userId())
                        .type(request.type())
                        .period(
                                Period.builder()
                                        .from(activity.period().from())
                                        .to(activity.period().to())
                                        .build()
                        )
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
                        .source(
                                Source.builder()
                                        .productName(
                                                request.data().source().product() != null ?
                                                        request.data().source().product().name() : null
                                        )
                                        .productVendor(
                                                request.data().source().product() != null ?
                                                        request.data().source().product().vendor() : null
                                        )
                                        .type(request.data().source().type())
                                        .mode(request.data().source().mode())
                                        .name(request.data().source().name())
                                        .build()
                        )
                        .build()
                )
                .toList();

        saveActivityUseCase.save(commands);

        return noContent()
                .build();
    }

}
