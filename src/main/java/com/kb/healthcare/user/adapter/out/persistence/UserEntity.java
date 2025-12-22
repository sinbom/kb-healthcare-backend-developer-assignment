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

    @Column
    private String name;

    @Column
    private String nickname;

    @Column
    private String email;

    @Column
    private String password;

    @CreatedDate
    @Column
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    private LocalDateTime updatedAt;

}
