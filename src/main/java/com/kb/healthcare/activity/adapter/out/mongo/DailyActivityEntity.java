package com.kb.healthcare.activity.adapter.out.mongo;

import com.kb.healthcare.activity.domain.ActivityType;
import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128;

@QueryEntity
@Document(collection = "daily_activity")
@EqualsAndHashCode(of = "id")
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class DailyActivityEntity {

    @Id
    private String id;

    private ActivityType type;

    private UUID userId;

    @Field(targetType = DECIMAL128)
    private BigDecimal steps;

    @Field(targetType = DECIMAL128)
    private BigDecimal calories;

    @Field(targetType = DECIMAL128)
    private BigDecimal distance;

    private String date;

    @CreatedDate
    private Instant createdAt;

}
