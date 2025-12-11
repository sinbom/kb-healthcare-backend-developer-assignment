package com.kb.healthcare.user.adapter.out.persistence;

import com.kb.healthcare.user.domain.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    User mapToDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
