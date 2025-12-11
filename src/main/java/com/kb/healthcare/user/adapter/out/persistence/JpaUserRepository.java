package com.kb.healthcare.user.adapter.out.persistence;

import com.kb.healthcare.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
class JpaUserRepository implements UserRepository {

    private final SpringDataJpaUserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id)
                .map(userMapper::mapToDomain);
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        if (!hasText(nickname)) {
            return empty();
        }

        return userRepository.findByNickname(nickname)
                .map(userMapper::mapToDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (!hasText(email)) {
            return empty();
        }

        return userRepository.findByEmail(email)
                .map(userMapper::mapToDomain);
    }

}
