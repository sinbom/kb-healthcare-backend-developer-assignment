package com.kb.healthcare.user.domain;

import lombok.Builder;

@Builder
public record UserToken(
        String accessToken
) {

}
