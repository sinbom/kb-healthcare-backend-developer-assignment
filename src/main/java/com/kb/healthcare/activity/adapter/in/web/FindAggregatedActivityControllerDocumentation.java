package com.kb.healthcare.activity.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

import static com.kb.healthcare.configuration.SwaggerConfiguration.SECURITY_SCHEME_NAME;

abstract class FindAggregatedActivityControllerDocumentation {

    @Operation(
            summary = "활동 데이터 일별 조회 API",
            tags = "Activity"
    )
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    abstract ResponseEntity<FindAggregatedActivityResponse> findDaily(FindAggregatedActivityRequest request);

    @Operation(
            summary = "활동 데이터 월별 조회 API",
            tags = "Activity"
    )
    @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    abstract ResponseEntity<FindAggregatedActivityResponse> findMonthly(FindAggregatedActivityRequest request);

}
