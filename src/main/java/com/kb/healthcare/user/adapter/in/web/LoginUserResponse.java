package com.kb.healthcare.user.adapter.in.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(title = "유저 로그인 응답")
@Builder
record LoginUserResponse(
        @Schema(description = "엑세스 토큰")
        String accessToken
) {
}
