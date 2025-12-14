package com.kb.healthcare.user.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.UUID;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "users")
@EntityListeners(value = AuditingEntityListener.class)
@EqualsAndHashCode(
        of = "id",
        callSuper = false
)
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
class UserEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;

    @Column(
            length = 64,
            nullable = false
    )
    private String name;

    @Column(
            length = 64,
            unique = true,
            nullable = false
    )
    private String nickname;

    @Column(
            length = 320,
            unique = true,
            nullable = false
    )
    private String email;

    @Column(
            length = 255,
            nullable = false
    )
    private String password;

    @CreatedDate
    @Column(
            updatable = false,
            nullable = false
    )
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
