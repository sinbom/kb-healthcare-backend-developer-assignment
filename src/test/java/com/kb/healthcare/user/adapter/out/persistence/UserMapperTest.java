package com.kb.healthcare.user.adapter.out.persistence;

import com.kb.healthcare.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.time.LocalDateTime.now;
import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    @DisplayName(value = "유저 도메인을 엔티티로 변환한다.")
    void mapToEntity() {
        // given
        User domain = User.builder()
                .id(randomUUID().toString())
                .name("홍길동")
                .nickname("gildong")
                .email("gildong@example.com")
                .password("enc_pwd")
                .createdAt(now())
                .updatedAt(now())
                .build();

        // when
        UserEntity entity = mapper.mapToEntity(domain);

        // then
        assertThat(entity.getId()).isEqualTo(fromString(domain.id()));
        assertThat(entity.getName()).isEqualTo(domain.name());
        assertThat(entity.getNickname()).isEqualTo(domain.nickname());
        assertThat(entity.getEmail()).isEqualTo(domain.email());
        assertThat(entity.getPassword()).isEqualTo(domain.password());
        assertThat(entity.getCreatedAt()).isNull();
        assertThat(entity.getUpdatedAt()).isNull();
    }

    @Test
    @DisplayName(value = "유저 엔티티를 도메인으로 변환한다.")
    void mapToDomain() {
        // given
        UserEntity entity = UserEntity.builder()
                .id(randomUUID())
                .name("홍길동")
                .nickname("gildong")
                .email("gildong@example.com")
                .password("enc_pwd")
                .createdAt(now())
                .updatedAt(now())
                .build();

        // when
        User user = mapper.mapToDomain(entity);

        // then
        assertThat(user.id()).isEqualTo(entity.getId().toString());
        assertThat(user.name()).isEqualTo(entity.getName());
        assertThat(user.nickname()).isEqualTo(entity.getNickname());
        assertThat(user.email()).isEqualTo(entity.getEmail());
        assertThat(user.password()).isEqualTo(entity.getPassword());
        assertThat(user.createdAt()).isEqualTo(entity.getCreatedAt());
        assertThat(user.updatedAt()).isEqualTo(entity.getUpdatedAt());
    }

}
