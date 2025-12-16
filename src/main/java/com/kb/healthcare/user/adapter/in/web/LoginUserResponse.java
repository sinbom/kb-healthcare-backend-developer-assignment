package com.kb.healthcare.user.adapter.in.web;

import lombok.Builder;

@Builder
record LoginUserResponse(
        String accessToken
) {
}
