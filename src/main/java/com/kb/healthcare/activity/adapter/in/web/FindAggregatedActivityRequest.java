package com.kb.healthcare.activity.adapter.in.web;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.BindParam;

import java.time.LocalDate;

@ParameterObject
record FindAggregatedActivityRequest(
        @Parameter(description = "조회 페이지")
        Integer page,

        @Parameter(description = "조회 갯수")
        Integer limit,

        @Parameter(
                name = "recordKey",
                description = "유저 구분 키"
        )
        @BindParam(value = "recordKey")
        @NotBlank(message = "유저 구분 키를 입력해주세요.")
        String userId,

        @Parameter(description = "조회 시작 날짜")
        LocalDate startDate,

        @Parameter(description = "조회 종료 페이지")
        LocalDate endDate
) {

    FindAggregatedActivityRequest {
        if (page == null) {
            page = 0;
        }

        if (limit == null) {
            limit = 100;
        }
    }

}
