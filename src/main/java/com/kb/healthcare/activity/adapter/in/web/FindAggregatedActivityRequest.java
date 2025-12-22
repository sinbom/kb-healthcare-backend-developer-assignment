package com.kb.healthcare.activity.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.BindParam;

import java.time.LocalDate;

record FindAggregatedActivityRequest(
        Integer page,

        Integer limit,

        @BindParam(value = "recordKey")
        @NotBlank(message = "유저 구분 키를 입력해주세요.")
        String userId,

        LocalDate startDate,

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
