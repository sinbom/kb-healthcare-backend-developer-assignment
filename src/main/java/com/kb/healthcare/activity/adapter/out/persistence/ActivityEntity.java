package com.kb.healthcare.activity.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "activity")
@EntityListeners(value = AuditingEntityListener.class)
@EqualsAndHashCode(
        of = "id",
        callSuper = false
)
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class ActivityEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer steps;

    @Column(
            precision = 35,
            scale = 30,
            nullable = false
    )
    private BigDecimal calories;

    @Column(
            precision = 35,
            scale = 30,
            nullable = false
    )
    private BigDecimal distance;

    @Column(nullable = false)
    private UUID deviceId;

    @Column(nullable = false)
    private Long userId;

    @CreatedDate
    @Column(
            updatable = false,
            nullable = false
    )
    private LocalDateTime createdAt;

}
