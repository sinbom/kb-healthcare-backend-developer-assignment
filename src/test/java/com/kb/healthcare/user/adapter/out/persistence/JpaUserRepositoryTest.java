package com.kb.healthcare.user.adapter.out.persistence;

import com.kb.healthcare.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
class JpaUserRepositoryTest {

    @InjectMocks
    private JpaUserRepository jpaUserRepository;

    @Mock
    private SpringDataJpaUserRepository springDataJpaUserRepository;

    @Spy
    private UserMapper userMapper;

    @Test
    @DisplayName("findById - ID로 유저를 조회한다.")
    void findById() {
        // given
        UserEntity entity = UserEntity.builder()
                .id(10L)
                .name("김철수")
                .nickname("chul")
                .email("chul@example.com")
                .password("pwd")
                .build();

        when(springDataJpaUserRepository.findById(entity.getId())).thenReturn(of(entity));

        // when
        Optional<User> result = jpaUserRepository.findById(entity.getId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(entity.getId());
        assertThat(result.get().name()).isEqualTo(entity.getName());
        assertThat(result.get().nickname()).isEqualTo(entity.getNickname());
        assertThat(result.get().email()).isEqualTo(entity.getEmail());
        assertThat(result.get().password()).isEqualTo(entity.getPassword());
        verify(springDataJpaUserRepository).findById(entity.getId());
    }

    @Test
    @DisplayName("findByNickname - 닉네임으로 유저를 조회한다.")
    void findByNickname() {
        // given
        UserEntity entity = UserEntity.builder()
                .id(2L)
                .name("이영희")
                .nickname("young")
                .email("young@example.com")
                .password("pwd")
                .build();

        when(springDataJpaUserRepository.findByNickname(eq(entity.getNickname()))).thenReturn(of(entity));

        // when
        Optional<User> result = jpaUserRepository.findByNickname(entity.getNickname());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(entity.getId());
        assertThat(result.get().name()).isEqualTo(entity.getName());
        assertThat(result.get().nickname()).isEqualTo(entity.getNickname());
        assertThat(result.get().email()).isEqualTo(entity.getEmail());
        assertThat(result.get().password()).isEqualTo(entity.getPassword());
        verify(springDataJpaUserRepository).findByNickname(entity.getNickname());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("findByNickname - Null이거나 공백인 닉네임으로 유저를 조회할 수 없다.")
    void findByNicknameWhenNullOrEmptyNickname(String nickname) {
        // when
        Optional<User> notFound = jpaUserRepository.findByNickname(nickname);

        // then
        assertThat(notFound).isEmpty();
        verifyNoInteractions(springDataJpaUserRepository);
    }

    @Test
    @DisplayName("findByEmail - 이메일로 유저를 조회한다.")
    void findByEmail() {
        UserEntity entity = UserEntity.builder()
                .id(3L)
                .name("박하늘")
                .nickname("sky")
                .email("sky@example.com")
                .password("pwd")
                .build();
        when(springDataJpaUserRepository.findByEmail(eq(entity.getEmail()))).thenReturn(of(entity));

        // when
        Optional<User> result = jpaUserRepository.findByEmail(entity.getEmail());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(entity.getId());
        assertThat(result.get().name()).isEqualTo(entity.getName());
        assertThat(result.get().nickname()).isEqualTo(entity.getNickname());
        assertThat(result.get().email()).isEqualTo(entity.getEmail());
        assertThat(result.get().password()).isEqualTo(entity.getPassword());
        verify(springDataJpaUserRepository).findByEmail(entity.getEmail());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("findByEmail - Null이거나 공백인 이메일로 유저를 조회할 수 없다.")
    void findByEmailWhenNullOrEmptyEmail(String email) {
        // when
        Optional<User> notFound = jpaUserRepository.findByEmail(email);

        // then
        assertThat(notFound).isEmpty();
        verifyNoInteractions(springDataJpaUserRepository);
    }

}
