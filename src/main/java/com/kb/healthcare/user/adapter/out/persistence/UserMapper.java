package com.kb.healthcare.user.adapter.out.persistence;

import com.kb.healthcare.user.domain.User;
import org.springframework.stereotype.Component;

import static java.util.UUID.fromString;

@Component
class UserMapper {

    User mapToDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId().toString())
                .name(entity.getName())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    UserEntity mapToEntity(User user) {
        return UserEntity.builder()
                .id(fromString(user.id()))
                .name(user.name())
                .nickname(user.nickname())
                .email(user.email())
                .password(user.password())
                .build();
    }

}
