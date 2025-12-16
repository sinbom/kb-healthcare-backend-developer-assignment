package com.kb.healthcare.activity.domain;

import lombok.Builder;

@Builder
public record Source(
        String productName,

        String productVendor,

        String type,

        String mode,

        String name
) {
}
