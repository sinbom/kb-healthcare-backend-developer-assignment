package com.kb.healthcare.user.adapter.out.persistence;

import com.kb.healthcare.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

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
