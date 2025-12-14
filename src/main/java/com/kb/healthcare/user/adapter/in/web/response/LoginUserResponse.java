package com.kb.healthcare.user.adapter.in.web.response;

import lombok.Builder;

@Builder
public record LoginUserResponse(
        String accessToken
) {
}
