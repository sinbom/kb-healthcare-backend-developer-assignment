package com.kb.healthcare.user.adapter.out.persistence;

import com.kb.healthcare.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    @DisplayName(value = "유저 엔티티를 도메인으로 변환한다.")
    void mapToDomain() {
        // given
        UserEntity entity = UserEntity.builder()
                .id(1L)
                .name("홍길동")
                .nickname("gildong")
                .email("gildong@example.com")
                .password("enc_pwd")
                .build();

        // when
        User user = mapper.mapToDomain(entity);

        // then
        assertThat(user.id()).isEqualTo(entity.getId());
        assertThat(user.name()).isEqualTo(entity.getName());
        assertThat(user.nickname()).isEqualTo(entity.getNickname());
        assertThat(user.email()).isEqualTo(entity.getEmail());
        assertThat(user.password()).isEqualTo(entity.getPassword());
    }

}
