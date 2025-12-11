package com.kb.healthcare.user.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "users")
@EqualsAndHashCode(
        of = "id",
        callSuper = false
)
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(
            length = 64,
            nullable = false
    )
    private String name;

    @Column(
            length = 64,
            nullable = false
    )
    private String nickname;

    @Column(
            length = 320,
            nullable = false
    )
    private String email;

    @Column(
            length = 255,
            nullable = false
    )
    private String password;

}
