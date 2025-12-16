package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.domain.ActivityType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Document(collection = "activity")
@EqualsAndHashCode(of = "id")
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class ActivityEntity {

    @Id
    private String id;

    private ActivityType type;

    private UUID userId;

    private BigDecimal steps;

    private BigDecimal calories;

    private BigDecimal distance;

    private Instant fromAt;

    private Instant toAt;

    private String productName;

    private String productVendor;

    private String sourceType;

    private String sourceMode;

    private String sourceName;

    @CreatedDate
    private Instant createdAt;

}
