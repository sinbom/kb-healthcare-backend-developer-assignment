package com.kb.healthcare.user.adapter.persistence;

import com.kb.healthcare.configuration.AbstractDataJpaTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static java.lang.Long.MAX_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

class SpringDataJpaUserRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private SpringDataJpaUserRepository repository;

    private UserEntity create(
            String name,
            String nickname,
            String email
    ) {
        return UserEntity.builder()
                .name(name)
                .nickname(nickname)
                .email(email)
                .password("secret")
                .build();
    }

    @Test
    @DisplayName("findById - ID로 유저를 조회한다.")
    void findById() {
        // given
        UserEntity saved = repository.save(
                create(
                        "홍길동",
                        "gildong",
                        "gildong@example.com"
                )
        );

        // when
        Optional<UserEntity> found = repository.findById(saved.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
        assertThat(found.get().getName()).isEqualTo(saved.getName());
        assertThat(found.get().getNickname()).isEqualTo(saved.getNickname());
        assertThat(found.get().getEmail()).isEqualTo(saved.getEmail());
        assertThat(found.get().getPassword()).isEqualTo(saved.getPassword());
        assertThat(found.get().getCreatedAt()).isNotNull();
        assertThat(found.get().getPassword()).isNotNull();
    }

    @Test
    @DisplayName("findById - 존재하지 않는 ID로 유저를 조회할 수 없다.")
    void findByIdWhenNotFound() {
        // when
        Optional<UserEntity> notFound = repository.findById(MAX_VALUE);

        // then
        assertThat(notFound).isEmpty();
    }

    @Test
    @DisplayName("findByNickname - 닉네임으로 유저를 조회한다.")
    void findByNicknames() {
        // given
        UserEntity saved = repository.save(
                create
                        ("임꺽정",
                                "imgg",
                                "im@example.com"
                        )
        );

        // when
        Optional<UserEntity> found = repository.findByNickname(saved.getNickname());
        Optional<UserEntity> notFound = repository.findByNickname("none");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
        assertThat(found.get().getName()).isEqualTo(saved.getName());
        assertThat(found.get().getNickname()).isEqualTo(saved.getNickname());
        assertThat(found.get().getEmail()).isEqualTo(saved.getEmail());
        assertThat(found.get().getPassword()).isEqualTo(saved.getPassword());
        assertThat(found.get().getCreatedAt()).isNotNull();
        assertThat(found.get().getPassword()).isNotNull();
        assertThat(notFound).isEmpty();
    }

    @Test
    @DisplayName("findByNickname - 존재하지 않는 닉네임으로 유저를 조회할 수 없다.")
    void findByNicknameWhenNotFound() {
        // when
        Optional<UserEntity> notFound = repository.findByNickname("none");

        // then
        assertThat(notFound).isEmpty();
    }

    @Test
    @DisplayName(value = "findByEmail - 이메일로 유저를 조회한다.")
    void findByEmail() {
        // given
        UserEntity saved = repository.save(
                create(
                        "성춘향",
                        "sung",
                        "sung@example.com"
                )
        );

        // when
        Optional<UserEntity> found = repository.findByEmail(saved.getEmail());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
        assertThat(found.get().getName()).isEqualTo(saved.getName());
        assertThat(found.get().getNickname()).isEqualTo(saved.getNickname());
        assertThat(found.get().getEmail()).isEqualTo(saved.getEmail());
        assertThat(found.get().getPassword()).isEqualTo(saved.getPassword());
        assertThat(found.get().getCreatedAt()).isNotNull();
        assertThat(found.get().getPassword()).isNotNull();
    }

    @Test
    @DisplayName(value = "findByEmail - 존재하지 않는 이메일로 유저를 조회할 수 없다.")
    void findByEmailWhenNotFound() {
        Optional<UserEntity> notFound = repository.findByEmail("none@example.com");

        // then
        assertThat(notFound).isEmpty();
    }

}
